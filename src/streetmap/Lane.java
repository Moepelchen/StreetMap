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

	private SSGlobals fGlobals;

	private Trajectory fTrajectory;

	public Lane(SSGlobals glob)
	{
		fGlobals = glob;
		fCars = new Vector<Car>();
	}


	public void print(Graphics2D g)
	{
		if (fGlobals.getConfig().isDrawLanes())
		{
			g.setColor(Color.PINK);
			g.drawLine((int) fStartAnchor.getPosition().getX(), (int) fStartAnchor.getPosition().getY(), (int) fEndAnchor.getPosition().getX(), (int) fEndAnchor.getPosition().getY());
		}
		drawCars(g);
	}

	private void drawCars(Graphics2D g)
	{
		for (Car fCar : fCars)
		{
			fCar.print(g);
		}
	}

	public void simulate()
	{
		Vector<Car> toRemoveCars = new Vector<Car>();
		if (this.fCars.size() < 2)
		{
			fCars.add(new Car(this, fStartAnchor.getPosition()));
		}
		for (Car fCar : fCars)
		{
			fCar.simulate();
			if (fCar.getPosition().distance(fEndAnchor.getPosition()) < 1)
			{
				toRemoveCars.add(fCar);
			}
		}
		fCars.remove(toRemoveCars);
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

	public Anchor getEnd()
	{
		return fEndAnchor;
	}

	public Trajectory getTrajectory()
	{
		return fTrajectory;
	}

	public void init()
	{
		fTrajectory = new Trajectory(this);
	}
}