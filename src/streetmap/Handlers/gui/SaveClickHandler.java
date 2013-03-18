package streetmap.Handlers.gui;

import streetmap.Interfaces.save.ISaveConstants;
import streetmap.LoadSaveHandling.config.ConfigSaver;
import streetmap.LoadSaveHandling.map.MapSaver;
import streetmap.SSGlobals;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class SaveClickHandler extends ClickHandler
{

	public SaveClickHandler(SSGlobals globals)
	{
		super(globals);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			File file = new File("./save/" + getSaveFileName() + ".xml");
			if (!file.exists())
			{

				file.createNewFile();

			}
			BufferedWriter output;


			output = new BufferedWriter(new FileWriter(file));

			beginMapTag(output);
			MapSaver.saveMap(output, fGlobals.getMap());
			output.newLine();
			ConfigSaver.saveConfig(output, fGlobals.getConfig());
			endMapTag(output);

			output.close();

		} catch (IOException e1)
		{
			e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	private void beginMapTag(BufferedWriter output) throws IOException
	{
		output.write("<");
		output.write(ISaveConstants.MAP_TAG);
		output.write(">");
	}

	private void endMapTag(BufferedWriter output) throws IOException
	{
		output.write("</");
		output.write(ISaveConstants.MAP_TAG);
		output.write(">");
	}

	public String getSaveFileName()
	{
		return String.valueOf(System.currentTimeMillis());
	}
}
