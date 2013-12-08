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
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex3d;

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

	private static Graphics2D getCarLayerGraphics(Car car)
	{
		return (Graphics2D) car.getLane().getGlobals().getMap().getCarLayerGraphics();
	}

	public static void drawFronCars(Car car, Vector<Car> frontCar)
	{
		Graphics2D g = getCarLayerGraphics(car);
		g.setColor(Color.WHITE);

		for (Car car1 : frontCar)
		{
			drawLine(g, car.getPosition(), car1.getPosition());
		}

	}

	private static void drawLine(Graphics2D g, Point2D position, Point2D position1)
	{
		g.drawLine((int) position.getX(), (int) position.getY(), (int) position1.getX(), (int) position1.getY());
	}

	public static void drawAnchor(Graphics2D g, Anchor anchor)
	{
		g.drawRect((int) anchor.getPosition().getX() - 1, (int) anchor.getPosition().getY() - 1, 2, 2);
	}

	public static void drawCurve(Graphics2D g, QuadCurve2D curve)
	{
		g.setColor(Color.PINK);
		g.draw(curve);
	}

	public static void drawStraight(Graphics2D g, Lane lane)
	{
		g.drawLine((int) lane.getStart().getPosition().getX(), (int) lane.getStart().getPosition().getY(), (int) lane.getEnd().getPosition().getX(), (int) lane.getEnd().getPosition().getY());

	}

	public static void drawSide(Graphics2D g, Side side)
	{
		if (side.getGlobals().getConfig().isDrawSides()&& g!= null)
		{
			g.setColor(Color.red);
			g.drawRect((int) (side.getPosition().getX()) - 2, (int) side.getPosition().getY() - 2, 5, 5);
		}
		if (side.getGlobals().getConfig().isDrawAnchors() && g!= null)
		{
			g.setColor(Color.green);
			side.getAnchorOne().print(g);
			g.setColor(Color.MAGENTA);
			side.getAnchorTwo().print(g);
		}
	}

	public static void drawStreet(Graphics2D g, Street street)
		{
			org.newdawn.slick.opengl.Texture streetText = null;
			String imagePath = street.getGlobals().getStreetConfig().getTemplate(street.getName()).getImagePath();
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

				Double tileSize = street.getTile().getWidth();
				glPushMatrix();
				glColor3d(1, 1, 1);
				if(imagePath != null)
				{
					glBindTexture(GL_TEXTURE_2D,streetText.getTextureID());
					glBegin(GL_QUADS);
					glTexCoord2d(0, 0);
					glVertex3d(street.getTile().getArrayPosition().getX() * tileSize, street.getTile().getArrayPosition().getY() * tileSize, 0);
					glTexCoord2d(0.65, 0);
					glVertex3d(street.getTile().getArrayPosition().getX() * tileSize + tileSize, street.getTile().getArrayPosition().getY() * tileSize, 0);
					glTexCoord2d(0.65, 0.65);
					glVertex3d(street.getTile().getArrayPosition().getX() * tileSize + tileSize, street.getTile().getArrayPosition().getY() * tileSize + tileSize, 0);
					glTexCoord2d(0, 0.65);
					glVertex3d(street.getTile().getArrayPosition().getX() * tileSize, street.getTile().getArrayPosition().getY() * tileSize + tileSize, 0);
					glEnd();
					glBindTexture(GL_TEXTURE_2D,0);
				}
			
				glPopMatrix();

			//g.drawImage(street.getImage(), (int) (street.getTile().getArrayPosition().getX() * tileSize), (int) (street.getTile().getArrayPosition().getY() * tileSize), tileSize.intValue(), tileSize.intValue(), null);

			for (Lane lane : street.getLanes())
			{
				lane.print(g);

			}
		}

	public static void drawHeatMap(BufferedImage image)
	{
	}
}
