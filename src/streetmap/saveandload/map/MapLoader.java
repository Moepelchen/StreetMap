package streetmap.saveandload.map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import streetmap.SSGlobals;
import streetmap.map.tile.Tile;
import streetmap.saveandload.ILoader;
import streetmap.saveandload.ISaveConstants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class MapLoader implements ILoader
{

	@Override
	public boolean load(File file, SSGlobals glob) throws ParserConfigurationException, IOException, SAXException
	{
		glob.getMap().release();
		// reset Map
		glob.getMap().reset();
		glob.resetPlayer();

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(file);

		doc.getDocumentElement().normalize();

		NodeList tileList = doc.getElementsByTagName(ISaveConstants.TILE_TAG);
		for (int i = 0; i < tileList.getLength(); i++)
		{
			Node tile = tileList.item(i);
			if (tile.getNodeType() == Node.ELEMENT_NODE)
			{
				Element tileElement = (Element) tile;

				String XPos = tileElement.getElementsByTagName(ISaveConstants.XPOS_TAG).item(0).getTextContent();
				String YPos = tileElement.getElementsByTagName(ISaveConstants.YPOS_TAG).item(0).getTextContent();
				String streetName = tileElement.getElementsByTagName(ISaveConstants.STREET_TAG).item(0).getTextContent();

				Tile mapTile = glob.getMap().getTile(Double.parseDouble(XPos), Double.parseDouble(YPos));
				glob.getStreetFactory().createStreet(mapTile, streetName);
			}

		}

		return true;
	}
}
