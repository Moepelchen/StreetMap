package streetmap.utils;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/5/12
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionUtils
{
	public static Object cast(String obj, Class clazz)
	{
		if (clazz.equals(String.class))
		{
			return obj;
		}
		else if (clazz.equals(boolean.class))
		{
			return Boolean.parseBoolean(obj.toString());
		}
		else if (clazz.equals(Boolean.class))
		{
			return (Boolean) Boolean.parseBoolean(obj.toString());
		}
		else if (clazz.equals(Double.class))
		{
			return (Double) Double.parseDouble(obj.toString());
		}
		return obj;
	}

}
