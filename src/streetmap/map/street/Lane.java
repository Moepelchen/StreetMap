package streetmap.map.street;

import streetmap.SSGlobals;
import streetmap.car.Car;
import streetmap.car.CarFactory;
import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.map.side.Anchor;
import streetmap.map.street.trajectory.BendTrajectory;
import streetmap.map.street.trajectory.ITrajectory;
import streetmap.map.street.trajectory.StraightTrajectory;
import streetmap.pathfinding.IPathFindingAlgorithm;

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
	private boolean fBlocked;

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
		fCars = new Vector<>();
		fDirections = new HashMap<>();

	}

	public void print()
	{
		if (fGlobals.getConfig().isDrawLanes())
		{
			fTrajectory.print();
		}
		drawCars();
	}

	private void drawCars()
	{
		for (Car fCar : fCars)
		{
			fCar.print();
		}
	}

	public void simulate()
	{
		Vector<Car> toRemoveCars = new Vector<Car>();

		if (this.getEnd().getRandomLane() != null && Math.random() < getCarGenerationModifier() && this.isStartLane() && fCars.size() < 4 && fGlobals.getMap().getCurrentNumberOfCars() < fGlobals.getConfig().getMaximumNumOfCars())
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
					else
					{
						getGlobals().getMap().addCarFlowData(1);
					}
				}
			}
		}
		fCars.removeAll(toRemoveCars);
	}

    private double getCarGenerationModifier()
    {
        double base = 0.015;
        if(fGlobals.getTimeHandler().isDay())
        {
            base = base+0.1;
        }
        return base;
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
		return fBlocked;
	}
	public void setIsBlocked(boolean s)
	{
		fBlocked = s;
	}

    public Street getStreet()
    {
        return fStreet;
    }
}