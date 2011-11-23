import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 11/22/11
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */


public class Globals {
    private IConfig fConfig;

    private StreetConfiguration fStreetConfig;

    public Globals() throws FileNotFoundException {
        fConfig = new Configuration();
        fStreetConfig = new StreetConfiguration(this);
    }

    public IConfig getfConfig() {
        return fConfig;
    }

   public StreetConfiguration getfStreetConfig(){
       return fStreetConfig;
   }
}
