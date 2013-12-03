package streetmap.utils;

import streetmap.car.Car;
import streetmap.map.side.Anchor;
import streetmap.map.side.Side;
import streetmap.map.street.Lane;
import streetmap.map.street.Street;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
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

		g.fillOval((int) (car.getPosition().getX() - width / 2), (int) (car.getPosition().getY() - width / 2), (int) width, (int) width);
		/*g.rotate(0,car.getPosition().getX(),car.getPosition().getY());
		g.drawImage(icon.getImage(), trans,null);
		g.rotate(-car.getLane().getTrajectory().getAngle(),car.getPosition().getX(),car.getPosition().getY());
*/
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
		if (street.getImage() == null)
		{

			String imagePath = street.getGlobals().getStreetConfig().getTemplate(street.getName()).getImagePath();
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
		Double tileSize = street.getTile().getWidth();

		g.drawImage(street.getImage(), (int) (street.getTile().getArrayPosition().getX() * tileSize), (int) (street.getTile().getArrayPosition().getY() * tileSize), tileSize.intValue(), tileSize.intValue(), null);

		for (Lane lane : street.getLanes())
		{
			lane.print(g);

		}
	}
}
