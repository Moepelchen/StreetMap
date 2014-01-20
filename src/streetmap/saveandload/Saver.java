/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.saveandload;

import streetmap.SSGlobals;
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
	public boolean save(SSGlobals globals, String fileName)
	{
		boolean couldBeCreated = false;
		try
		{
			File file = new File("./save/" + fileName + ".xml");
			if (!file.exists())
			{

				file.createNewFile();

			}
			BufferedWriter output;

			output = new BufferedWriter(new FileWriter(file));

			save(output, globals);

			couldBeCreated = true;

		}
		catch (IOException e1)
		{
			couldBeCreated = false;
		}
		return couldBeCreated;
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
