package streetmap.utils;

import org.lwjgl.opengl.*;

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

    public static void drawBuffers(RenderStuff car, int textureId)
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

}
