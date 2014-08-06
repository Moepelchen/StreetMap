/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.car;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import streetmap.SSGlobals;
import streetmap.interfaces.IPrintable;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Short description in a complete sentence.
 * <p/>
 * Purpose of this class / interface
 * Say how it should be and should'nt be used
 * <p/>
 * Known bugs: Noneu
 * Historical remarks: None
 *
 * @author Ulrich Tewes
 * @version 1.0
 * @since Release
 */

public class CarRenderBuffer
{
	private static List<RenderStuff> fFreeStuff = new ArrayList<>();

	public static RenderStuff initBuffers(SSGlobals globals, List<IPrintable> cars)
	{

		RenderStuff stuff = new RenderStuff();

		float[] vertices = new float[cars.size() * 16];

		// OpenGL expects to draw vertices in counter clockwise order by default
		int[] indices = new int[cars.size() * 6];
		float[] colors = new float[cars.size() * 16];

		float length;
		for (int i = 0; i < cars.size(); i++)
		{
			IPrintable car = cars.get(i);
			Point2D position = car.getPosition();
			float x = (float) position.getX();
			float y = (float) position.getY();
			length = car.getLength();

			int offset = 16 * i;
			double rotation = car.getAngle();

			colors[offset] = 1f;
			colors[1+offset] = 0f;
			colors[2+offset] = 0f;
			colors[3+offset] = 1f;

			colors[4+offset] = 0f;
			colors[5+offset] = 1f;
			colors[6+offset] = 0f;
			colors[7+offset] = 1f;

			colors[8+offset] = 0f;
			colors[9+offset] = 0f;
			colors[10+offset] = 1f;
			colors[11+offset] = 1f;

			colors[12+offset] = 1f;
			colors[13+offset] = 1f;
			colors[14+offset] = 1f;
			colors[15+offset] = 1f;

			Matrix4f transMat = new Matrix4f();

			transMat.rotate((float) rotation, new Vector3f(0, 0, 1));

			Vector4f pos1 = new Vector4f(0, 0, 0, 0);
			Vector4f pos2 = new Vector4f(0 + length, 0, 0, 0);
			Vector4f pos3 = new Vector4f(0 + length, 0 + length, 0, 0);
			Vector4f pos4 = new Vector4f(0, 0 + length, 0, 0);

			Matrix4f.transform(transMat, pos1, pos1);
			Matrix4f.transform(transMat, pos2, pos2);
			Matrix4f.transform(transMat, pos3, pos3);
			Matrix4f.transform(transMat, pos4, pos4);

			pos1.translate(x, y, 0, 0);
			pos2.translate(x, y, 0, 0);
			pos3.translate(x, y, 0, 0);
			pos4.translate(x, y, 0, 0);

			vertices[offset] = pos1.getX();
			vertices[1 + offset] = pos1.getY();
			vertices[2 + offset] = 0;
			vertices[3 + offset] = 1f;

			vertices[4 + offset] = pos2.getX();
			vertices[5 + offset] = pos2.getY();
			vertices[6 + offset] = 0;
			vertices[7 + offset] = 1f;


			vertices[8 + offset] = pos3.getX();
			vertices[9 + offset] = pos3.getY();
			vertices[10 + offset] = 0;
			vertices[11 + offset] = 1f;

			vertices[12 + offset] = pos4.getX();
			vertices[13 + offset] = pos4.getY();
			vertices[14 + offset] = 0;
			vertices[15 + offset] = 1f;


			int verticesOffset = 4 * i;
			int indiciesOffset = 6 * i;
			indices[indiciesOffset] = (verticesOffset);
			indices[1 + indiciesOffset] = (1 + verticesOffset);
			indices[2 + indiciesOffset] = (2 + verticesOffset);
			indices[3 + indiciesOffset] = (2 + verticesOffset);
			indices[4 + indiciesOffset] = (3 + verticesOffset);
			indices[5 + indiciesOffset] = (verticesOffset);

		}

		FloatBuffer colorsBuffer = BufferUtils.createFloatBuffer(colors.length);
		colorsBuffer.put(colors);
		colorsBuffer.flip();

		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices);
		verticesBuffer.flip();

		int fIndicesCount = indices.length;
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(fIndicesCount);
		indicesBuffer.put(indices);
		indicesBuffer.flip();

		// Create a new Vertex Array Object in memory and select it (bind)
		// A VAO can have up to 16 attributes (VBO's) assigned to it by default
		int fVAOId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(fVAOId);

		// Create a new Vertex Buffer Object in memory and select it (bind)
		// A VBO is a collection of Vectors which in this case resemble the location of each vertex.
		int fVBOId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, fVBOId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STREAM_DRAW);
		// Put the VBO in the attributes list at index 0
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		// Create a new VBO for the indices and select it (bind) - COLORS
		int vbocId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbocId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorsBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		// Deselect (bind to 0) the VAO
		GL30.glBindVertexArray(0);

		int fVBOId2 = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, fVBOId2);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STREAM_DRAW);
		// Deselect (bind to 0) the VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		stuff.init(fVAOId, fVBOId, fVBOId2, fIndicesCount, vbocId);
		stuff.setPID(globals.getGame().getPID());
		return stuff;
	}

	public static int loadShader(String filename, int type)
	{
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			reader.close();
		}
		catch (IOException e)
		{
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}

		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);

		return shaderID;
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

} //CarRenderBuffer
