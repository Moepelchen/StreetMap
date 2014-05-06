package streetmap.utils;

import org.lwjgl.opengl.GL11;
import streetmap.car.Car;
import streetmap.map.side.Anchor;
import streetmap.map.side.Side;
import streetmap.map.street.Street;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.*;

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

    public static void drawCar(Car car, Color color)
    {

        float length = (float) (car.getLength() / 1);

        float x = (float) (car.getPosition().getX() - length / 2);
        float y = (float) (car.getPosition().getY() - length / 2);


        glBindTexture(GL_TEXTURE_2D, TextureCache.getTextureId(car.getImagePath()));
        glPushMatrix();


        double angle = car.getLane().getTrajectory().getAngle(car);
        //glTranslated(length/2,length/2,0);
        glTranslated(car.getPosition().getX(), car.getPosition().getY(), 0);
        glRotated(Math.toDegrees(angle), 0, 0, 1);
        glTranslated(-car.getPosition().getX(),-car.getPosition().getY(),0);



        glColor3d((double) color.getRed() / 255, (double) color.getGreen() / 255, (double) color.getBlue() / 255);
        glBegin(GL_QUADS);
        glTexCoord2f(0.0F, 0.0F);
        glVertex2f(x, y);
        glTexCoord2f(0, 1.0F);
        glVertex2f(x, y+length);
        glTexCoord2f(1.0F, 1.0F);
        glVertex2f(x+length,y+length);
        glTexCoord2f(1.0F, 0.0F);
        glVertex2f(length+x, y);
        glEnd();
        glPopMatrix();
        glBindTexture(GL_TEXTURE_2D, 0);

       /* GL30.glBindVertexArray(car.getVAOId());
        GL20.glEnableVertexAttribArray(0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, car.getVBOId2());
        GL11.glDrawElements(GL11.GL_TRIANGLES,car.getIndicesCount(),GL11.GL_UNSIGNED_BYTE,0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER,0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);*/


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
        int textureId = 0;
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
	           glColor3d(1, 1, 1);
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
	   	    glColor3d(1,1,1);

	           glPopMatrix();
	    glPushMatrix();
	    glColor3d(street.getGlobals().getMap().getHeatMapReading(street.getTile().getArrayPosition()) * 1, 0, 0);
	    GL11.glColor4d(1, 0, 0,street.getGlobals().getMap().getHeatMapReading(street.getTile().getArrayPosition()) * 1);
	    glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    if (imagePath != null)
	    {
		    float y = (float) (street.getTile().getArrayPosition().getY() * tileSize);
		    float x = (float) (street.getTile().getArrayPosition().getX() * tileSize);
		    glBegin(GL_QUADS);

		    glVertex3f(x, y, 0);

		    glVertex3f(x + tileSize, y, 0);

		    glVertex3f(x + tileSize, y + tileSize, 0);

		    glVertex3f(x, y + tileSize, 0);
		    glEnd();

	    }
	    glColor3d(1, 1, 1);

	    glPopMatrix();

    }

    public static void drawHeatMap(BufferedImage image)
    {
        // TODO
    }
}
