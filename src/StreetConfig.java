import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: shifter
 * Date: 11/22/11
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class StreetConfig {
    
    private Globals fGlobals;
    private static final String STREET_CONFIG_NAME = "streets.xml";

    public StreetConfig(Globals glob) throws FileNotFoundException {
        fGlobals = glob;
        parse(glob.getfConfig().getStreetPath());
    }

    private void parse(String streetPath) throws FileNotFoundException {
        File file = new File(streetPath + STREET_CONFIG_NAME);
        if(!file.exists()){
            throw new FileNotFoundException();
        }
    }
}
