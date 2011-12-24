package streetmap;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

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

	/**
	 * graphics to draw
	 */
	private Graphics2D fGraphics;

	/**
	 * Constructor setting everything up
	 *
	 * @param globals Global settings and parameters
	 */
	public Map(SSGlobals globals)
	{
		//setting up display configuration
		fHeight = globals.getConfig().getHeight();
		fWidth = globals.getConfig().getWidth();
		fTileSize = globals.getConfig().getTileSize();
		fGlobals = globals;

		fImage = new BufferedImage(fWidth.intValue() + 5, fHeight.intValue() + 5, BufferedImage.TYPE_INT_ARGB);
		fGraphics = (Graphics2D) fImage.getGraphics();
		int numberOfTilesX = (int) (fWidth / fTileSize);
		int numberOfTilesY = (int) (fHeight / fTileSize);
		fTiles = new Tile[numberOfTilesX][numberOfTilesY];

		generateTiles();


		this.addMouseListener(new MapClickHandler(fGlobals, this));
		// debug stuff
		this.setBounds(0, 0, fWidth.intValue() + 2 * 5, fHeight.intValue() + 2 * 5);
		this.setPreferredSize(new Dimension(fWidth.intValue() + 2 * 5, fHeight.intValue() + 2 * 5));
		this.setVisible(true);
		Timer timer;
		timer = new Timer(25, this);
		timer.start();

		fGlobals.setMap(this);
	}


	/**
	 * This method generates all Tiles, determined by the width, height and tile size
	 */
	private void generateTiles()
	{
		int numberOfTilesX = (int) (fWidth / fTileSize);
		int numberOfTilesY = (int) (fHeight / fTileSize);
		for (double x = 0; x < numberOfTilesX; x++)
		{
			for (double y = 0; y < numberOfTilesY; y++)
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
		for (Tile[] fTile : fTiles)
		{
			for (Tile tile : fTile)
			{
				tile.simulate();
			}
		}
	}

	/**
	 * print each tile
	 *
	 * @param g current Graphics2D object
	 */
	public void print(Graphics2D g)
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
		} catch (ArrayIndexOutOfBoundsException e)
		{
			return null;
		}
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		fGraphics.clearRect(0, 0, fWidth.intValue() + 5, fHeight.intValue() + 5);
		this.print(fGraphics);
		g.translate(5, 5);
		g.drawImage(fImage, 0, 0, null);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();

	}

	/*public static void main(String[] args) {
			SSGlobals globals = null;
			try {
				globals = new SSGlobals();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Map map = new Map(globals);
		}*/

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}

    public Tile[][] getTiles() {
        return fTiles;
    }
}