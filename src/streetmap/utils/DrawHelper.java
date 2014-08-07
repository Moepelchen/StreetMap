package streetmap.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import streetmap.car.RenderStuff;
import streetmap.map.side.Anchor;
import streetmap.map.side.Side;
import streetmap.map.street.Street;

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

    public static void drawCar(RenderStuff car)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GL20.glUseProgram(car.getPID());

// Bind to the VAO that has all the information about the vertices
        GL30.glBindVertexArray(car.getFVAOId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

// Bind to the index VBO that has all the information about the order of the vertices
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, car.getFVBOId2());

// Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, car.getFIndicesCount(), GL11.GL_UNSIGNED_INT, 0);

// Put everything back to default (deselect)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
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
        drawStreet(street, false);
    }

    public static void drawStreet(Street street, boolean forMenu)
    {
      /*  int textureId = 0;
        String imagePath;
        if (forMenu)
        {
            imagePath = street.getMenuImagePath();
        } else
        {
            imagePath = street.getImagePath();
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
}
