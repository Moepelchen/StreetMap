package streetmap.map.street.trajectory;

import streetmap.car.Car;
import streetmap.map.street.Lane;
import streetmap.map.tile.ICompassPoint;

import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

/**
 * Created by IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 23.01.12
 * Time: 07:28
 * To change this template use File | Settings | File Templates.
 */

public class BendTrajectory implements ITrajectory
{

    private boolean fisSideWays;
	private final QuadCurve2D fCurve;
	private double fLength;
    private boolean fIsUpward;

	public BendTrajectory(Lane lane)
	{
		double startX = lane.getStart().getPosition().getX();
		double endX = lane.getEnd().getPosition().getX();
		double startY = lane.getStart().getPosition().getY();
		double endY = lane.getEnd().getPosition().getY();

		double x = startX;
		double y = endY;
		if (startX > endX && lane.getDirection(lane.getStart()).equals(ICompassPoint.COMPASS_POINT_E) || (startX < endX && lane.getDirection(lane.getStart()).equals(ICompassPoint.COMPASS_POINT_W)))
		{
			x = endX;
			y = startY;
		}

        if(lane.getTo().equals(ICompassPoint.COMPASS_POINT_N) || lane.getFrom().equals(ICompassPoint.COMPASS_POINT_S))
            fIsUpward = true;

        if(lane.getTo().equals(ICompassPoint.COMPASS_POINT_W)|| lane.getTo().equals(ICompassPoint.COMPASS_POINT_E))
            fisSideWays = true;

		fCurve = new QuadCurve2D.Double(startX, startY, x, y, endX, endY);

	}

	@Override
	public Point2D calculatePosition(Point2D pos, double speed)
	{

		PathIterator p = fCurve.getPathIterator(null);
        double reduceSpeed = reduceCarSpeed(speed);
        if(reduceSpeed < 0.01)
            return pos;
        FlatteningPathIterator f = new FlatteningPathIterator(p, reduceSpeed);
		while (!f.isDone())
		{
			float[] pts = new float[6];
			switch (f.currentSegment(pts))
			{
				case PathIterator.SEG_MOVETO:
				case PathIterator.SEG_LINETO:
					Point2D point = new Point2D.Float(pts[0], pts[1]);
					if (point.distance(pos) == 0)
					{
						f.next();
						if (!f.isDone())
						{
							pts = new float[6];
							f.currentSegment(pts);
							return new Point2D.Float(pts[0], pts[1]);
						}
						else
						{
							return new Point2D.Double(-1, -1);
						}
					}

			}
			f.next();
		}
		return new Point2D.Double(-1, -1);
	}

	private double reduceCarSpeed(double speed)
	{
		return speed/10;
	}

	@Override
	public double getAngle(Car car)
	{
		PathIterator p = fCurve.getPathIterator(null);
		FlatteningPathIterator f = new FlatteningPathIterator(p, reduceCarSpeed(car.getSpeed()));
		while (!f.isDone())
		{
			float[] pts = new float[6];
			switch (f.currentSegment(pts))
			{
				case PathIterator.SEG_MOVETO:
				case PathIterator.SEG_LINETO:
					Point2D point = new Point2D.Float(pts[0], pts[1]);
					Point2D pos = car.getPosition();
					if (point.distance(pos) == 0)
					{
						f.next();
						if (!f.isDone())
						{
							pts = new float[6];
							f.currentSegment(pts);
							Point2D nextPoint = new Point2D.Float(pts[0], pts[1]);
							double m = (pos.getY() - nextPoint.getY()) / (pos.getX() - nextPoint.getX());
							double angle = Math.atan(m);
                            if(angle <0)
                                angle = angle +Math.PI;

                            if(fIsUpward)
                                angle = angle + Math.PI;
                            return angle;
						}

					}

			}
			f.next();
		}

        double defaultAngle = Math.PI / 2;
        if(fisSideWays)
            defaultAngle = 0;
        return defaultAngle;
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
		PathIterator p = fCurve.getPathIterator(null);
		FlatteningPathIterator f = new FlatteningPathIterator(p, reduceCarSpeed(fCar.getSpeed()));
		while (!f.isDone())
		{
			float[] pts = new float[6];
			switch (f.currentSegment(pts))
			{
				case PathIterator.SEG_MOVETO:
				case PathIterator.SEG_LINETO:
					Point2D point = new Point2D.Float(pts[0], pts[1]);
					if (point.distance(fCar.getPosition()) <= 1)
					{
						return true;
					}

			}
			f.next();
		}
		return false;
	}

	@Override
	public void relocate(Car fCar)
	{
		PathIterator p = fCurve.getPathIterator(null);
		FlatteningPathIterator f = new FlatteningPathIterator(p, reduceCarSpeed(fCar.getSpeed()));
		Point2D nearestPoint = null;
		double distance = 1000;
		while (!f.isDone())
		{
			float[] pts = new float[6];
			switch (f.currentSegment(pts))
			{
				case PathIterator.SEG_MOVETO:
				case PathIterator.SEG_LINETO:
					Point2D point = new Point2D.Float(pts[0], pts[1]);
					double distance1 = point.distance(fCar.getPosition());
					if (distance1 < distance)
					{
						nearestPoint = point;
						distance = distance1;
					}

			}
			f.next();
		}
		if (nearestPoint != null)
		{
			fCar.setPosition(nearestPoint);
		}
	}

	@Override
	public double getLength()
	{
		if (fLength == 0)
		{
			PathIterator iter = fCurve.getPathIterator(null,0.5);

	        double [] curSeg = new double[2];
	        iter.currentSegment(curSeg);
	        iter.next();
	        double x0 = curSeg[0];
	        double y0 = curSeg[1];
	        while(!iter.isDone()) {
	            iter.currentSegment(curSeg);
	            fLength += Math.sqrt((curSeg[0] - x0)*(curSeg[0] - x0) +
	                    (curSeg[1] - y0)*(curSeg[1] - y0));
	            x0 = curSeg[0];
	            y0 = curSeg[1];
	            iter.next();
	        }
		}
		return fLength;
	}

}
