/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 11/22/11
 * Time: 11:25 AM
 * This is a Dummy Config (without a config file, which is coming soon)
 */
public class Configuration {
	private boolean fDrawTiles;
	private Double fHeight;
	private Double fWidth;
	private Double fTileSize;

	private boolean fDrawAnchors;

	public boolean isDrawTiles() {

		return true;
	}

	public boolean isDrawAnchors() {
		return true;
	}


	public Double getTileSize() {
		return 50.0;
	}

	public Double getHeight() {
		return 300.0;
	}

	public Double getWidth() {
		return 300.0;
	}

	public boolean isDrawSides() {
		return true;
	}
}
