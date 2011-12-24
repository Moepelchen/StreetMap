package streetmap.Handlers.gui;

import streetmap.Interfaces.save.ISaveConstants;
import streetmap.LoadSaveHandling.config.ConfigSaver;
import streetmap.LoadSaveHandling.map.MapSaver;
import streetmap.SSGlobals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class SaveHandler extends ClickHandler implements ActionListener
{


	private String saveFileName;

	public SaveHandler(SSGlobals globals)
	{
		super(globals);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			File file = new File("./save/" + getSaveFileName()+".txt");
			if (!file.exists())
			{

				file.createNewFile();

			}
			BufferedWriter output = null;


			output = new BufferedWriter(new FileWriter(file));


			MapSaver.saveMap(output, fGlobals.getMap());
            output.newLine();
            output.write(ISaveConstants.BLOCK_SEPERATOR);
            output.newLine();
			ConfigSaver.saveConfig(output, fGlobals.getConfig());


			output.close();

		} catch (IOException e1)
		{
			e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public String getSaveFileName()
	{
		return String.valueOf(System.currentTimeMillis());
	}
}
