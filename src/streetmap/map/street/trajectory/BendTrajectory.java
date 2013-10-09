package streetmap.map.street.trajectory;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.awt.*;
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

	private double fStartX;
	private double fEndX;
	private double fStartY;
	private Lane fLane;
	private double fEndY;
	private boolean isBackWard;
	private final QuadCurve2D fCurve;

	public BendTrajectory(Lane lane)
	{
		fLane = lane;
		fStartX = lane.getStart().getPosition().getX();
		fEndX = lane.getEnd().getPosition().getX();
		fStartY = lane.getStart().getPosition().getY();
		fEndY = lane.getEnd().getPosition().getY();

		double x = fStartX;
		double y = fEndY;
		if (fStartX > fEndX && fLane.getDirection(fLane.getStart()).equals("E") || (fStartX < fEndX && fLane.getDirection(fLane.getStart()).equals("W")))
		{
			isBackWard = true;
			x = fEndX;
			y = fStartY;
		}
		fCurve = new QuadCurve2D.Double(fStartX, fStartY, x, y, fEndX, fEndY);

	}

	@Override
	public Point2D calculatePosition(Point2D pos, double speed)
	{
		if (isBackWard)
		{

		}
		PathIterator p = fCurve.getPathIterator(null);
		FlatteningPathIterator f = new FlatteningPathIterator(p, reduceCarSpeed(speed));
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
		return speed / 50;
	}

	@Override
	public double getAngle()
	{
		return 0;
	}

	@Override
	public void print(Graphics2D g)
	{
		g.draw(fCurve);
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

}
