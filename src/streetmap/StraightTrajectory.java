package streetmap;

import streetmap.Interfaces.ILaneTypes;
import streetmap.Interfaces.ITrajectory;

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
 *
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
		} else
		{
			fA = INT;
		}
        double angle = Math.abs(Math.atan2(deltaY, deltaX));
        fAngle = angle;
		if (lane.getType() == ILaneTypes.BEND)
		{

			if (angle >= Math.PI / 2 && angle <= Math.PI * 6 / 4)
			{
				fDirection = -1;
                if(fA > 0)
                    fAngle = fAngle+ Math.PI/2;
			} else {
				fDirection = +1;
                if(fA < 0)
                    fAngle = fAngle- Math.PI/2;
            }

		} else
		{
            boolean isSouth = fLane.getDirection(fLane.getEnd()).equals(Tile.COMPASS_POINT_S);
            if (fLane.getDirection(fLane.getEnd()).equals(Tile.COMPASS_POINT_W) || isSouth)
			{
				fDirection = -1;


			} else{
				fDirection = +1;
                if(fLane.getDirection(fLane.getEnd()).equals(Tile.COMPASS_POINT_N))
                    fAngle = fAngle+Math.PI;

            }

		}

	}

    public Point2D calculatePosition(Point2D pos, double speed)
    {
        Point2D newPos = new Point2D.Double();
        if (fLane.getType() == ILaneTypes.BEND)
        {
            speed = speed / 2;
        }
        double x = pos.getX() + fDirection * speed;
        if (fA != INT)
            newPos.setLocation(x, fA * x + fB);
        else if (fA == INT)
        {
            newPos.setLocation(pos.getX(), pos.getY() - fDirection * speed);
        }


        return newPos;
    }

    public double getAngle() {
        return fAngle;
    }
}
