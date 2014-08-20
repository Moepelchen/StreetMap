package streetmap.map;

import org.lwjgl.opengl.GL11;
import streetmap.SSGlobals;
import streetmap.car.PrintableRenderBuffer;
import streetmap.car.RenderStuff;
import streetmap.events.EventQueue;
import streetmap.events.IEvent;
import streetmap.events.PlacementEvent;
import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.map.street.IPlaceable;
import streetmap.map.street.Lane;
import streetmap.map.tile.Tile;
import streetmap.pathfinding.AbstractPathFinder;
import streetmap.pathfinding.PathFactory;
import streetmap.utils.DrawHelper;
import streetmap.utils.TextureCache;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

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
    private Vector<Lane> fEndLanes;
    private int fMaxNumberOfCarsOnOneTile = 0;
    private int fCurrentNumberOfCars = 0;
    private double[][] fHeatMapData;
    private int fNumberOfTilesX;
    private int fNumberOfTilesY;
    private LinkedList<Integer> fCarFlowData;
    private double fCarFlowIndex;
    private ArrayList<Tile> fOccupiedTiles;
    private DataStorage2d fCarData = new DataStorage2d(300);
    private DataStorage2d fFlowData = new DataStorage2d(300);
    private PathFactory fPathFactory;
    private streetmap.events.EventQueue fEvents;
    public Vector<Lane> getEndLanes()
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
        fHeatMapData = new double[numberOfTilesX][numberOfTilesY];
        for (int i = 0; i < fNumberOfTilesX; i++)
        {
            for (int y = 0; y < fNumberOfTilesY; y++)
            {
                fHeatMapData[i][y] = 0;
            }
        }
        fMaxNumberOfCarsOnOneTile = 1;
        fCarFlowData = new LinkedList<>();
        fCarFlowData.add(0);
        fCarFlowIndex = 0;
        fPathFactory = new PathFactory();
        fEvents = new EventQueue();

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
        fCarFlowData.add(0);

        for (Tile tile : fOccupiedTiles)
        {
            fCurrentNumberOfCars = fCurrentNumberOfCars + tile.getNumberOfCars();
            int numberOfCars = tile.getNumberOfCars();
            if (fMaxNumberOfCarsOnOneTile < numberOfCars)
            {
                fMaxNumberOfCarsOnOneTile = numberOfCars;
            }
        }


        for (Tile tile : fOccupiedTiles)
        {
            tile.simulate();
        }


        updateHeatMap();
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

	    double max = Double.MIN_VALUE;
	    double min = Double.MAX_VALUE;
        for (Tile fOccupiedTile : fOccupiedTiles)
        {
            Point2D arrayPos = fOccupiedTile.getArrayPosition();
            int x = (int) arrayPos.getX();
            int y = (int) arrayPos.getY();
            fHeatMapData[x][y] = Math.max(fHeatMapData[x][y] - fHeatMapData[x][y]/100d,0);
            fHeatMapData[x][y] = fHeatMapData[x][y]+((double) fOccupiedTile.getNumberOfCars() / (double) fMaxNumberOfCarsOnOneTile)/100d;
			if(fHeatMapData[x][y] > max)
				max = fHeatMapData[x][y];
	        else if(fHeatMapData[x][y] <= min)
	        min = fHeatMapData[x][y];
        }
	    if(max >0)
	    {
		    for (int i = 0; i < fHeatMapData.length; i++)
		    {
			    for (int j = 0; j < fHeatMapData[i].length; j++)
			    {
				    fHeatMapData[i][j] = Math.max((fHeatMapData[i][j]-min)/max,0);
			    }
		    }

	    }
       // fHeatMap.updateData(fHeatMapData, true);
    }

    /**
     * print each tile
     *
     */
    public void print()
    {

        drawTiles();

        //drawCars(g);
    }

    private void drawTiles()
    {
        if (fOccupiedTiles != null)
        {
	        List<IPrintable> cars = new ArrayList<>();
	        List<IPrintable> placeables = new ArrayList<>();
	        for (Tile tile : fOccupiedTiles)
	        {
		        placeables.add(tile.getPlaceable());
		        for (Lane lane : tile.getLanes())
		        {
			        cars.addAll(lane.getCars());
		        }

	        }

	        //placeables.addAll(cars);
	        RenderStuff stuff = PrintableRenderBuffer.initBuffers(fGlobals, placeables);
	        RenderStuff stuff2 = PrintableRenderBuffer.initBuffers(fGlobals, cars);

	        if (stuff != null && stuff2 != null)
	        {
		        GL11.glEnable(GL11.GL_BLEND);
		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		        DrawHelper.drawCars(stuff, TextureCache.getTextureId("F:\\WorkspaceGIT2\\WorkspaceGIT\\StreetMap\\images\\streets\\streets.png"));
		        if(fGlobals.getConfig().isShowCars())
		        {
			        DrawHelper.drawCars(stuff2, TextureCache.getTextureId("F:\\WorkspaceGIT2\\WorkspaceGIT\\StreetMap\\images\\cars\\Car.png"));
		        }

		        stuff.release();
		        stuff2.release();
	        }

	        //DrawHelper.drawCars(DrawHelper.setupQuad(),TextureCache.getTextureId("F:\\WorkspaceGIT2\\WorkspaceGIT\\StreetMap\\images\\streets\\BendSE.png"));
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

    public double getHeatMapReading(Point2D point)
    {
        return fHeatMapData[((int) point.getX())][((int) point.getY())];
    }

    public void addCarFlowData(int removedCars)
    {
        int newLast = fCarFlowData.getLast() + removedCars;
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
}