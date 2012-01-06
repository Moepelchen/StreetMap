package streetmap.Interfaces;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/6/12
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITrajectory
{
	public Point2D calculatePosition(Point2D pos, double speed);
}
