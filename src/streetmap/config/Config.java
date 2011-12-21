package streetmap.config;

import org.xml.sax.SAXException;
import streetmap.Interfaces.IConfig;
import streetmap.SSGlobals;
import streetmap.xml.jaxb.JAXBConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
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
    JAXBConfig fConfig;


    public Config(SSGlobals glob) throws FileNotFoundException {
        try {
            parseConfig(glob);
        } catch (ParserConfigurationException e) {
            throw new FileNotFoundException("Die Konfigurationsdatei konnte nicht gefunden werden");
        } catch (IOException e) {
            throw new FileNotFoundException("Die Konfigurationsdatei konnte nicht gefunden werden");
        } catch (SAXException e) {
            throw new FileNotFoundException("Die Konfigurationsdatei konnte nicht gefunden werden");
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private void parseConfig(SSGlobals glob) throws ParserConfigurationException, IOException, SAXException, JAXBException {
        JAXBContext jc = JAXBContext.newInstance("streetmap.xml.jaxb");

        // create an Unmarshaller
        Unmarshaller u = jc.createUnmarshaller();

        // unmarshal a po instance document into a tree of Java content
        // objects composed of classes from the primer.po package.
        JAXBElement<?> poElement = (JAXBElement<?>) u.unmarshal(new FileInputStream(new File("config/config.xml")));
        fConfig = (JAXBConfig) poElement.getValue();

    }

    public boolean isDrawSides() {
        return fConfig.isDrawsides();
    }

	@Override
	public boolean isDrawLanes()
	{
		return fConfig.isDrawlanes();
	}

	public boolean isDrawTiles() {
        return fConfig.isDrawtiles();
    }

    public boolean isDrawAnchors() {
        return fConfig.isDrawanchors();
    }

    public Double getTileSize() {
        return fConfig.getTilesize();
    }

    public Double getHeight() {
        return fConfig.getHeight();
    }

    public Double getWidth() {
        return fConfig.getWidth();
    }

    public String getStreetPath() {
        return null;
    }
}
