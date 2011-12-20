package streetmap;

import streetmap.xml.jaxb.StreetTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/20/11
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class StreetPanel extends JPanel
{
	private SSGlobals fGlobals;

	public StreetPanel(SSGlobals globals){

		fGlobals = globals;
		this.setBounds(0,0,500,200);
		this.setPreferredSize(new Dimension(200,200));
		this.setVisible(true);

	}

	public void paint(Graphics g){

		g.setColor(Color.black);
		Collection<StreetTemplate> streetTemplates = fGlobals.getStreetConfig().getTemplates();
		int count = 0;
		for (StreetTemplate streetTemplate : streetTemplates)
		{

			Tile tile = new Tile(fGlobals,null,new Point2D.Double(count,0));
			tile.setStreet(fGlobals.getStreetFactory().createStreet(tile,streetTemplate.getName()));
			tile.print((Graphics2D) g);
			count++;
			count++;
		}
	}

}
