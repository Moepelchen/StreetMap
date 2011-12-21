package streetmap;

import streetmap.Interfaces.IConfig;
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


public class SSGlobals {
    private IConfig fConfig;

    private StreetConfig fStreetConfig;

    private StreetFactory fStreetFactory;
	
	private String fSelectedStreetTemplate;

	public SSGlobals() throws FileNotFoundException {
        fConfig = new Config(this);
        fStreetConfig = new StreetConfig(this);
        fStreetFactory = new StreetFactory(this);
    }

    public IConfig getConfig() {
        return fConfig;
    }

    public StreetConfig getStreetConfig() {
        return fStreetConfig;
    }

    public StreetFactory getStreetFactory() {
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
}
