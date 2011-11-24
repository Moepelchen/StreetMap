import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: shifter
 * Date: 11/23/11
 * Time: 7:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Config implements IConfig {

    private boolean fDrawSides;
    private boolean fDrawAnchors;
    private boolean fDrawTiles;
    private Double fTileSize;
    private String fStreetConfigPath;
    private Double fWidth;
    private Double fHeight;


    public Config(Globals glob) throws FileNotFoundException {
        try {
            parseConfig(glob);
        } catch (ParserConfigurationException e) {
            throw new FileNotFoundException("Die Konfigurationsdatei konnte nicht gefunden werden");
        } catch (IOException e) {
              throw new FileNotFoundException("Die Konfigurationsdatei konnte nicht gefunden werden");
        } catch (SAXException e) {
              throw new FileNotFoundException("Die Konfigurationsdatei konnte nicht gefunden werden");
        }

    }

    private void parseConfig(Globals glob) throws ParserConfigurationException, IOException, SAXException {

    }

    public boolean isDrawSides() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isDrawTiles() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isDrawAnchors() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Double getTileSize() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Double getHeight() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Double getWidth() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getStreetPath() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
