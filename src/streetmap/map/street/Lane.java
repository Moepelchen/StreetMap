package streetmap.map.street;

import streetmap.SSGlobals;
import streetmap.car.Car;
import streetmap.car.CarFactory;
import streetmap.interfaces.IPathFindingAlgorithm;
import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.map.side.Anchor;
import streetmap.map.street.trajectory.BendTrajectory;
import streetmap.map.street.trajectory.ITrajectory;
import streetmap.map.street.trajectory.StraightTrajectory;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class Lane implements IPrintable, ISimulateable
{

    private final Street fStreet;
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
	private double fMinX;
	private double fMinY;
	private boolean isStart;

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

	public Lane(SSGlobals glob, Street street)
	{
        fStreet = street;
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

		if (this.getEnd().getRandomLane() != null &&Math.random() < 0.05 && this.isStartLane() && fCars.size() < 1 && fGlobals.getMap().getCurrentNumberOfCars() < fGlobals.getMap().getMaximumNumberOfCars())
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

                Lane nexLane = null;
                IPathFindingAlgorithm pathFinder = fCar.getPathFinder();
                if (pathFinder != null)
                {
                    nexLane = pathFinder.getNextLane();
                }
                if (nexLane == null)
                {
                    nexLane = fEndAnchor.getRandomLane();
                }
				if (nexLane != null)
				{
					nexLane.addCar(fCar);
				}
				else
				{
					if (!fCar.getLane().isEndLane())
					{

						Lane parallelLane = fEndAnchor.getParallelLane();
						if (parallelLane != null)
						{
							parallelLane.addCar(fCar);
						}
					}
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

	public int getType()
	{
		return fType;
	}

	public void setType(int laneType)
	{
		fType = laneType;
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

	public String getTo()
	{
		return fTo;
	}

	public void setTo(String to)
	{
		fTo = to;
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

    public Street getStreet()
    {
        return fStreet;
    }
}