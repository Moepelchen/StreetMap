package streetmap.saveandload.map;

import streetmap.map.Map;
import streetmap.map.tile.Tile;
import streetmap.saveandload.AbstractSaver;
import streetmap.saveandload.ISaveConstants;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class MapSaver extends AbstractSaver
{
	public void save(BufferedWriter output, Object object) throws IOException
	{
		Map map = (Map) object;

		beginTilesTag(output);
		Tile[][] mapTiles = map.getTiles();
		int x = mapTiles.length;
		for (Tile[] tiles : mapTiles)
		{
			for (Tile tile : tiles)
			{
				if (tile.getStreet() != null)
				{
					beginTileTag(output);
					beginXPosTag(output);
					output.write(String.valueOf(tile.getArrayPosition().getX()));
					endXPosTag(output);
					beginYPosTag(output);
					output.write(String.valueOf(tile.getArrayPosition().getY()));
					endYPosTag(output);
					beginStreetTag(output);
					output.write(tile.getStreet().getName());
					endStreetTag(output);
					writeEndTileTag(output);
				}
			}
		}
		endTilesTag(output);
	}

	private static void beginTilesTag(BufferedWriter output) throws IOException
	{
		writeStartTag(ISaveConstants.TILES_TAG,output);
	}

	private static void endTilesTag(BufferedWriter output) throws IOException
	{
		writeEndTag(ISaveConstants.END_TILES_TAG,output);
	}

	private static void beginStreetTag(BufferedWriter output) throws IOException
	{
		writeStartTag(ISaveConstants.STREET_TAG,output);
	}

	private static void endStreetTag(BufferedWriter output) throws IOException
	{
		writeEndTag(ISaveConstants.STREET_TAG,output);
	}

	private static void beginXPosTag(BufferedWriter output) throws IOException
	{
		writeStartTag(ISaveConstants.XPOS_TAG,output);
	}

	private static void beginYPosTag(BufferedWriter output) throws IOException
	{
		writeStartTag(ISaveConstants.YPOS_TAG,output);
	}

	private static void endYPosTag(BufferedWriter output) throws IOException
	{
		writeEndTag(ISaveConstants.END_YPOS_TAG,output);
	}

	private static void endXPosTag(BufferedWriter output) throws IOException
	{
		writeEndTag(ISaveConstants.END_XPOS_TAG,output);
	}

	private static void writeEndTileTag(BufferedWriter output) throws IOException
	{
		writeEndTag(ISaveConstants.END_TILE_TAG,output);
	}

	private static void beginTileTag(BufferedWriter output) throws IOException
	{

		writeStartTag(ISaveConstants.TILE_TAG,output);
	}
}
