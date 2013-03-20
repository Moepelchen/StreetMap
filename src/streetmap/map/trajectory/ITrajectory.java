package streetmap.map.trajectory;

import streetmap.map.street.Lane;
import streetmap.car.Car;

import java.awt.*;
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

	double getAngle();

	void print(Graphics2D g);

	/**
	 * determines whether the car is still on this lane
	 *
	 * @param fCar car to test with
	 * @param lane
	 * @return
	 */
	boolean carOnLane(Car fCar, Lane lane);

	void relaocate(Car car);
}
