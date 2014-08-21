package streetmap.utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import streetmap.car.RenderStuff;
import streetmap.car.TexturedVertex;
import streetmap.map.side.Anchor;
import streetmap.map.side.Side;
import streetmap.map.street.IPlaceable;
import streetmap.map.street.Street;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/6/12
 * Time: 8:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class DrawHelper
{

    /**
     * Image Storage
     */

    public static void drawCars(RenderStuff car, int textureId)
    {

	// Render

		GL20.glUseProgram(car.getPID());

		// Bind the texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

		// Bind to the VAO that has all the information about the vertices
		GL30.glBindVertexArray(car.getFVAOId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);

		// Bind to the index VBO that has all the information about the order of the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, car.getFVBOId2());

		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, car.getFIndicesCount(), GL11.GL_UNSIGNED_INT, 0);

		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);

		GL20.glUseProgram(0);
    }

    public static void drawAnchor(Anchor anchor)
    {
        // TODO
    }

    public static void drawSide(Side side)
    {
        // TODO
    }

    public static void drawStreet(Street street)
    {
        drawPlaceable(street, false);
    }

    public static void drawPlaceable(IPlaceable placeable, boolean forMenu)
    {
      /*  int textureId = 0;
        String imagePath;
        if (forMenu)
        {
            imagePath = street.getMenuImagePath();
        } else
        {
            imagePath = street.getImageId();
        }
        if(imagePath != null)
        {
            textureId = TextureCache.getTextureId(imagePath);
        }

	    float tileSize = (float) street.getTile().getWidth();
	    glPushMatrix();
	    if(!forMenu && street.getGlobals().getConfig().isShowHeatMap())
	    {
		    GL11.glBlendFunc(GL11.GL_ONE,GL11.GL_ONE_MINUS_SRC_ALPHA);
		    double heatMapReading = street.getGlobals().getMap().getHeatMapReading(street.getTile().getArrayPosition());
		    Color color = Gradient.GRADIENT_RAINBOW[(int)Math.floor(heatMapReading * (Gradient.GRADIENT_HOT.length-1))];
		    GL11.glColor4d(((double)color.getRed())/255,((double)color.getGreen())/255, ((double)color.getBlue())/255,0.5);
	    }
	    else
	    {
		    glColor3d(1, 1, 1);

	    }

	    if (imagePath != null)
	    {
		    float y = (float) (street.getTile().getArrayPosition().getY() * tileSize);
		    float x = (float) (street.getTile().getArrayPosition().getX() * tileSize);
		    glBindTexture(GL_TEXTURE_2D, textureId);
		    glBegin(GL_QUADS);
		    glTexCoord2f(0.0F, 0.0F);
		    glVertex3f(x, y, 0);
		    glTexCoord2f(1.0F, 0.0F);
		    glVertex3f(x + tileSize, y, 0);
		    glTexCoord2f(1.0F, 1.0F);
		    glVertex3f(x + tileSize, y + tileSize, 0);
		    glTexCoord2f(0.0F, 1.0F);
		    glVertex3f(x, y + tileSize, 0);
		    glEnd();
		    glBindTexture(GL_TEXTURE_2D, 0);
	    }
	    glColor3d(1, 1, 1);

	    glPopMatrix();

*/
    }

	public static RenderStuff setupQuad() {
		// We'll define our quad using 4 vertices of the custom 'TexturedVertex' class

		TexturedVertex v0 = new TexturedVertex();
		v0.setXYZ(-0.5f, 0.5f, 0);
		v0.setRGB(1, 0, 0);
		v0.setUV(0, 0);
		TexturedVertex v1 = new TexturedVertex();
		v1.setXYZ(-0.5f, -0.5f, 0);
		v1.setRGB(0, 1, 0);
		v1.setUV(0, 1);
		TexturedVertex v2 = new TexturedVertex();
		v2.setXYZ(0.5f, -0.5f, 0);
		v2.setRGB(0, 0, 1);
		v2.setUV(1, 1);
		TexturedVertex v3 = new TexturedVertex();
		v3.setXYZ(0.5f, 0.5f, 0);
		v3.setRGB(1, 1, 1);
		v3.setUV(1, 0);

		TexturedVertex[] vertices = new TexturedVertex[] {v0, v1, v2, v3};
		// Put each 'Vertex' in one FloatBuffer
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length *
				TexturedVertex.elementCount);
		for (int i = 0; i < vertices.length; i++) {
			// Add position, color and texture floats to the buffer
			verticesBuffer.put(vertices[i].getElements());
		}
		verticesBuffer.flip();
		// OpenGL expects to draw vertices in counter clockwise order by default
		int[] indices = {
				0, 1, 2,
				2, 3, 0
		};
		int indicesCount = indices.length;
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indicesCount);
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

		return new RenderStuff().init(vaoId, vboId, vboiId, indicesCount, 0).setPID(1);
	}
}
