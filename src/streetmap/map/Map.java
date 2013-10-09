package streetmap.map;

import streetmap.handler.gui.MapClickHandler;
import streetmap.heatmap.Gradient;
import streetmap.heatmap.HeatMap;
import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.SSGlobals;
import streetmap.map.street.Lane;
import streetmap.map.tile.Tile;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * This represents the the whole Street-Map. The map consist of an Array of Tiles.
 * The number of Tiles is determined by fTileSize, fHeight and fWidth
 */
public class Map extends JPanel implements IPrintable, ISimulateable, ActionListener
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

	private BufferedImage fCarLayerImage;

	/**
	 * graphics to draw
	 */
	private Graphics2D fGraphics;
	private Vector<Lane> fStartingLanes;
	private Vector<Lane> fEndLanes;

    private int fMaxNumberOfCars = 0;

    private double[][] fHeatMapData;
    private java.util.List<double[][]> fHeatMapCache;
    private HeatMap fHeatMap;
    private int fNumberOfTilesX;
    private int fNumberOfTilesY;

    /**
	 * Constructor setting everything up
	 *
	 * @param globals Global settings and parameters
	 */
	public Map(SSGlobals globals)
	{
		init(globals);

	}

	private void init(SSGlobals globals)
	{
		//setting up display configuration
		fHeight = globals.getConfig().getHeight();
		fWidth = globals.getConfig().getWidth();
		fTileSize = globals.getConfig().getTileSize();
		fGlobals = globals;

		fImage = new BufferedImage(fWidth.intValue() + 5, fHeight.intValue() + 5, BufferedImage.TYPE_INT_ARGB);
		fCarLayerImage = new BufferedImage(fWidth.intValue() + 5, fHeight.intValue() + 5, BufferedImage.TYPE_INT_ARGB);
		fGraphics = (Graphics2D) fImage.getGraphics();
		int numberOfTilesX = (int) (fWidth / fTileSize);
		int numberOfTilesY = (int) (fHeight / fTileSize);
		fTiles = new Tile[numberOfTilesX][numberOfTilesY];
		fStartingLanes = new Vector<Lane>();
		fEndLanes = new Vector<Lane>();
		generateTiles();

		this.addMouseListener(new MapClickHandler(fGlobals, this));
		// debug stuff
		this.setBounds(0, 0, fWidth.intValue() + 2 * 5, fHeight.intValue() + 2 * 5);
		this.setPreferredSize(new Dimension(fWidth.intValue() + 2 * 5, fHeight.intValue() + 2 * 5));
		this.setVisible(true);
		Timer timer;
		timer = new Timer(25, this);
		timer.start();
        fHeatMapData = new double[numberOfTilesX][numberOfTilesY];
        for(int i = 0; i< fNumberOfTilesX ; i++)
        {
            for(int y = 0; y< fNumberOfTilesY; y++)
            {
                fHeatMapData[i][y] =  0;
            }
        }
        fHeatMapCache = new ArrayList<double[][]>();
        fMaxNumberOfCars = 1;

        fHeatMap = new HeatMap(fHeatMapData,true, Gradient.GRADIENT_RAINBOW);
		fGlobals.setMap(this);
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
				fTiles[(int) x][(int) y] = new Tile(fGlobals, this, new Point2D.Double(x, y));
			}
		}

	}

	/**
	 * Simulate each tile
	 */
	public void simulate()
	{
        fMaxNumberOfCars = 1;

		for (Tile[] fTile : fTiles)
		{
			for (Tile tile : fTile)
			{
				tile.simulate();
                int numberOfCars = tile.getNumberOfCars();
                if(fMaxNumberOfCars < numberOfCars)
                {
                    fMaxNumberOfCars = numberOfCars;
                }
            }
		}
        updateHeatMap();

	}

    private void updateHeatMap() {

        double[][] cache = new double[fNumberOfTilesX][fNumberOfTilesY];

        for(int i = 0; i< fNumberOfTilesX ; i++)
        {
            for(int y = 0; y< fNumberOfTilesY; y++)
            {
                cache[i][y] =  (double)fTiles[i][y].getNumberOfCars()/(double)fMaxNumberOfCars;
            }
        }
        fHeatMapCache.add(cache);
        if(fHeatMapCache.size() > 300)
        {
            fHeatMapCache.remove(0);
        }

        cache = new double[fNumberOfTilesX][fNumberOfTilesY];
        for (double[][] doubles : fHeatMapCache) {
            for (int i = 0; i<fNumberOfTilesX; i++)
            {
                for (int y = 0; y<fNumberOfTilesY;y++)
                {
                    cache[i][y] = cache[i][y] + doubles[i][y]/(double)fMaxNumberOfCars;
                }
            }
        }

        fHeatMap.updateData(cache,true);
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
		for (Tile[] fTile : fTiles)
		{
			for (Tile tile : fTile)
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
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return null;
		}
	}

	public void paint(Graphics g)
	{
		this.simulate();
		super.paint(g);
		fGraphics.clearRect(0, 0, fWidth.intValue() + 5, fHeight.intValue() + 5);
		clearCarLayer();
		this.print(fGraphics);
        fHeatMap.update(fHeatMap.getGraphics());
        AlphaComposite alpha = AlphaComposite
                .getInstance(
                        AlphaComposite.SRC_OVER,
                        0.75f);
        Composite composite = fGraphics.getComposite();
        fGraphics.setComposite(alpha);
        fGraphics.drawImage(fHeatMap.getBufferedImage(),0,0,fWidth.intValue() + 5, fHeight.intValue() + 5,null);
        fGraphics.setComposite(composite);

		g.translate(5, 5);
		fGraphics.drawImage(fCarLayerImage, 0, 0, null);
		g.drawImage(fImage, 0, 0, null);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
		getCarLayerGraphics().dispose();

	}

	private void clearCarLayer()
	{
		Graphics2D carLayerGraphics = (Graphics2D) getCarLayerGraphics();
		Composite backup = carLayerGraphics.getComposite();
		carLayerGraphics.setComposite(
				AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		Rectangle2D.Double rect =
				new Rectangle2D.Double(0, 0, fWidth.intValue() + 5, fHeight.intValue() + 5);
		carLayerGraphics.fill(rect);
		carLayerGraphics.setComposite(backup);
	}

	public void actionPerformed(ActionEvent e)
	{
		repaint();
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

	public Graphics getCarLayerGraphics()
	{
		return fCarLayerImage.getGraphics();
	}

    public int getMaxNumberOfCars()
    {
        return fMaxNumberOfCars;
    }
}