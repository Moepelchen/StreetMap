package streetmap.GUI;

import streetmap.Handlers.gui.LoadClickHandler;
import streetmap.Handlers.gui.SaveClickHandler;
import streetmap.SSGlobals;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * MenuPanel contains all GUI components for configuration/load/saving
 */
public class MenuPanel extends JPanel
{
	private SSGlobals fGlobals;

	public MenuPanel(SSGlobals glob)
	{
		fGlobals = glob;
		this.setPreferredSize(new Dimension(200, 500));
		JButton save = new JButton("Save");
		save.addActionListener(new SaveClickHandler(fGlobals));
		this.add(save);
		JButton load = new JButton("Load");
		load.addActionListener(new LoadClickHandler(fGlobals));
		this.add(load);
		this.setVisible(true);
	}

}
