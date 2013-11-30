package streetmap.handler.gui;

import streetmap.map.Map;
import streetmap.SSGlobals;
import streetmap.map.tile.Tile;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/21/11
 * Time: 8:22 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class handles all clicks on the map and its tiles
 */
public class MapClickHandler implements MouseListener
{
	private Map fMap;

	private SSGlobals fGlobals;

    private Point2D fStartPoint;

    private Point2D fEndPoint;

	public MapClickHandler(SSGlobals glob, Map map)
	{
		fMap = map;
		fGlobals = glob;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		Tile tile = fMap.getTile(e.getPoint());
		if (tile != null)
		{
			fGlobals.getStreetFactory().createStreet(tile, fGlobals.getSelectedStreetTemplate());
            fGlobals.getMap().handleAddition();

		}

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
        fStartPoint = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		fEndPoint = e.getPoint();
        Tile startTile = fMap.getTile(fStartPoint);
        Tile endTile = fMap.getTile(fEndPoint);
        if( startTile != null && startTile.equals(endTile))
        {
            mouseClicked(e);
        }else
        {
            Line2D.Double line = new Line2D.Double(fStartPoint,fEndPoint);
            for (Tile[] tiles : fMap.getTiles())
            {
                for (Tile tile : tiles)
                {
                    if(line.intersects(tile.getRect()))
                    {
                        fGlobals.getStreetFactory().createStreet(tile, fGlobals.getSelectedStreetTemplate());
                    }
                }
            }
        }
        fGlobals.getMap().handleAddition();

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
