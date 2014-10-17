/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.saveandload;

import java.io.BufferedWriter;
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
public abstract class AbstractSaver
{

	public abstract void save(BufferedWriter out, Object object) throws IOException;

	private static void writeStart(BufferedWriter output) throws IOException
	{
		output.write("<");
	}

	private static void writeStartEnd(BufferedWriter output) throws IOException
	{
		output.write(">");
	}

	private static void writeEnd(BufferedWriter output) throws IOException
	{
		output.write("</");
	}

	protected static void writeEndTag(String name, BufferedWriter out) throws IOException
	{
		writeEnd(out);
		out.write(name);
		writeStartEnd(out);
	}

	protected static void writeStartTag(String name, BufferedWriter out) throws IOException
	{
		writeStart(out);
		out.write(name);
		writeStartEnd(out);
	}
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
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //Saver
