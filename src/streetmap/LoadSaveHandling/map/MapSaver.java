package streetmap.LoadSaveHandling.map;

import streetmap.Interfaces.save.ISaveConstants;
import streetmap.Map;
import streetmap.Tile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class MapSaver
{
	public static void saveMap(BufferedWriter output, Map map) throws IOException {
        for (Tile[] tiles : map.getTiles()) {
            for (Tile tile : tiles) {
                output.write(String.valueOf(tile.getArrayPosition().getX()));
                output.write(ISaveConstants.CONFIG_SEPERATOR);
                output.write(String.valueOf(tile.getArrayPosition().getY()));
                output.write(ISaveConstants.CONFIG_SEPERATOR);
                if(tile.getStreet() != null)
                    output.write(tile.getStreet().getName());
                else
                    output.write("none");
                output.newLine();
            }
        }
	}
}
