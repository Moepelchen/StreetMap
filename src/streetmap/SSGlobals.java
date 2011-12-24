package streetmap;

import streetmap.Interfaces.config.IConfig;
import streetmap.config.ChangeableConfig;
import streetmap.config.Config;
import streetmap.config.StreetConfig;

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
	private IConfig fConfig;
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

	public IConfig getConfig()
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
}
