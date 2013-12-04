package streetmap.utils;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import streetmap.car.Car;
import streetmap.map.side.Anchor;
import streetmap.map.side.Side;
import streetmap.map.street.Lane;
import streetmap.map.street.Street;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

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
	private static final HashMap<String, Image> gImageStore = new HashMap<String, Image>();

	public static void drawCar(Car car, Color color)
	{

        double length = car.getLength()/2;


		double x = car.getPosition().getX();
		double y = car.getPosition().getY();


		new org.newdawn.slick.Color(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha()).bind();
		GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x + length, y);
        GL11.glVertex2d(x + length, y + length);
        GL11.glVertex2d(x, y + length);
        GL11.glEnd();

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
		if (side.getGlobals().getConfig().isDrawSides())
		{
			g.setColor(Color.red);
			g.drawRect((int) (side.getPosition().getX()) - 2, (int) side.getPosition().getY() - 2, 5, 5);
		}
		if (side.getGlobals().getConfig().isDrawAnchors())
		{
			g.setColor(Color.green);
			side.getAnchorOne().print(g);
			g.setColor(Color.MAGENTA);
			side.getAnchorTwo().print(g);
		}
	}

	public static void drawStreet(Graphics2D g, Street street)
	{
		String imagePath = street.getGlobals().getStreetConfig().getTemplate(street.getName()).getImagePath();
		if (street.getImage() == null)
		{

			if (imagePath != null)
			{
				street.setImage(gImageStore.get(imagePath));
				if (street.getImage() == null)
				{
					street.setImage(new ImageIcon(imagePath).getImage());
					gImageStore.put(imagePath, street.getImage());
				}
			}
		}
		org.newdawn.slick.opengl.Texture streetText = null;
		try

		{
			Double tileSize = street.getTile().getWidth();
			org.newdawn.slick.Color.green.bind();
			if(imagePath != null)
			{
				streetText = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imagePath));
				streetText.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2d(0, 0);
				GL11.glVertex2d(street.getTile().getArrayPosition().getX() * tileSize, street.getTile().getArrayPosition().getY() * tileSize);
				GL11.glTexCoord2d(1, 0);
				GL11.glVertex2d(street.getTile().getArrayPosition().getX() * tileSize + tileSize, street.getTile().getArrayPosition().getY() * tileSize);
				GL11.glTexCoord2d(1, 1);
				GL11.glVertex2d(street.getTile().getArrayPosition().getX() * tileSize + tileSize, street.getTile().getArrayPosition().getY() * tileSize + tileSize);
				GL11.glTexCoord2d(0, 1);
				GL11.glVertex2d(street.getTile().getArrayPosition().getX() * tileSize, street.getTile().getArrayPosition().getY() * tileSize + tileSize);
				GL11.glEnd();
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}


		//g.drawImage(street.getImage(), (int) (street.getTile().getArrayPosition().getX() * tileSize), (int) (street.getTile().getArrayPosition().getY() * tileSize), tileSize.intValue(), tileSize.intValue(), null);

		for (Lane lane : street.getLanes())
		{
			lane.print(g);

		}
	}
}
