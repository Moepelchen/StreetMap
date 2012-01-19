package streetmap.Utils;

import streetmap.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
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
		double scaleX;
        double scaleY;
		g.setColor(color);
        double width = car.getLane().getGlobals().getConfig().getTileSize() / 4;
        scaleX =  width /car.getImage().getWidth(null);
        Shape b = new Rectangle();

       Image image = car.getImage();//.getScaledInstance(Image.SCALE_DEFAULT,width,width);
        g.setComposite(AlphaComposite.Src);
        AffineTransform trans = new AffineTransform();

        double x = car.getPosition().getX();
        double y = car.getPosition().getY();
        trans.setToRotation(car.getLane().getTrajectory().getAngle(), x, y);
        trans.translate(x-width/4,y-width/4);
        //trans.shear(width,width);
        Rectangle2D rect;
        rect = new Rectangle2D.Double();

        trans.scale(scaleX,scaleX);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
       // g.rotate(image,car.getPosition().getX(),car.getPosition().getY());
        g.drawImage(image, trans,null);
        //g.rotate(-car.getLane().getTrajectory().getAngle(),car.getPosition().getX(),car.getPosition().getY());


    }

}
