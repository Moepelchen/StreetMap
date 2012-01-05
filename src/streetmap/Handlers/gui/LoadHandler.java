package streetmap.Handlers.gui;

import org.xml.sax.SAXException;
import streetmap.Handlers.Filter.TextFileFilter;
import streetmap.LoadSaveHandling.map.MapLoader;
import streetmap.SSGlobals;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/5/12
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoadHandler extends ClickHandler
{
	//Create a file chooser
	final JFileChooser fFileChooser = new JFileChooser();

	public LoadHandler(SSGlobals globals)
	{
		super(globals);
		fFileChooser.setAcceptAllFileFilterUsed(false);
		fFileChooser.addChoosableFileFilter(new TextFileFilter());


	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
	int returnVal = fFileChooser.showOpenDialog((Component) e.getSource());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fFileChooser.getSelectedFile();
            if(file != null){
	            MapLoader mapLoader= new MapLoader();
	            try
	            {
		            mapLoader.loadMap(file,fGlobals);
	            } catch (ParserConfigurationException e1)
	            {
		            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	            } catch (IOException e1)
	            {
		            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	            } catch (SAXException e1)
	            {
		            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	            }
            }
        }

	}
}
