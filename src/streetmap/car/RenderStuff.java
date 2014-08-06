/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.car;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
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

	public void init(int fVAOId, int fVBOId, int fVBOId2, int fIndicesCount)
	{

		fFVAOId = fVAOId;
		fFVBOId = fVBOId;
		fFVBOId2 = fVBOId2;
		fFIndicesCount = fIndicesCount;
	}

	public void release()
	{

		// Select the VAO
		GL30.glBindVertexArray(fFVAOId);

		// Disable the VBO index from the VAO attributes list
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);

		// Delete the vertex VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(fFVBOId);

		// Delete the index VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(fFVBOId2);

		// Delete the VAO
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(fFVAOId);
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
