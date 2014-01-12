package streetmap.gui;

import streetmap.handler.gui.LoadClickHandler;
import streetmap.handler.gui.SaveClickHandler;
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
    private final ConfigPanel fConfigPanel;
    private SSGlobals fGlobals;

    public MenuPanel(SSGlobals glob)
	{
        this.setLayout(new GridLayout(6,1));
		fGlobals = glob;
		this.setPreferredSize(new Dimension(300, 1000));
		JButton save = new JButton("Save");
		save.addActionListener(new SaveClickHandler(fGlobals));
		this.add(save);
		JButton load = new JButton("Load");
		load.addActionListener(new LoadClickHandler(fGlobals));
		this.add(load);
        fConfigPanel = new ConfigPanel(glob.getConfig());
        this.add(fConfigPanel);
		this.setVisible(true);
	}

    public void update(SSGlobals globals)
    {
        fConfigPanel.update(globals);
    }
}
