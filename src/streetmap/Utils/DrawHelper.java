package streetmap.Utils;

import streetmap.Car;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/6/12
 * Time: 8:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class DrawHelper
{

	public static void drawCar(Graphics2D g, Car car, Color color)
	{
		int width;
		g.setColor(color);
		width = (int) (car.getLane().getGlobals().getConfig().getTileSize() / 4);
		g.fillOval((int) car.getPosition().getX() - width / 2, (int) car.getPosition().getY() - width / 2, width, width);

	}
}
