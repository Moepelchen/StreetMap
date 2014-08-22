package streetmap.config;

import org.xml.sax.SAXException;
import streetmap.SSGlobals;
import streetmap.xml.jaxb.streets.StreetTemplate;
import streetmap.xml.jaxb.streets.StreetTemplates;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: shifter
 * Date: 11/22/11
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class reads the street config file to determine which kind of streets are available
 */
public class StreetConfig
{

	private HashMap<String, StreetTemplate> fStreets;

	/**
	 * Constructor
	 *
	 * @param glob current globals
	 * @throws FileNotFoundException thrown when the streets.xml is not available
	 */
	public StreetConfig(SSGlobals glob) throws FileNotFoundException
	{
		fStreets = new HashMap<>();
		try
		{
			parse();
		}
		catch (ParserConfigurationException | IOException | SAXException | JAXBException e)
		{
			throw new FileNotFoundException("Die Strassen-Konfigurationsdatei konnte nicht gefunden werden");
		}
	}

	/**
	 * Parses the streets.xml into an jaxb object
	 *
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws JAXBException
	 */
	private void parse() throws IOException, ParserConfigurationException, SAXException, JAXBException
	{
		JAXBContext jc = JAXBContext.newInstance("streetmap.xml.jaxb.streets");

		// create an Unmarshaller
		Unmarshaller u = jc.createUnmarshaller();

		// unmarshal a po instance document into a tree of Java content
		// objects composed of classes from the primer.po package.
		JAXBElement<?> poElement = (JAXBElement<?>) u.unmarshal(new FileInputStream("config/streets.xml"));

		StreetTemplates streetTypes = (StreetTemplates) poElement.getValue();
		List<StreetTemplate> streets = streetTypes.getStreetTemplate();
		for (StreetTemplate street : streets)
		{
			fStreets.put(street.getName(), street);
		}

	}

	/**
	 * Get the StreetTemplate for a specific name
	 *
	 * @param streetName Name of the Template
	 * @return StreetTemplate with the desired name
	 */
	public StreetTemplate getTemplate(String streetName)
	{
		return fStreets.get(streetName);
	}

	public Collection<StreetTemplate> getTemplates()
	{
		return fStreets.values();
	}
}
