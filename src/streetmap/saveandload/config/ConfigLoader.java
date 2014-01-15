package streetmap.saveandload.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import streetmap.interfaces.config.IChangeableConfig;
import streetmap.interfaces.save.ISaveConstants;
import streetmap.saveandload.ILoader;
import streetmap.SSGlobals;
import streetmap.utils.ReflectionUtils;
import streetmap.config.Config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/5/12
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigLoader implements ILoader
{

	@Override
	public boolean load(File file, SSGlobals glob) throws ParserConfigurationException, IOException, SAXException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{

		if (!(glob.getConfig() instanceof IChangeableConfig))
		{
			return false;
		}

		IChangeableConfig config = (IChangeableConfig) glob.getConfig();

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(file);

		doc.getDocumentElement().normalize();
		NodeList configList = doc.getElementsByTagName(ISaveConstants.CONFIG_TAG);

		if (configList.getLength() == 1)
		{
			Node configNode = configList.item(0);
			if (configNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element configElement = (Element) configNode;
				for (Method method : glob.getConfig().getClass().getMethods())
				{
					if (method.getDeclaringClass().equals(Config.class))
					{

						Node item = configElement.getElementsByTagName(method.getName()).item(0);
						if (item != null)
						{
							String value = item.getTextContent();
							int beginIndex = 3;
							if (method.getReturnType().equals(boolean.class)||method.getReturnType().equals(Boolean.class) )
							{
								beginIndex = 2;
							}
							String name = "set" + method.getName().substring(beginIndex);
							try
							{
								Method setMethod = config.getClass().getMethod(name, method.getReturnType());

								Object[] args = new Object[1];
								args[0] = ReflectionUtils.cast(value, method.getReturnType());
								setMethod.invoke(config, args);
							}
							catch (NoSuchMethodException e)
							{
                                System.out.println("e = " + e);
                            }
						}
					}
				}
			}
		}

		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
