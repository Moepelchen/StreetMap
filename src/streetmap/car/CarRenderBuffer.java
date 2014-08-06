/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.car;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.awt.geom.Point2D;
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

	public static RenderStuff initBuffers(List<Car> cars)
	{

			RenderStuff stuff = new RenderStuff();

			float[] vertices = new float[cars.size()*12];


			// OpenGL expects to draw vertices in counter clockwise order by default
			int[] indices = new int[cars.size()*6];

			float length;
			for (int i = 0; i < cars.size(); i++)
			{
				Car car = cars.get(i);
				Point2D position = car.getPosition();
				float x = (float) position.getX();
				float y = (float) position.getY();
						length = car.getLength();
				int offset = 12 * i;
				vertices[offset] = x;
				vertices[1 + offset] = y;
				vertices[2 + offset] = 0;

				vertices[3 + offset] = x+length;
				vertices[4 + offset] = y;
				vertices[5 + offset] = 0;

				vertices[6 + offset] = x+length;
				vertices[7 + offset] = y+length;
				vertices[8 + offset] = 0;

				vertices[9 + offset] = x;
				vertices[10 + offset] = y+length;
				vertices[11 + offset] = 0;

				int verticesOffset = 4*i;
				int indiciesOffset = 6*i;
				indices[indiciesOffset] =  (verticesOffset);
				indices[1+ indiciesOffset] =  (1+ verticesOffset);
				indices[2+ indiciesOffset] =  (2+ verticesOffset);
				indices[3+ indiciesOffset] =  (2+ verticesOffset);
				indices[4+ indiciesOffset] = (3+ verticesOffset);
				indices[5+ indiciesOffset] =(verticesOffset);
			}

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
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer,  GL15.GL_STREAM_DRAW);
			// Put the VBO in the attributes list at index 0
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			// Deselect (bind to 0) the VBO
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			// Deselect (bind to 0) the VAO
			GL30.glBindVertexArray(0);

			int fVBOId2 = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, fVBOId2);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STREAM_DRAW);
			// Deselect (bind to 0) the VBO
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

			stuff.init(fVAOId, fVBOId, fVBOId2, fIndicesCount);
			return stuff;
	}

	public static void release(RenderStuff renderStuff)
	{
		fFreeStuff.add(renderStuff);
		if(fFreeStuff.size() >10)
		{
			RenderStuff o = fFreeStuff.get(0);
			fFreeStuff.remove(o);
			o.release();
		}
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
