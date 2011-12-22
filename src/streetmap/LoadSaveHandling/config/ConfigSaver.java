package streetmap.LoadSaveHandling.config;

import streetmap.Interfaces.config.IConfig;
import streetmap.Interfaces.save.ISaveConstants;
import streetmap.config.Config;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigSaver
{


	public static void saveConfig(Writer out,IConfig config){
		for (Method method : config.getClass().getMethods())
		{
			if(method.getDeclaringClass().equals(Config.class)){
				System.out.println("method = " + method.getName() +"  "+method.getDeclaringClass());
				try
				{
					out.write(String.valueOf(method.invoke(config)));
					out.write(ISaveConstants.CONFIG_SEPERATOR);
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (InvocationTargetException e)
				{
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (IOException e)
				{
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}
		}
	}
}
