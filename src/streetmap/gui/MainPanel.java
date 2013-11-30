package streetmap.gui;

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

    private StreetPanel fStreetPanel;
    private MenuPanel fMenuPanel;

    /**
	 * Constructor to return one MainPanel
	 *
	 * @param globals current globals
	 */
	public MainPanel(SSGlobals globals)
	{
		this.setLayout(new BorderLayout(5, 5));
		this.getContentPane().add(new Map(globals));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fStreetPanel = new StreetPanel(globals);
        fMenuPanel = new MenuPanel(globals);


        this.getContentPane().add(fStreetPanel, BorderLayout.PAGE_END);
        this.getContentPane().add(fMenuPanel, BorderLayout.LINE_END);
		this.pack();
		this.setVisible(true);

        globals.setMainPanel(this);


	}

	public static void main(String[] args)
	{
		SSGlobals globals = null;
		try
		{
			globals = new SSGlobals();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		MainPanel main = new MainPanel(globals);

	}

    public void update(SSGlobals globals)
    {
       fMenuPanel.update(globals);
    }
}
