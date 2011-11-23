/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 11/22/11
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class Configuration implements IConfig {
    private boolean fDrawTiles;
    private Double fHeight;
    private Double fWidth;
    private Double fTileSize;
    private boolean fDrawAnchors;

    public boolean isDrawTiles() {

        return true;
    }

    public void setDrawTiles(boolean fDrawTiles) {
        this.fDrawTiles = fDrawTiles;
    }

    public boolean isDrawAnchors() {
        return true;
    }

    public void setDrawAnchors(boolean fDrawAnchors) {
        this.fDrawAnchors = fDrawAnchors;
    }

    public Double getTileSize() {
        return 50.0;
    }

    public void setTileSize(Double fTileSize) {
        this.fTileSize = fTileSize;
    }


    public Double getHeight() {
        return 300.0;
    }

    public Double getWidth() {
        return 800.0;
    }

    public boolean isDrawSides() {
        return true;
    }

    public String getStreetPath() {
        return "/home/shifter/workspace/StreetMap/config/Streets/";
    }
}
