package streetmap.gui.controller;

import de.lessvoid.nifty.controls.ListBox;
import org.xml.sax.SAXException;
import streetmap.SSGlobals;
import streetmap.gui.IScreenNames;
import streetmap.saveandload.config.ConfigLoader;
import streetmap.saveandload.map.MapLoader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by ulrichtewes on 19.01.14.
 */
public class LoadScreenController extends AbstractScreenController
{
    public LoadScreenController(SSGlobals globals)
    {
        super(globals);
    }

    @Override
    protected String getEscapeScreen()
    {
        return IScreenNames.SCREEN_MENU;
    }

    @Override
    protected void postScreenActivation()
    {

    }

    @Override
    public void onStartScreen()
    {
        super.onStartScreen();
        ListBox<File> loadBox = getLoadBox();
        File saveDirectory = new File("./save");
        File[] files = saveDirectory.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return pathname.toString().endsWith(".xml");
            }
        });

        for (File file : files)
        {
            loadBox.addItem(file);
        }

    }

	protected ListBox<File> getLoadBox()
	{
		return (ListBox<File>)fNifty.getCurrentScreen().findNiftyControl("loadbox", ListBox.class);
	}

	@Override
    public void onEndScreen()
    {
	    ListBox loadBox = getLoadBox();
	    if (loadBox != null)
	    {
		    loadBox.clear();
	    }
    }

    public void load()
    {
        ListBox loadBox = getLoadBox();

        List files = loadBox.getSelection();
        if(files.size() == 1)
        {
            File file = (File) files.get(0);
            if (file != null)
            {
                MapLoader mapLoader = new MapLoader();
                ConfigLoader configLoader = new ConfigLoader();
                try
                {
                    configLoader.load(file, getGlobals());
                    mapLoader.load(file, getGlobals());

                    getGlobals().handleLoading();
                }
                catch (ParserConfigurationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | SAXException | IOException e1)
                {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                super.activateScreenAndUnPause(IScreenNames.SCREEN_GAME);
            }
        }
    }
}
