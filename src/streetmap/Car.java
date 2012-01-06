package streetmap;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;
import streetmap.Utils.DrawHelper;

import java.awt.*;
import java.awt.geom.Point2D;

public class Car implements IPrintable, ISimulateable
{


	private Point2D fPosition;

	private Lane fLane;

	private Trajectory fTrjectory;

	public Point2D getPosition()
	{
		return fPosition;
	}

	public void setPosition(Point2D fPosition)
	{
		this.fPosition = fPosition;
	}

	public Lane getLane()
	{
		return fLane;
	}

	public void setLane(Lane fLane)
	{
		this.fLane = fLane;
	}

	public Car(Lane lane, Point2D pos)
	{
		fLane = lane;
		fPosition = pos;
	}

	public void print(Graphics2D g)
	{
		DrawHelper.drawCar(g, this);
	}

	public void simulate()
	{
		move();
	}

	private void move()
	{
		Trajectory trajectory = fLane.getTrajectory();
		if (trajectory != null)
			setPosition(trajectory.calculatePosition(fPosition, 0.5));
	}
}