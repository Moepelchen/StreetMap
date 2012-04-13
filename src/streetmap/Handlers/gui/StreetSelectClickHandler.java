package streetmap.Handlers.gui;

import streetmap.Interfaces.IPrintable;
import streetmap.SSGlobals;
import streetmap.Tile;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/21/11
 * Time: 7:48 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class handles the street selection
 */
public class StreetSelectClickHandler implements IPrintable
{
	private Tile fTile;

	private SSGlobals fGlobals;

	public StreetSelectClickHandler(SSGlobals globals, Tile tile)
	{
		fTile = tile;
		fGlobals = globals;
	}

	@Override
	public void print(Graphics2D g)
	{
		fTile.print(g);
	}

	public void handleClick()
	{
		fGlobals.setSelectedStreetTemplate(fTile.getStreet().toString());
	}

    public String getTemplateName() {
        return fTile.getStreet().toString();
    }
}
