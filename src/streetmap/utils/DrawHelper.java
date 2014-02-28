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

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

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

	private static int carTextId = 0;

	public static void drawCar(Car car, Color color)
	{

		float length = (float) (car.getLength() / 1);

		float x = (float) (car.getPosition().getX() - length / 2);
		float y = (float) (car.getPosition().getY() - length / 2);

		if (carTextId == 0)
		{
			try
			{
				Texture streetText = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("./images/cars/car.png"), GL_NEAREST);
				carTextId = streetText.getTextureID();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		glBindTexture(GL_TEXTURE_2D, carTextId);

		glPushMatrix();
		float angle = (float) car.getLane().getTrajectory().getAngle();
		glRotatef(angle, x, y, 1);


		glColor3d((double) color.getRed() / 255, (double) color.getGreen() / 255, (double) color.getBlue() / 255);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0F, 0.0F);
		glVertex2f(x, y);
		glTexCoord2f(1.0F, 0.0F);
		glVertex2f(x + length, y);
		glTexCoord2f(1.0F, 1.0F);
		glVertex2f(x + length, y + length);
		glTexCoord2f(0.0F, 1.0F);
		glVertex2f(x, y + length);
		glEnd();
		glPopMatrix();
		glBindTexture(GL_TEXTURE_2D, 0);

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
		Texture streetText = null;
		String imagePath;
		if (forMenu)
		{
			imagePath = street.getMenuImagePath();
		}
		else
		{
			imagePath = street.getImagePath();
		}
		if (street.getImage() == null)
		{

			if (imagePath != null)
			{
				streetText = gImageStore.get(imagePath);
				if (streetText == null)
				{
					try
					{
						streetText = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imagePath), GL_NEAREST);
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
		if (imagePath != null)
		{
			float y = (float) (street.getTile().getArrayPosition().getY() * tileSize);
			float x = (float) (street.getTile().getArrayPosition().getX() * tileSize);
			if (streetText != null)
			{
				glBindTexture(GL_TEXTURE_2D, streetText.getTextureID());
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
			glBindTexture(GL_TEXTURE_2D, 0);
		}

		glPopMatrix();
		for (Lane lane : street.getLanes())
		{
			lane.print();

		}
	}

	public static void drawHeatMap(BufferedImage image)
	{
		// TODO
	}
}
