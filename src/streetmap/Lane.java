package streetmap;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;

import java.awt.*;
import java.util.Vector;

public class Lane implements IPrintable, ISimulateable
{

	public Anchor fStartAnchor;

	public Anchor fEndAnchor;

	public Vector<Car> fCars;
	private int fType;

	public void print(Graphics2D g)
	{
		g.setColor(Color.PINK);
		g.drawLine((int) fStartAnchor.getPosition().getX(), (int) fStartAnchor.getPosition().getY(), (int) fEndAnchor.getPosition().getX(), (int) fEndAnchor.getPosition().getY());
	}

	public void simulate()
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void setStart(Anchor start)
	{
		fStartAnchor = start;
	}

	public void setEnd(Anchor end)
	{

		fEndAnchor = end;
	}

	public void setType(int laneType)
	{
		fType = laneType;
	}

	public int getType()
	{
		return fType;
	}

	public Anchor getStart()
	{
		return fStartAnchor;
	}
}