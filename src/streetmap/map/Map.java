package streetmap.map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import streetmap.SSGlobals;
import streetmap.car.Car;
import streetmap.events.EventQueue;
import streetmap.events.IEvent;
import streetmap.events.PlacementEvent;
import streetmap.heatmap.*;
import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.map.street.IPlaceable;
import streetmap.map.street.Lane;
import streetmap.map.tile.Tile;
import streetmap.pathfinding.AbstractPathFinder;
import streetmap.pathfinding.IPathFindingAlgorithm;
import streetmap.pathfinding.PathFactory;
import streetmap.utils.*;
import sun.java2d.pipe.RenderBuffer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * This represents the the whole Street-Map. The map consist of an Array of Tiles.
 * The number of Tiles is determined by fTileSize, fHeight and fWidth
 */
public class Map implements ISimulateable, ActionListener
{
    /* {author=Ulrich Tewes, version=1.0}*/
    /**
     * height of the map
     */
    private Double fHeight;
    /**
     * width of the map
     */
    private Double fWidth;
    /**
     * Array containing all tiles
     */
    private Tile[][] fTiles;
    /**
     * side length of one Tile
     */
    private Double fTileSize;
    /**
     * Globals containing the different configurations
     */
    private SSGlobals fGlobals;

    /**
     * graphics to draw
     */
    private List<Lane> fEndLanes;
    private int fMaxNumberOfCarsOnOneTile = 0;
    private int fCurrentNumberOfCars = 0;
    private OccupanceHeatMap fHeatMap;
    private IHeatMap fHeatMapPaths;
    private int fNumberOfTilesX;
    private int fNumberOfTilesY;
    private LinkedList<Integer> fCarFlowData;
    private double fCarFlowIndex;
    private List<Tile> fOccupiedTiles;
    private final DataStorage2d fCarData = new DataStorage2d(300);
    private final DataStorage2d fFlowData = new DataStorage2d(300);
    private PathFactory fPathFactory;
    private streetmap.events.EventQueue fEvents;
    private List<IPrintable> fDrawAblesBackground;
    private List<IPrintable> fDrawAblesForeground;
    public List<Lane> getEndLanes()
    {
        return fEndLanes;
    }

    private void init(SSGlobals globals)
    {
        //setting up display configuration
        fHeight = globals.getConfig().getHeight();
        fWidth = globals.getConfig().getWidth();
        fTileSize = globals.getConfig().getTileSize();
        fGlobals = globals;
        fGlobals.setMap(this);

        int numberOfTilesX = (int) (fWidth / fTileSize);
        int numberOfTilesY = (int) (fHeight / fTileSize);
        fTiles = new Tile[numberOfTilesX][numberOfTilesY];
        fEndLanes = new Vector<>();
        generateTiles();
        fHeatMap = new OccupanceHeatMap(fGlobals,fNumberOfTilesX,fNumberOfTilesY);
        fHeatMapPaths = new PathHeatMap(fGlobals,fNumberOfTilesX,fNumberOfTilesY);
        fMaxNumberOfCarsOnOneTile = 1;
        fCarFlowData = new LinkedList<>();
        fCarFlowData.add(0);
        fCarFlowIndex = 0;
        fPathFactory = new PathFactory();
        fEvents = new EventQueue();
        fDrawAblesBackground = new Vector<>();
        fDrawAblesForeground = new Vector<>();

    }

    /**
     * This method generates all Tiles, determined by the width, height and tile size
     */
    private void generateTiles()
    {
        fNumberOfTilesX = (int) (fWidth / fTileSize);
        fNumberOfTilesY = (int) (fHeight / fTileSize);
        for (double x = 0; x < fNumberOfTilesX; x++)
        {
            for (double y = 0; y < fNumberOfTilesY; y++)
            {
                tilesTest(x, y);
            }
        }

    }

    private void tilesTest(double x, double y)
    {
        fTiles[(int) x][(int) y] = new Tile(fGlobals, this, new Point2D.Double(x, y));

    }

    public int getCurrentNumberOfCars()
    {
        return fCurrentNumberOfCars;
    }

	/**
     * Simulate each tile
     */
    public synchronized void simulate()
    {
        if (fOccupiedTiles == null)
        {
            fOccupiedTiles = new ArrayList<>();
            for (Tile[] fTile : fTiles)
            {
                for (Tile tile : fTile)
                {
                    if (tile.getPlaceable() != null)
                    {
                        fOccupiedTiles.add(tile);
                    }
                }
            }
        }
        fMaxNumberOfCarsOnOneTile = 1;
        fCurrentNumberOfCars = 0;
        fCarFlowIndex = 0;
        fDrawAblesBackground.clear();
        fDrawAblesForeground.clear();
        fCarFlowData.add(0);

        for (Tile tile : fOccupiedTiles)
        {
            fCurrentNumberOfCars = fCurrentNumberOfCars + tile.getNumberOfCars();
            int numberOfCars = tile.getNumberOfCars();
            if (fMaxNumberOfCarsOnOneTile < numberOfCars)
            {
                fMaxNumberOfCarsOnOneTile = numberOfCars;
            }
            fDrawAblesBackground.add(tile.getPlaceable());
            for (Lane lane : tile.getLanes()) {
                fDrawAblesForeground.addAll(lane.getCars());
            }

        }


        for (Tile tile : fOccupiedTiles)
        {
            tile.simulate();
        }

        updateHeatMap();
        updatePathHeatMap(new ArrayList<>(fDrawAblesForeground));
        if (fCarFlowData.size() > 300)
        {
            fCarFlowData.removeFirst();
        }
        for (Integer integer : fCarFlowData)
        {
            fCarFlowIndex = fCarFlowIndex + integer;
        }
        fCarFlowIndex = fCarFlowIndex / 300;
        fEvents.clearEventQueues();
    }

    private void updateHeatMap()
    {
        fHeatMap.setMaxNumberOfCarsOnOneTile(fMaxNumberOfCarsOnOneTile);
        fHeatMap.setData(fOccupiedTiles);
        fHeatMap.update();
    }

    private void updatePathHeatMap(List<IPrintable> printables)
    {
        fHeatMapPaths.setData(printables);
        fHeatMapPaths.update();
    }

    /**
     * print each tile
     *
     */
    void print()
    {

        drawTiles();

        //drawBuffers(g);
    }

    private void drawTiles()
    {
        if (fOccupiedTiles != null)
        {

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

	        RenderStuff stuff = PrintableRenderBuffer.initBuffers(fGlobals, fDrawAblesBackground);
	        if (stuff != null)
	        {

		        DrawHelper.drawBuffers(stuff, TextureCache.getTextureId("./images/streets/streets.png"));

		        stuff.release();

	        }
	        if(fGlobals.getConfig().isShowCars())
            {
                RenderStuff stuff2 = PrintableRenderBuffer.initBuffers(fGlobals, fDrawAblesForeground,true);


                PrintableRenderBuffer.drawPaths(fGlobals,fDrawAblesForeground);

                if(stuff2 != null)
	            {
		            DrawHelper.drawBuffers(stuff2, TextureCache.getTextureId("./images/cars/Car.png"));
		            stuff2.release();
	            }

            }



	        //DrawHelper.drawBuffers(DrawHelper.setupQuad(),TextureCache.getTextureId("F:\\WorkspaceGIT2\\WorkspaceGIT\\StreetMap\\images\\streets\\BendSE.png"));
         }
    }

    /**
     * Returns the Tile defined by x and y
     *
     * @param x x posiotn in the array
     * @param y y position in the array
     * @return the desired Tile, else null
     */
    public Tile getTile(double x, double y)
    {
        try
        {
            return fTiles[(int) x][(int) y];
        } catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }

    public void paint()
    {

        this.print();
        double l = Math.round(fCarFlowIndex * 1000) / 1000.0;
        fCarData.add((double) fCurrentNumberOfCars);
        fFlowData.add(l);

    }


    public void actionPerformed(ActionEvent e)
    {
        // repaint();
    }

    public Tile[][] getTiles()
    {
        return fTiles;
    }

    public void reset()
    {
        init(fGlobals);
    }

    public void removeEnd(Lane lane)
    {
        fEndLanes.remove(lane);
    }

    public void addEnd(Lane lane)
    {
        fEndLanes.add(lane);
    }

    public double getTileWidth()
    {
        return fTileSize;
    }

    public void addCarFlowData()
    {
        int newLast = fCarFlowData.getLast() + 1;
        fCarFlowData.set(fCarFlowData.size() - 1, newLast);
    }

    public Vector<IEvent> getEvents()
    {
        return fEvents.getEvents();
    }

    /**
     * Constructor setting everything up
     *
     * @param globals Global settings and parameters
     */
    public Map(SSGlobals globals)
    {
        init(globals);

    }

    public void handleAddition(IPlaceable placeable)
    {
        fOccupiedTiles = null;
        fEvents.addEvent(new PlacementEvent(placeable));
        AbstractPathFinder.clearNoGo();
    }

    public DataStorage2d getFlowData()
    {
        return fFlowData;
    }

    public DataStorage2d getCarData()
    {
        return fCarData;
    }

    public PathFactory getPathFactory()
    {
        return fPathFactory;
    }

    public void release()
    {
        fPathFactory.release();
    }

    public IHeatMap getHeatMap()
    {
        return fHeatMap;
    }

    public IHeatMap getHeatMapPaths()
    {
        return fHeatMapPaths;
    }

}