package streetmap.map.side;

import streetmap.interfaces.IPrintable;
import streetmap.map.street.Lane;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;

/**
 * An anchor is a point where a lane can connect to
 * currently each Side as two Anchors, this might be changed
 * in future releases
 */
public class Anchor implements IPrintable
{
	/**
	 * Position of this anchor
	 */
	private Point2D fPosition;
	/**
	 * Lanes connected to this anchor
	 */
	private HashMap<String, Lane> fLanes;
	/**
	 * indicates that the anchor is currently blocked, meaning that the cars can not move any further
	 */
	private boolean fBlocked;

	public Anchor(Point2D position, String compassPoint)
	{
		fPosition = position;
		fLanes = new HashMap<String, Lane>();

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

	public Lane getRandomLane()
	{
		int index = (int) (Math.floor(fLanes.size() * Math.random()));

		Object[] objects = fLanes.keySet().toArray();
		if (objects.length > 0)
		{
			String test = (String) objects[index];
			Lane lane = fLanes.get(test);
			if (!lane.isBlocked())
			{
				return lane;
			}
		}
		return null;
	}

	public void removeLane(String s, Lane lane)
	{
		fLanes.remove(s);
	}

	public Collection<Lane> getLanes()
	{
		return fLanes.values();
	}
}