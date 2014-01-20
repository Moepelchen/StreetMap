package streetmap.saveandload.config;

import streetmap.config.Config;
import streetmap.config.IConfig;
import streetmap.saveandload.ISaveConstants;
import streetmap.saveandload.AbstractSaver;

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
public class ConfigSaver extends AbstractSaver
{

	public void save(BufferedWriter out, Object object) throws IOException
	{
		IConfig config = (IConfig) object;

		try
		{
			beginConfigTag(out);

			for (Method method : config.getClass().getMethods())
			{
				if (method.getDeclaringClass().equals(Config.class))
				{
					writeStartTag(method.getName(), out);
					out.write(String.valueOf(method.invoke(config)));
					writeEndTag(method.getName(), out);
				}
			}
			endConfigTag(out);
		}
		catch (IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}

	private static void endConfigTag(BufferedWriter out) throws IOException
	{
		writeEndTag(ISaveConstants.CONFIG_TAG, out);

	}

	private static void beginConfigTag(BufferedWriter out) throws IOException
	{
		writeStartTag(ISaveConstants.CONFIG_TAG, out);
	}

}
