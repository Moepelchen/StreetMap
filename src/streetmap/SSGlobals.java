package streetmap;

import de.lessvoid.nifty.Nifty;
import streetmap.config.ChangeableConfig;
import streetmap.config.IChangeableConfig;
import streetmap.config.StreetConfig;
import streetmap.map.Map;
import streetmap.map.street.StreetFactory;
import streetmap.timehandling.ITimeHandler;
import streetmap.timehandling.TimeHandler;

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
    /**
     * Current game beeing played
     */
    private Game fGame;

    private TimeHandler fTimeHandler;
	private Nifty fNifty;

	public void resetPlayer()
	{
		fGame.getPlayer().reset();
	}

	public void setNifty(Nifty nifty)
	{
		fNifty = nifty;
	}

	public Nifty getNifty()
	{
		return fNifty;
	}

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
        fTimeHandler = new TimeHandler();
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

	public void setMap(Map map)
	{
		this.fMap = map;
	}

	public Map getMap()
	{
		return fMap;
	}

    public void handleLoading()
    {
        this.getMap().handleAddition(null);
    }

    public ITimeHandler getTimeHandler()
    {
        return fTimeHandler;
    }
}
