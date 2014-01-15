package streetmap.saveandload;

import org.xml.sax.SAXException;
import streetmap.SSGlobals;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/5/12
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ILoader
{
	public abstract boolean load(File file, SSGlobals glob) throws ParserConfigurationException, IOException, SAXException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

}
