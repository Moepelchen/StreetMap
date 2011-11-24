import org.xml.sax.SAXException;
import xml.jaxb.JAXBConfigType;

import javax.xml.bind.JAXBContext;
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
	JAXBConfigType configType;


	public Config(Globals glob) throws FileNotFoundException {
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

	private void parseConfig(Globals glob) throws ParserConfigurationException, IOException, SAXException, JAXBException {
		JAXBContext jc = JAXBContext.newInstance("xml.jaxb");

		// create an Unmarshaller
		Unmarshaller u = jc.createUnmarshaller();

		// unmarshal a po instance document into a tree of Java content
		// objects composed of classes from the primer.po package.
		configType = (JAXBConfigType) u.unmarshal(new FileInputStream(new File("config/JAXBConfig.xml")));


	}

	public boolean isDrawSides() {
		return configType.isDrawsides();
	}

	public boolean isDrawTiles() {
		return configType.isDrawtiles();
	}

	public boolean isDrawAnchors() {
		return configType.isDrawanchors();
	}

	public Double getTileSize() {
		return configType.getTilesize();
	}

	public Double getHeight() {
		return configType.getHeight();
	}

	public Double getWidth() {
		return configType.getWidth();
	}

	public String getStreetPath() {
		return null;
	}
}
