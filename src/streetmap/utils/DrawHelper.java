package streetmap.utils;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import streetmap.car.Car;
import streetmap.map.side.Anchor;
import streetmap.map.side.Side;
import streetmap.map.street.Lane;
import streetmap.map.street.Street;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

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
	private static final HashMap<String, Texture> gImageStore = new HashMap<String, Texture>();

	public static void drawCar(Car car, Color color)
	{

        double length = car.getLength()/1.25;


		double x = car.getPosition().getX()-length/2;
		double y = car.getPosition().getY()-length/2;

		glPushMatrix();
		glColor3d((double) color.getRed() / 255, (double) color.getGreen() / 255, (double) color.getBlue() / 255);
		glBegin(GL_QUADS);
		glVertex3d(x, y, 1);
        glVertex3d(x + length, y, 1);
        glVertex3d(x + length, y + length, 1);
        glVertex3d(x, y + length, 1);
        glEnd();
		glPopMatrix();

    }

	public static void drawAnchor(Anchor anchor)
	{
	}

	public static void drawSide(Side side)
	{
	}

    public static void drawStreet(Street street)
    {
        drawStreet(street, false);
    }

    public static void drawStreet(Street street, boolean forMenu)
		{
			Texture streetText = null;
			String imagePath;
            if (forMenu)
            {
               imagePath = street.getMenuImagePath();
            } else
                imagePath = street.getImagePath();
            if (street.getImage() == null)
			{

				if (imagePath != null)
				{
					streetText = gImageStore.get(imagePath);
					if (streetText == null)
					{
						try
						{
							streetText = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imagePath));
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}

						gImageStore.put(imagePath, streetText);
					}
				}
			}

				float tileSize = (float) street.getTile().getWidth();
				glPushMatrix();
				glColor3d(1, 1, 1);
				if(imagePath != null)
				{
                    float y = (float) (street.getTile().getArrayPosition().getY() * tileSize);
                    float x = (float) (street.getTile().getArrayPosition().getX() * tileSize);
                    if (streetText != null)
                    {
                        glBindTexture(GL_TEXTURE_2D,streetText.getTextureID());
                    }
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
					glBindTexture(GL_TEXTURE_2D,0);
				}
			
				glPopMatrix();
			for (Lane lane : street.getLanes())
			{
				lane.print();

			}
		}

	public static void drawHeatMap(BufferedImage image)
	{
	}
}
