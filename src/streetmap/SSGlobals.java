package streetmap;

import streetmap.gui.MainPanel;
import streetmap.interfaces.config.IChangeableConfig;
import streetmap.interfaces.config.IConfig;
import streetmap.config.ChangeableConfig;
import streetmap.config.StreetConfig;
import streetmap.map.Map;
import streetmap.map.street.StreetFactory;

import java.awt.*;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 11/22/11
 * Time: 11:33 AM
 * SS stands for StreetSimulator ;)
 */

public class SSGlobals
{
	/**
	 * the configuration
	 */
	private IChangeableConfig fConfig;
	/**
	 * the streets the user is able to place
	 */
	private StreetConfig fStreetConfig;
	/**
	 * the factory used to create streets
	 */
	private StreetFactory fStreetFactory;
	/**
	 * the currently selected street
	 */
	private String fSelectedStreetTemplate;
	/**
	 * the map where the magic happens
	 */
	private Map fMap;
    private MainPanel fMainPanel;
    private Game fGame;

    /**
	 * Constructor
	 *
	 * @throws FileNotFoundException is thrown when either the config or the streetconfig file is not found
	 */
	public SSGlobals() throws FileNotFoundException
	{
		fConfig = new ChangeableConfig(this);
		fStreetConfig = new StreetConfig(this);
		fStreetFactory = new StreetFactory(this);
	}

    public void setGame(Game fGame)
    {
        this.fGame = fGame;
    }

    public Game getGame()
    {
        return fGame;
    }

    public IChangeableConfig getConfig()
	{
		return fConfig;
	}

	public StreetConfig getStreetConfig()
	{
		return fStreetConfig;
	}

	public StreetFactory getStreetFactory()
	{
		return fStreetFactory;
	}

	public void setSelectedStreetTemplate(String s)
	{
		fSelectedStreetTemplate = s;
	}

	public String getSelectedStreetTemplate()
	{
		return fSelectedStreetTemplate;
	}

	public void setMap(Map map)
	{
		this.fMap = map;
	}

	public Map getMap()
	{
		return fMap;
	}

    public void setMainPanel(MainPanel mainPanel)
    {
        fMainPanel = mainPanel;
    }

    public void handleLoading()
    {
        this.getMap().handleAddition();
        fMainPanel.update(this);
    }
}
