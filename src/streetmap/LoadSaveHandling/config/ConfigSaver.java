package streetmap.LoadSaveHandling.config;

import streetmap.Interfaces.config.IConfig;
import streetmap.Interfaces.save.ISaveConstants;
import streetmap.config.Config;

import java.io.BufferedWriter;
import java.io.IOException;
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

	public static void saveConfig(BufferedWriter out, IConfig config)
	{
		try
		{
			beginConfigTag(out);

			for (Method method : config.getClass().getMethods())
			{
				if (method.getDeclaringClass().equals(Config.class))
				{
					writeMethodStartTag(method.getName(), out);
					out.write(String.valueOf(method.invoke(config)));
					writeMethodEndTag(method.getName(), out);
				}
			}
			endConfigTag(out);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	private static void endConfigTag(BufferedWriter out) throws IOException
	{
		out.write("</");
		out.write(ISaveConstants.END_CONFIG_TAG);
		out.write(">");

	}

	private static void beginConfigTag(BufferedWriter out) throws IOException
	{
		out.write("<");
		out.write(ISaveConstants.CONFIG_TAG);
		out.write(">");
	}

	private static void writeMethodEndTag(String name, BufferedWriter out) throws IOException
	{
		out.write("</" + name + ">");
	}

	private static void writeMethodStartTag(String name, BufferedWriter out) throws IOException
	{
		out.write("<" + name + ">");
	}
}
