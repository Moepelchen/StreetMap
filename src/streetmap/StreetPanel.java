package streetmap;

import streetmap.xml.jaxb.StreetTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/20/11
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class StreetPanel extends JPanel implements MouseListener
{
	private SSGlobals fGlobals;

	private StreetSelectHandler[] fHandlers;

	public StreetPanel(SSGlobals globals)
	{

		fGlobals = globals;
		fHandlers = new StreetSelectHandler[fGlobals.getStreetConfig().getTemplates().size() * 2];
		this.setBounds(0, 0, 500, 200);
		this.setPreferredSize(new Dimension(200, 200));
		this.setVisible(true);
		createHandlers();
		this.addMouseListener(this);


	}

	private void createHandlers()
	{
		Collection<StreetTemplate> streetTemplates = fGlobals.getStreetConfig().getTemplates();
		int count = 0;
		for (StreetTemplate streetTemplate : streetTemplates)
		{

			Tile tile = new Tile(fGlobals, null, new Point2D.Double(count, 0));
			tile.setStreet(fGlobals.getStreetFactory().createStreet(tile, streetTemplate.getName()));
			fHandlers[count] = new StreetSelectHandler(fGlobals, tile);
			count++;
			count++;
		}
	}

	public void paint(Graphics g)
	{

		for (StreetSelectHandler fHandler : fHandlers)
		{
			if (fHandler != null)
				fHandler.print((Graphics2D) g);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();

		int arryPos = (int) (x / fGlobals.getConfig().getTileSize());
		if (y < fGlobals.getConfig().getTileSize())
		{
			StreetSelectHandler handler = fHandlers[arryPos];
			if (handler != null)
				handler.handleClick();
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
		// not implemented
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// not implemented
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// not implemented
	}
}
