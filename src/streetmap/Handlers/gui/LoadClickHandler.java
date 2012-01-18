package streetmap.Handlers.gui;

import org.xml.sax.SAXException;
import streetmap.Handlers.Filter.XMLFileFilter;
import streetmap.LoadSaveHandling.config.ConfigLoader;
import streetmap.LoadSaveHandling.map.MapLoader;
import streetmap.SSGlobals;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/5/12
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * ClickHandler which handles all interaction with the save button
 */
public class LoadClickHandler extends ClickHandler
{
	//Create a file chooser
	final JFileChooser fFileChooser = new JFileChooser("./");

	public LoadClickHandler(SSGlobals globals)
	{
		super(globals);
		fFileChooser.setAcceptAllFileFilterUsed(false);
		fFileChooser.addChoosableFileFilter(new XMLFileFilter());
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

		int returnVal = fFileChooser.showOpenDialog((Component) e.getSource());

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = fFileChooser.getSelectedFile();
			if (file != null)
			{
				MapLoader mapLoader = new MapLoader();
				ConfigLoader configLoader = new ConfigLoader();
				try
				{
					configLoader.load(file, fGlobals);
					mapLoader.load(file, fGlobals);
				} catch (ParserConfigurationException e1)
				{
					e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (IOException e1)
				{
					e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (SAXException e1)
				{
					e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (NoSuchMethodException e1)
				{
					e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (IllegalAccessException e1)
				{
					e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (InvocationTargetException e1)
				{
					e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}
		}

	}
}
