/*
 * Copyright (C) Ulrich Tewes GmbH a2010-2014.
 */

package streetmap.saveandload;

import streetmap.SSGlobals;
import streetmap.interfaces.save.ISaveConstants;
import streetmap.saveandload.config.ConfigSaver;
import streetmap.saveandload.map.MapSaver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Short description in a complete sentence.
 * <p/>
 * Purpose of this class / interface
 * Say how it should be and should'nt be used
 * <p/>
 * Known bugs: None
 * Historical remarks: None
 *
 * @author Ulrich Tewes
 * @version 1.0
 * @since Release
 */
public class Saver extends AbstractSaver
{
	// -----------------------------------------------------
// constants
// -----------------------------------------------------
// -----------------------------------------------------
// variables
// -----------------------------------------------------
// -----------------------------------------------------
// inner classes
// -----------------------------------------------------
// -----------------------------------------------------
// constructors
// -----------------------------------------------------
// -----------------------------------------------------
// methods
// -----------------------------------------------------
	public void save(SSGlobals globals)
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

			save(output, globals);

		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public String getSaveFileName()
	{
		return String.valueOf(System.currentTimeMillis());
	}

	@Override
	public void save(BufferedWriter output, Object object) throws IOException
	{
		SSGlobals globals = (SSGlobals) object;
		writeStartTag(ISaveConstants.MAP_TAG, output);
		new MapSaver().save(output, globals.getMap());
		output.newLine();
		new ConfigSaver().save(output, globals.getConfig());
		writeEndTag(ISaveConstants.MAP_TAG, output);

		output.close();
	}
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //Saver
