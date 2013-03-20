package streetmap;

import streetmap.Interfaces.ILaneTypes;
import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;
import streetmap.Interfaces.ITrajectory;
import streetmap.car.Car;
import streetmap.car.CarFactory;

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

	private ITrajectory fTrajectory;

	private HashMap<Anchor, String> fDirections;

	private boolean isEnd;
	private String fTo;
	private String fFrom;
	private double fMaxX;
	private double fMaxY;

	public double getMaxX()
	{
		return fMaxX;
	}

	public double getMaxY()
	{
		return fMaxY;
	}

	public double getMinX()
	{
		return fMinX;
	}

	public double getMinY()
	{
		return fMinY;
	}

	private double fMinX;
	private double fMinY;

	public boolean isEndLane()
	{
		return isEnd;
	}

	public void setIsEndLane(boolean end)
	{
		isEnd = end;
	}

	public boolean isStartLane()
	{
		return isStart;
	}

	public void setIsStartLane(boolean start)
	{
		isStart = start;
	}

	private boolean isStart;

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
			fTrajectory.print(g);
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
		if (Math.random() < 0.05 && this.isStartLane() && fCars.size() < 1)
		{
			Car car = CarFactory.createCar(getGlobals(), this, fStartAnchor.getPosition());
			fCars.add(car);

		}
		for (Car fCar : fCars)
		{
			fCar.simulate();
			if (!fTrajectory.carOnLane(fCar, this))
			{
				toRemoveCars.add(fCar);
				Lane randomOtherLane = fEndAnchor.getRandomLane();
				if (randomOtherLane != null)
				{
					randomOtherLane.addCar(fCar);
				}
			}
		}
		fCars.removeAll(toRemoveCars);
	}

	private void addCar(Car fCar)
	{
		fCar.setPosition(fStartAnchor.getPosition());
		fCar.reset(this);
		fCars.add(fCar);
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

	public ITrajectory getTrajectory()
	{
		return fTrajectory;
	}

	public void init()
	{
		//fTrajectory = new StraightTrajectory(this);
		if (getType() == ILaneTypes.BEND)
		{
			fTrajectory = new BendTrajectory(this);
		}
		else
		{
			fTrajectory = new StraightTrajectory(this);
		}
		fMaxX = Math.max(getStart().getPosition().getX(), getEnd().getPosition().getX());
		fMaxY = Math.max(getStart().getPosition().getY(), getEnd().getPosition().getY());
		fMinX = Math.min(getStart().getPosition().getX(), getEnd().getPosition().getX());
		fMinY = Math.min(getStart().getPosition().getY(), getEnd().getPosition().getY());
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

	public void setTo(String to)
	{
		fTo = to;
	}

	public String getTo()
	{
		return fTo;
	}

	public String getFrom()
	{
		return fFrom;
	}

	public void setFrom(String fFrom)
	{
		this.fFrom = fFrom;
	}

	public boolean isBlocked()
	{
		return false;
	}
}