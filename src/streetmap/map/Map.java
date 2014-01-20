package streetmap.map;

import streetmap.SSGlobals;
import streetmap.events.*;
import streetmap.events.EventQueue;
import streetmap.heatmap.Gradient;
import streetmap.heatmap.HeatMap;
import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.map.street.Lane;
import streetmap.map.street.Street;
import streetmap.map.tile.Tile;
import streetmap.pathfinding.AbstractPathFinder;
import streetmap.pathfinding.PathFactory;
import streetmap.utils.DrawHelper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * This represents the the whole Street-Map. The map consist of an Array of Tiles.
 * The number of Tiles is determined by fTileSize, fHeight and fWidth
 */
public class Map implements IPrintable, ISimulateable, ActionListener
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
     * Image usd for double buffering
     */
    private BufferedImage fImage;
    /**
     * graphics to draw
     */
    private Graphics2D fGraphics;
    private double[][] fHeatMapCache;
    private Vector<Lane> fStartingLanes;
    private Vector<Lane> fEndLanes;
    private int fMaxNumberOfCarsOnOneTile = 0;
    private int fCurrentNumberOfCars = 0;
    private double[][] fHeatMapData;
    private java.util.List<double[][]> fHeatMapCollection;
    private HeatMap fHeatMap;
    private int fNumberOfTilesX;
    private int fNumberOfTilesY;
    private LinkedList<Integer> fCarFlowData;
    private double fCarFlowIndex;
    private ArrayList<Tile> fOccupiedTiles;
    private DataStorage2d fCarData = new DataStorage2d(300);
    private DataStorage2d fFPSData = new DataStorage2d(300);
    private DataStorage2d fFlowData = new DataStorage2d(300);
    private PathFactory fPathFactory;
    private streetmap.events.EventQueue fEvents;

    public Vector<Lane> getStartingLanes()
    {
        return fStartingLanes;
    }

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

        fImage = new BufferedImage(fWidth.intValue() + 5, fHeight.intValue() + 5, BufferedImage.TYPE_INT_ARGB);
        //  fCarLayerImage = new BufferedImage(fWidth.intValue() + 5, fHeight.intValue() + 5, BufferedImage.TYPE_INT_ARGB);
        fGraphics = (Graphics2D) fImage.getGraphics();
        int numberOfTilesX = (int) (fWidth / fTileSize);
        int numberOfTilesY = (int) (fHeight / fTileSize);
        fTiles = new Tile[numberOfTilesX][numberOfTilesY];
        fStartingLanes = new Vector<>();
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
        fHeatMapCollection = new ArrayList<>();
        fMaxNumberOfCarsOnOneTile = 1;

        fHeatMap = new HeatMap(fHeatMapData, true, Gradient.GRADIENT_HEAT);
        fHeatMapCache = new double[fNumberOfTilesX][fNumberOfTilesY];
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
                    if (tile.getStreet() != null)
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

        double[][] cache = new double[fNumberOfTilesX][fNumberOfTilesY];

        for (int i = 0; i < fNumberOfTilesX; i++)
        {
            for (int y = 0; y < fNumberOfTilesY; y++)
            {

                Tile tile = fTiles[i][y];
                if (tile != null)
                {
                    cache[i][y] = (double) tile.getNumberOfCars() / (double) fMaxNumberOfCarsOnOneTile;

                }
            }
        }
        fHeatMapCollection.add(cache);
        if (fHeatMapCollection.size() > 10)
        {
            fHeatMapCollection.remove(0);
        }

        fHeatMapCache = new double[fNumberOfTilesX][fNumberOfTilesY];
        for (double[][] doubles : fHeatMapCollection)
        {
            for (int i = 0; i < fNumberOfTilesX; i++)
            {
                for (int y = 0; y < fNumberOfTilesY; y++)
                {
                    try
                    {
                        fHeatMapCache[i][y] = fHeatMapCache[i][y] + doubles[i][y] / (double) fMaxNumberOfCarsOnOneTile;
                    } catch (ArrayIndexOutOfBoundsException e)
                    {
                        System.out.println("e = " + e);
                    }
                }
            }
        }

        fHeatMap.updateData(fHeatMapCache, true);
    }

    /**
     * print each tile
     *
     * @param g current Graphics2D object
     */
    public void print(Graphics2D g)
    {

        drawTiles(g);

        //drawCars(g);
    }

    private void drawTiles(Graphics2D g)
    {
        if (fOccupiedTiles != null)
        {
            for (Tile tile : fOccupiedTiles)
            {
                tile.print(g);
            }

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
        long time = System.currentTimeMillis();
        this.print(fGraphics);
        long takenTime = Math.max(System.currentTimeMillis() - time, 1);
        double fps = 1000 / takenTime;
        double l = Math.round(fCarFlowIndex * 1000) / 1000.0;
        fCarData.add((double) fCurrentNumberOfCars);
        fFPSData.add(fps);
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

    public void removeStart(Lane lane)
    {
        fStartingLanes.remove(lane);
    }

    public void addStart(Lane lane)
    {
        fStartingLanes.add(lane);
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
        return fHeatMapCache[((int) point.getX())][((int) point.getY())];
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

    public void handleAddition(Street street)
    {
        fOccupiedTiles = null;
        fEvents.addEvent(new StreetPlacementEvent(street));
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

    public DataStorage2d getFrameData()
    {
        return fFPSData;
    }

    public Tile getTile(Point2D point)
    {
        double arrayX = (int) (point.getX() / fGlobals.getConfig().getTileSize());
        double arrayY = (int) (point.getY() / fGlobals.getConfig().getTileSize());
        return this.getTile(arrayX, arrayY);
    }

    public PathFactory getPathFactory()
    {
        return fPathFactory;
    }
}