package streetmap.utils;

import org.lwjgl.opengl.GL11;
import streetmap.car.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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

	public static void drawCar(Car car, Color color)
	{

        double length = car.getLength()/2;


		double x = car.getPosition().getX();
		double y = car.getPosition().getY();


        GL11.glColor3d(color.getRed()/126,color.getGreen()/126,color.getBlue()/126);
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
}
