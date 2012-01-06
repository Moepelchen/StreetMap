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

	public static void drawCar(Graphics2D g, Car car){
		g.drawRect((int)car.getPosition().getX() ,(int)car.getPosition().getY(),10,10);

	}
}
