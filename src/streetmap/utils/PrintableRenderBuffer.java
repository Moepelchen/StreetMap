/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import streetmap.SSGlobals;
import streetmap.car.Car;
import streetmap.heatmap.Gradient;
import streetmap.interfaces.IPrintable;
import streetmap.map.street.Lane;
import streetmap.map.tile.Tile;
import streetmap.pathfinding.IPathFindingAlgorithm;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

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

public class PrintableRenderBuffer
{
	public static RenderStuff initBuffers(SSGlobals globals, List<IPrintable> printables)
	{
		return initBuffers(globals, printables, false);
	}

	public static RenderStuff initBuffers(SSGlobals globals, List<IPrintable> printables, boolean center)
	{
		RenderStuff stuff = null;
		if (printables.size() > 0)
		{
			stuff = new RenderStuff();

			FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(4*printables.size()*TexturedVertex.elementCount);

			// OpenGL expects to draw vertices in counter clockwise order by default
			int[] indices = new int[printables.size() * 6];

			float length;
			float height;
			for (int i = 0; i < printables.size(); i++)
			{
				IPrintable printable = printables.get(i);
				Point2D position = printable.getPosition();
				float screenHeight = globals.getGame().getHeight();
				float screenWidth = globals.getGame().getWidth();
				float x = (float) (position.getX());
				float y = (float) (position.getY());
				length = printable.getLength();
				height = printable.getWidth();

				double rotation = printable.getAngle();

				ReadableColor color = printable.getColor();

				Matrix4f transMat = new Matrix4f();

				transMat.rotate((float) rotation, new Vector3f(0, 0, 1));
				if(center)
					transMat.translate(new Vector3f(-length/2,-height/2,0));

				Vector4f pos1 = new Vector4f(0, 0, 0, 1f);
				Vector4f pos2 = new Vector4f(0 + length, 0, 0, 1f);
				Vector4f pos3 = new Vector4f(0 + length, 0 + height, 0, 1f);
				Vector4f pos4 = new Vector4f(0, 0 + height, 0, 1f);

				Matrix4f.transform(transMat, pos1, pos1);
				Matrix4f.transform(transMat, pos2, pos2);
				Matrix4f.transform(transMat, pos3, pos3);
				Matrix4f.transform(transMat, pos4, pos4);

				pos1 = scale(x, y, pos1, screenHeight, screenWidth);
				pos2 = scale(x, y, pos2, screenHeight, screenWidth);
				pos3 = scale(x, y, pos3, screenHeight, screenWidth);
				pos4 = scale(x, y, pos4, screenHeight, screenWidth);

				TexturedVertex vert1 = new TexturedVertex(pos1,color);

				TexturedVertex vert2 = new TexturedVertex(pos2,color);

				TexturedVertex vert3 = new TexturedVertex(pos3,color);

				TexturedVertex vert4 = new TexturedVertex(pos4, color);
				float v = printable.getStepWidth();
				vert4.setUV(0 , v * 2 * printable.getImageId() );

				vert3.setUV(1, v * 2 * printable.getImageId());

				vert2.setUV(1 , v * 2 + 2*v* printable.getImageId());

				vert1.setUV(0 , v * 2+ 2*v* printable.getImageId());



				verticesBuffer.put(vert1.getElements());
				verticesBuffer.put(vert2.getElements());
				verticesBuffer.put(vert3.getElements());
				verticesBuffer.put(vert4.getElements());

				int verticesOffset = 4 * i;
				int indiciesOffset = 6 * i;
				indices[indiciesOffset] = (verticesOffset);
				indices[1 + indiciesOffset] = (1 + verticesOffset);
				indices[2 + indiciesOffset] = (2 + verticesOffset);
				indices[3 + indiciesOffset] = (2 + verticesOffset);
				indices[4 + indiciesOffset] = (3 + verticesOffset);
				indices[5 + indiciesOffset] = (verticesOffset);

			}
			verticesBuffer.flip();

			int fIndicesCount = indices.length;
			IntBuffer indicesBuffer = BufferUtils.createIntBuffer(fIndicesCount);
			indicesBuffer.put(indices);
			indicesBuffer.flip();

				// Create a new Vertex Array Object in memory and select it (bind)
			int vaoId = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vaoId);

			// Create a new Vertex Buffer Object in memory and select it (bind)
			int vboId = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

			// Put the position coordinates in attribute list 0
			GL20.glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL11.GL_FLOAT,
					false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
			// Put the color components in attribute list 1
			GL20.glVertexAttribPointer(1, TexturedVertex.colorElementCount, GL11.GL_FLOAT,
					false, TexturedVertex.stride, TexturedVertex.colorByteOffset);
			// Put the texture coordinates in attribute list 2
			GL20.glVertexAttribPointer(2, TexturedVertex.textureElementCount, GL11.GL_FLOAT,
					false, TexturedVertex.stride, TexturedVertex.textureByteOffset);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			// Deselect (bind to 0) the VAO
			GL30.glBindVertexArray(0);

			// Create a new VBO for the indices and select it (bind) - INDICES
			int vboiId = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

			stuff.setPID(globals.getGame().getPID());
			stuff.init(vaoId, vboId, vboiId, fIndicesCount);
		}
		return stuff;
	}

	private static Vector4f scale(float x, float y, Vector4f pos1, float height, float width)
	{
		Vector4f translate = pos1.translate(x, y, 0, 0);
		pos1.set(pos1.getX() / width, pos1.getY() / height);
		return translate;
	}

	public static int loadShader(String filename, int type)
	{
		StringBuilder shaderSource = new StringBuilder();
		int shaderID;

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

		int status = GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS);
		if (status == GL11.GL_FALSE)
		{
			System.out.println("Shader not compiled!");
			System.exit(-1);
		}

		return shaderID;
	}

    public static void drawPaths(SSGlobals globals, List<IPrintable> cars)
    {

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