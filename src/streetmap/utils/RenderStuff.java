/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.utils;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

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
public class RenderStuff
{
	private int fFVAOId;
	private int fFVBOId;
	private int fPID;

	private int fVBOCId;

	public int getFVAOId()
	{
		return fFVAOId;
	}

	public int getFVBOId()
	{
		return fFVBOId;
	}

	public int getFVBOId2()
	{
		return fFVBOId2;
	}

	public int getFIndicesCount()
	{
		return fFIndicesCount;
	}

	private int fFVBOId2;
	private int fFIndicesCount;

	public RenderStuff init(int fVAOId, int fVBOId, int fVBOId2, int fIndicesCount)
	{

		fFVAOId = fVAOId;
		fFVBOId = fVBOId;
		fFVBOId2 = fVBOId2;
		fFIndicesCount = fIndicesCount;

		return this;
	}

	public void release()
	{

		// Select the VAO
		GL30.glBindVertexArray(fFVAOId);

		// Delete the vertex VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(fFVBOId);

		// Delete the index VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(fFVBOId2);

		// Delete the index VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(fVBOCId);

		// Delete the VAO
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(fFVAOId);
	}

	public int getPID()
	{
		return fPID;
	}

	public RenderStuff setPID(int PID)
	{
		fPID = PID;
		return this;
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

} //RenderStuff