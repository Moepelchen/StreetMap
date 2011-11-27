package streetmap;

import streetmap.config.Config;
import streetmap.config.StreetConfig;
import streetmap.Interfaces.*;

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

    public SSGlobals() throws FileNotFoundException {
        fConfig = new Config(this);
        fStreetConfig = new StreetConfig(this);
    }

    public IConfig getConfig() {
        return fConfig;
    }

   public StreetConfig getStreetConfig(){
       return fStreetConfig;
   }
}
