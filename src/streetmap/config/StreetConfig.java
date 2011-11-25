package streetmap.config;

import org.xml.sax.SAXException;
import streetmap.SSGlobals;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: shifter
 * Date: 11/22/11
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class StreetConfig {
    
    private SSGlobals fGlobals;
    private static final String STREET_CONFIG_NAME = "config/Streets/streets.xml";

    public StreetConfig(SSGlobals glob) throws FileNotFoundException {
        fGlobals = glob;
	    try {
		    parse();
	  } catch (ParserConfigurationException e) {
            throw new FileNotFoundException("Die Straﬂen-Konfigurationsdatei konnte nicht gefunden werden");
        } catch (IOException e) {
              throw new FileNotFoundException("Die Straﬂen-Konfigurationsdatei konnte nicht gefunden werden");
        } catch (SAXException e) {
              throw new FileNotFoundException("Die Straﬂen-Konfigurationsdatei konnte nicht gefunden werden");
        }
    }


	private void parse() throws IOException, ParserConfigurationException, SAXException {
        File file = new File(STREET_CONFIG_NAME);
        System.out.println(file.getAbsoluteFile());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
    }
}
