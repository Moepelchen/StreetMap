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

	private StraightTrajectory fTrjectory;
	private Color fColor;


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
		fColor = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
	}

	public void print(Graphics2D g)
	{
		DrawHelper.drawCar(g, this, fColor);
	}

	public void simulate()
	{
		move();
	}

	private void move()
	{
		StraightTrajectory trajectory = fLane.getTrajectory();
		if (trajectory != null)
			setPosition(trajectory.calculatePosition(fPosition, getLane().getGlobals().getConfig().getTileSize() / 50));
	}
}