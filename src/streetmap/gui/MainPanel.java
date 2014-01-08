package streetmap.gui;

import org.lwjgl.LWJGLException;
import streetmap.Game;
import streetmap.SSGlobals;
import streetmap.map.Map;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/20/11
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Main frame which contains all other gui components
 */
public class MainPanel extends JFrame
{
    private MenuPanel fMenuPanel;

    /**
	 * Constructor to return one MainPanel
	 *
	 * @param globals current globals
	 */
	public MainPanel(SSGlobals globals)
	{
		this.setLayout(new BorderLayout(5, 5));
		//this.getContentPane().add();
        new Map(globals);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fMenuPanel = new MenuPanel(globals);

        this.getContentPane().add(fMenuPanel, BorderLayout.LINE_END);
		this.pack();
		this.setVisible(true);

        globals.setMainPanel(this);


	}

    public void update(SSGlobals globals)
    {
       fMenuPanel.update(globals);
    }
}
