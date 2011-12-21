package streetmap;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/21/11
 * Time: 8:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class MapClickHandler implements MouseListener
{
	private Map fMap;

	private SSGlobals fGlobals;

	public MapClickHandler(SSGlobals glob, Map map){
		fMap = map;
		fGlobals = glob;
	}
	@Override
	public void mouseClicked(MouseEvent e)
	{
		double arrayX = (int) (e.getX() / fGlobals.getConfig().getTileSize());
		double arrayY = (int) (e.getY() / fGlobals.getConfig().getTileSize());
		Tile tile = fMap.getTile(arrayX,arrayY);
		if(tile != null){
			fGlobals.getStreetFactory().createStreet(tile,fGlobals.getSelectedStreetTemplate());

		}

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		mouseClicked(e);
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		//To change body of implemented methods use File | Settings | File Templates.
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
