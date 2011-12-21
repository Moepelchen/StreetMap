package streetmap;

import streetmap.Interfaces.IPrintable;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/21/11
 * Time: 7:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class StreetSelectHandler implements IPrintable
{
	private Tile fTile;

	private SSGlobals fGlobals;

	public StreetSelectHandler(SSGlobals globals,Tile tile){
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
		//System.out.println("fTile.getStreet().toString() = " + fTile.getStreet().toString());
		fGlobals.setSelectedStreetTemplate(fTile.getStreet().toString());
	}
}
