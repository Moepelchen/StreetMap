package streetmap.utils;

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
		Graphics2D g = getCarLayerGraphics(car);
		double scaleX;
		double scaleY;
		g.setColor(color);
		double width = car.getLength();
		scaleX = width / car.getImage().getImage().getWidth(null);
		Shape b = new Rectangle();

		ImageIcon icon = car.getImage();//.getScaledInstance(Image.SCALE_DEFAULT,width,width);
		g.setComposite(AlphaComposite.Src);
		AffineTransform trans = new AffineTransform();

		double x = car.getPosition().getX();
		double y = car.getPosition().getY();
		trans.setToRotation(car.getLane().getTrajectory().getAngle(), x, y);
		trans.translate(x - width / 4, y - width / 4);
		//trans.shear(width,width);
		Rectangle2D rect;
		rect = new Rectangle2D.Double();

		trans.scale(scaleX, scaleX);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		//g.rotate(0,car.getPosition().getX(),car.getPosition().getY());
		//g.drawImage(icon.getImage(), trans,null);
		g.fillOval((int) (car.getPosition().getX() - width / 2), (int) (car.getPosition().getY() - width / 2), (int) width, (int) width);
		//g.rotate(-car.getLane().getTrajectory().getAngle(),car.getPosition().getX(),car.getPosition().getY());

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
