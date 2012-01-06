package streetmap;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class Lane implements IPrintable, ISimulateable
{

	private Anchor fStartAnchor;

	private Anchor fEndAnchor;

	private Vector<Car> fCars;

	private int fType;

	private SSGlobals fGlobals;

	private StraightTrajectory fTrajectory;

	private HashMap<Anchor, String> fDirections;

	public Lane(SSGlobals glob)
	{
		fGlobals = glob;
		fCars = new Vector<Car>();
		fDirections = new HashMap<Anchor, String>();
	}


	public void print(Graphics2D g)
	{
		if (fGlobals.getConfig().isDrawLanes())
		{
			g.setColor(Color.PINK);
			g.drawLine((int) fStartAnchor.getPosition().getX(), (int) fStartAnchor.getPosition().getY(), (int) fEndAnchor.getPosition().getX(), (int) fEndAnchor.getPosition().getY());
		}
		//drawCars(g);
	}

	public void simulate()
	{
		Vector<Car> toRemoveCars = new Vector<Car>();
		if (Math.random() < 0.01)
		{
			Car car = new Car(this, fStartAnchor.getPosition());
			fCars.add(car);

		}
		for (Car fCar : fCars)
		{
			fCar.simulate();
			if (!carOnLane(fCar))
			{
				toRemoveCars.add(fCar);

			}
		}
		fCars.removeAll(toRemoveCars);
	}

	private boolean carOnLane(Car fCar)
	{
		double maxX = Math.max(getStart().getPosition().getX(), getEnd().getPosition().getX());
		double maxY = Math.max(getStart().getPosition().getY(), getEnd().getPosition().getY());
		double minX = Math.min(getStart().getPosition().getX(), getEnd().getPosition().getX());
		double minY = Math.min(getStart().getPosition().getY(), getEnd().getPosition().getY());
		double x = fCar.getPosition().getX();
		double y = fCar.getPosition().getY();
		if (x >= minX && x <= maxX && y >= minY && y <= maxY)
		{
			return true;
		}
		return false;  //To change body of created methods use File | Settings | File Templates.
	}

	public void setStart(Anchor start, String compass)
	{
		fStartAnchor = start;
		fDirections.put(fStartAnchor, compass);
	}

	public void setEnd(Anchor end, String compass)
	{
		fEndAnchor = end;
		fDirections.put(fEndAnchor, compass);
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

	public StraightTrajectory getTrajectory()
	{
		return fTrajectory;
	}

	public void init()
	{
		fTrajectory = new StraightTrajectory(this);
	}

	public Vector<Car> getCars()
	{
		return fCars;
	}

	public SSGlobals getGlobals()
	{
		return fGlobals;
	}

	public String getDirection(Anchor anc)
	{
		return fDirections.get(anc);
	}
}