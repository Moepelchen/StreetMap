import org.xml.sax.SAXException;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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


    public Config(Globals glob) {
        try {
            parseConfig(glob);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private void parseConfig(Globals glob) throws ParserConfigurationException, IOException, SAXException {
        File file = new File("config/config.xml");
        System.out.println(file.getAbsoluteFile());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
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
