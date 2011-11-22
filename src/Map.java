import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * This represents the the whole Street-Map. The map consist of an Array of Tiles.
 * The number of Tiles is determined by fTileSize, fHeight and fWidth
 */
public class Map extends JFrame implements IPrintable, ISimulateable {
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


	private Globals fGlobals;

	private BufferedImage fImage;

	private Graphics2D fGraphics;

	public Map(Globals globals) {
		fHeight = globals.getfConfig().getHeight();
		fWidth = globals.getfConfig().getWidth();
		fTileSize = globals.getfConfig().getTileSize();
		fGlobals = globals;

		fImage= new BufferedImage(fWidth.intValue()+5,fHeight.intValue()+5, BufferedImage.TYPE_INT_ARGB);
		fGraphics = (Graphics2D) fImage.getGraphics();
			Double numberOfTilesX = fWidth / fTileSize;
		Double numberOfTilesY = fHeight / fTileSize;
		fTiles = new Tile[numberOfTilesX.intValue()][numberOfTilesY.intValue()];

		generateTiles();

		// debug stuff
		this.setBounds(300,200,fWidth.intValue()+100,fHeight.intValue()+100);
		this.setVisible(true);

	}

	/**
	 * This method generates all Tiles, determined by the width, height and tile size
	 */
	private void generateTiles() {
		double numberOfTilesX = fWidth / fTileSize;
		double numberOfTilesY = fHeight / fTileSize;
		for (double x = 0; x < numberOfTilesX; x++) {
			for (double y = 0; y < numberOfTilesY; y++) {
				fTiles[(int) x][(int) y] = new Tile(fGlobals,this, new Point2D.Double(x, y));
			}
		}




	}

	/**
	 * Simulate each tile
	 */
	public void simulate() {
		for (Tile[] fTile : fTiles) {
			for (Tile tile : fTile) {
				tile.simulate();
			}
		}
	}

	/**
	 * print each tile
	 *
	 * @param g current Graphics2D object
	 */
	public void print(Graphics2D g) {
		for (Tile[] fTile : fTiles) {
			for (Tile tile : fTile) {
				tile.print(g);
			}
		}
	}

	/**
	 * Returns the Tile defined by x and y
	 * @param x x posiotn in the array
	 * @param y y position in the array
	 * @return the desired Tile, else null
	 */
	public Tile getTile(double x, double y) {
		try
		{
			return fTiles[(int)x][(int)y];
		}catch (ArrayIndexOutOfBoundsException e){
			return null;
		}
	}

	public void paint(Graphics g){

		this.print(fGraphics);
		g.translate(50,50);
		g.drawImage(fImage,0,0,new ImageObserver() {
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;  //To change body of implemented methods use File | Settings | File Templates.
			}
		});

	}

	public static void main(String[] args){
		Globals globals = new Globals();
		Map map = new Map(globals);
	}
}