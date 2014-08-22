package streetmap.map.street.trajectory;

import streetmap.car.Car;
import streetmap.map.street.ILaneTypes;
import streetmap.map.street.Lane;
import streetmap.map.tile.ICompassPoint;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/6/12
 * Time: 8:36 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class determines on what trajectory the car moves on a lane between two anchor points
 * In this case it is a straight line
 */
public class StraightTrajectory implements ITrajectory
{
	private static final int INT = 10000000;

	private double fA;

	private double fB;

	private double fDirection;

	private Lane fLane;
	private double fAngle;

	public StraightTrajectory(Lane lane)
	{
		fLane = lane;
		Point2D p1 = lane.getStart().getPosition();
		Point2D p2 = lane.getEnd().getPosition();

		double deltaX = p2.getX() - p1.getX();
		double deltaY = p2.getY() - p1.getY();

		if (deltaX != 0)
		{
			fA = (deltaY) / (deltaX);
			fB = p1.getY() - fA * p1.getX();
		}
		else
		{
			fA = INT;
		}

		boolean isSouth = fLane.getDirection(fLane.getEnd()).equals(ICompassPoint.COMPASS_POINT_S);
		boolean isWest = fLane.getDirection(fLane.getEnd()).equals(ICompassPoint.COMPASS_POINT_W);
		boolean isEast = fLane.getDirection(fLane.getEnd()).equals(ICompassPoint.COMPASS_POINT_E);
		fDirection = +1;
		if (isWest)
		{
			fAngle = Math.PI;
			fDirection = -1;
		}
		else if (isEast)
		{
			fAngle = 0;
		}
		else if (isSouth)
		{
			fAngle =  Math.PI / 2;
			fDirection = -1;
		}
		else
		{
			fAngle = 3*Math.PI / 2;
		}


	}

	public Point2D calculatePosition(Point2D pos, double speed)
	{
		Point2D newPos = new Point2D.Double();
		if (fLane.getType() == ILaneTypes.BEND)
		{
			speed = speed / 2;
		}
		double x = pos.getX() + fDirection * speed*2.5;
		if (fA != INT)
		{
			newPos.setLocation(x, fA * x + fB);
		}
		else if (fA == INT)
		{
			newPos.setLocation(pos.getX(), pos.getY() - fDirection * speed*2.5);
		}

		return newPos;
	}

	public double getAngle(Car car)
	{
		return fAngle;
	}

	@Override
	public void print()
	{
	}

	/**
	 * determines whether the car is still on this lane
	 *
	 * @param fCar car to test with
	 * @param lane
	 * @return
	 */
	public boolean carOnLane(Car fCar, Lane lane)
	{

		double x = fCar.getPosition().getX();
		double y = fCar.getPosition().getY();
		return x >= lane.getMinX() && x <= lane.getMaxX() && y >= lane.getMinY() && y <= lane.getMaxY();
	}

	@Override
	public void relocate(Car car)
	{
		// nothing to do here
	}

	@Override
	public double getLength()
	{
		return fLane.getStart().getPosition().distance(fLane.getEnd().getPosition());
	}
}
