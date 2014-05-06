package streetmap.map.street.trajectory;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 23.01.12
 * Time: 07:28
 * To change this template use File | Settings | File Templates.
 */

public class QuickBendTrajectory implements ITrajectory
{

	public static final double HALF_PI = Math.PI / 2;
	public static final Point2D.Double CONST_POINT = new Point2D.Double(-1, -1);
	private boolean fisSideWays;
	private double fStartX;
	private double fEndX;
	private double fStartY;
	private Lane fLane;
	private double fEndY;
	private boolean isBackWard;
	private final QuadCurve2D fCurve;
	private double fLength;
	private boolean fIsUpward;
	private ArrayList<Point2D> fPointCache;
	private HashMap<Point2D, Double> fAngleCache;

	public QuickBendTrajectory(Lane lane)
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

		if (lane.getTo().equals("N") || lane.getFrom().equals("S"))
		{
			fIsUpward = true;
		}

		if (lane.getTo().equals("W") || lane.getTo().equals("E"))
		{
			fisSideWays = true;
		}

		fCurve = new QuadCurve2D.Double(fStartX, fStartY, x, y, fEndX, fEndY);
		fPointCache = new ArrayList<>();
		fAngleCache = new HashMap<>();
		initCurve();
		initAngleCurve();
	}

	private void initAngleCurve()
	{

		double angle;
		Point2D pos;

		for (int currentIndex = 0; currentIndex < fPointCache.size() - 1; currentIndex++)
		{
			pos = fPointCache.get(currentIndex);

			if (fPointCache.size() - 1 >= currentIndex + 1)
			{
				Point2D nextPoint = fPointCache.get(currentIndex + 1);
				double m = (pos.getY() - nextPoint.getY()) / (pos.getX() - nextPoint.getX());
				angle = Math.atan(m);
				if (angle < 0)
				{
					angle = angle + Math.PI;
				}

				if (fIsUpward)
				{
					angle = angle + Math.PI;

				}
				fAngleCache.put(pos, angle);

			}
		}
	}

	private void initCurve()
	{
		PathIterator p = fCurve.getPathIterator(null);
		FlatteningPathIterator f = new FlatteningPathIterator(p, fLane.getGlobals().getConfig().getMaximumCarSpeed() / 40);
		while (!f.isDone())
		{
			float[] pts = new float[6];
			switch (f.currentSegment(pts))
			{
				case PathIterator.SEG_MOVETO:
				case PathIterator.SEG_LINETO:
					Point2D point = new Point2D.Float(pts[0], pts[1]);
					fPointCache.add(point);

			}
			f.next();
		}
	}

	@Override
	public Point2D calculatePosition(Point2D pos, double speed)
	{
		Point2D nextPoint = CONST_POINT;
		int currentIndex = fPointCache.indexOf(pos);
		if (currentIndex >= 0)
		{
			if (fPointCache.size() - 1 >= currentIndex + 1)
			{
				nextPoint = fPointCache.get(currentIndex + 1);
				return nextPoint;
			}
		}
		return nextPoint;
	}

	@Override
	public double getAngle(Car car)
	{
		try
		{
			return fAngleCache.get(car.getPosition());

		}
		catch (NullPointerException e)
		{
			double angle = HALF_PI;
			if (fisSideWays)
			{
				angle = 0;
			}
			return angle;
		}
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
		return fPointCache.contains(fCar.getPosition());
	}

	@Override
	public void relocate(Car fCar)
	{

	}

	@Override
	public double getLength()
	{
		if (fLength == 0)
		{
			PathIterator iter = fCurve.getPathIterator(null, 0.5);

			double[] curSeg = new double[2];
			iter.currentSegment(curSeg);
			iter.next();
			double x0 = curSeg[0];
			double y0 = curSeg[1];
			while (!iter.isDone())
			{
				iter.currentSegment(curSeg);
				fLength += Math.sqrt((curSeg[0] - x0) * (curSeg[0] - x0) +
						(curSeg[1] - y0) * (curSeg[1] - y0));
				x0 = curSeg[0];
				y0 = curSeg[1];
				iter.next();
			}
		}
		return fLength;
	}

}