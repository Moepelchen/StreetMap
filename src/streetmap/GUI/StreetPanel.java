package streetmap.GUI;

import streetmap.Handlers.gui.StreetSelectClickHandler;
import streetmap.SSGlobals;
import streetmap.Tile;
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

/**
 * This class contains all available streets for selection
 */
public class StreetPanel extends JPanel implements MouseListener
{
    /**
     * Size of each selectable Tile/Street
     */
	private static final int GUI_TILE_WIDTH = 50;

    /**
     * Current globals
     */
	private SSGlobals fGlobals;

    /**
     * Collection of all ClickHandlers for the Streets
     */
	private StreetSelectClickHandler[] fHandlers;

    /**
     * Constructor
     * @param globals current globals
     */
	public StreetPanel(SSGlobals globals)
	{

		fGlobals = globals;
		fHandlers = new StreetSelectClickHandler[fGlobals.getStreetConfig().getTemplates().size()];
		this.setBounds(0, 0, 500, 200);
		this.setPreferredSize(new Dimension((int) (GUI_TILE_WIDTH * fHandlers.length), 200));
		this.setVisible(true);
		createHandlers();
		this.addMouseListener(this);


	}

    /**
     * This method creates a ClickHandler for each available streets
     */
	private void createHandlers()
	{
		Collection<StreetTemplate> streetTemplates = fGlobals.getStreetConfig().getTemplates();
		int count = 0;
		for (StreetTemplate streetTemplate : streetTemplates)
		{

			Tile tile = new Tile(fGlobals, null, new Point2D.Double(count, 0), GUI_TILE_WIDTH);
			tile.setStreet(fGlobals.getStreetFactory().createStreet(tile, streetTemplate.getName()));
			fHandlers[count] = new StreetSelectClickHandler(fGlobals, tile);
			count++;
		}
	}

	public void paint(Graphics g)
	{

		for (StreetSelectClickHandler fHandler : fHandlers)
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

		int arryPos = (int) (x / GUI_TILE_WIDTH);
		if (y < GUI_TILE_WIDTH)
		{
			StreetSelectClickHandler handler = fHandlers[arryPos];
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
