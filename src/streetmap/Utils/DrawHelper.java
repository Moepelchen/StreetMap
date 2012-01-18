package streetmap.Utils;

import streetmap.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
        Image image = new ImageIcon("./images/car.png").getImage();
        g.rotate(car.getLane().getTrajectory().getAngle(),car.getPosition().getX(),car.getPosition().getY());
        g.drawImage(image,(int) car.getPosition().getX() - width / 2, (int) car.getPosition().getY() - width / 2, width, width,null);
        g.rotate(-car.getLane().getTrajectory().getAngle(),car.getPosition().getX(),car.getPosition().getY());
    }
}
