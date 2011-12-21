package streetmap;

import streetmap.Interfaces.IPrintable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

public class Anchor implements IPrintable
{

	private Point2D fPosition;

	private HashMap<String, Lane> fLanes;

	private boolean fBlocked;
	private String fCompassPoint;

	public Anchor(Point2D position, String compassPoint)
	{
		fPosition = position;
		fLanes = new HashMap<String, Lane>();
		fCompassPoint = compassPoint;

	}

	public boolean isfBlocked()
	{
		return fBlocked;
	}

	public void setBlocked(boolean fBlocked)
	{
		this.fBlocked = fBlocked;
	}

	public boolean isBlocked()
	{
		return false;
	}

	public Point2D getPosition()
	{
		return fPosition;
	}

	public void setPosition(Point2D position)
	{
		this.fPosition = position;
	}

	public void print(Graphics2D g)
	{

		g.drawRect((int) getPosition().getX() - 1, (int) getPosition().getY() - 1, 2, 2);
	}


	public void addLane(String to, Lane lane)
	{

		fLanes.put(to, lane);
	}
}