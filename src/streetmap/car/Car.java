package streetmap.car;

import streetmap.events.IEvent;
import streetmap.events.IEventHandler;
import streetmap.events.StreetPlacementEvent;
import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.map.street.Lane;
import streetmap.map.street.Street;
import streetmap.map.street.trajectory.ITrajectory;
import streetmap.pathfinding.AbstractPathFinder;
import streetmap.pathfinding.IPathFindingAlgorithm;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Vector;

/**
 * This class represents one car moving on the map
 */
public class
		Car implements IPrintable, ISimulateable, IEventHandler
{

	public static final int COLOR_HAPPINESS = 120;
	private boolean fHasRequestedPath;

	/**
	 * Current position
	 */
	private Point2D fPosition;

	/**
	 * Current lane the car is driving on
	 */
	private Lane fLane;

	/**
	 * Color of this car
	 */
	private Color fColor;
	private String fImagePath;
	private double fSpeed;
	private final double fOriginalSpeed;
	private float fLength;
	private double fHappiness;
	private IPathFindingAlgorithm fPathFinder;

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

	public void setHasRequestedPath(boolean hasRequestedPath)
	{
		fHasRequestedPath = hasRequestedPath;
	}

	public boolean hasRequestedPath()
	{
		return fHasRequestedPath;
	}

	public Car(Lane lane, Point2D pos, String carImagePath, float length)
	{
		fLength = length;
		fLane = lane;
		fPosition = pos;
		fColor = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
		fImagePath = carImagePath;
		double v = Math.max(Math.random(), 0.25) * lane.getGlobals().getConfig().getMaximumCarSpeed();
		fSpeed = v;
		fOriginalSpeed = v;
		fLane.getGlobals().getMap().getPathFactory().createPath(this);
	}

	public void print()
	{

	}

	public void simulate()
	{
		for (IEvent event : fLane.getGlobals().getMap().getEvents())
		{
			handleEvent(event);
		}
		move();
		fHappiness = Math.min(1, fSpeed / fOriginalSpeed);
	}

	/**
	 * This method is the one called by lane for the car to move
	 */
	private void move()
	{
		boolean caped = false;
		for (Car car : getFrontCars())
		{
			double distance = car.getPosition().distance(this.getPosition());
			if (distance < 2.5 * car.getLength())
			{

				setSpeed(car.getSpeedModifier() - 0.1 * car.getSpeedModifier());
				caped = true;
				break;

			}
		}
		/*for (Car car : getCrossingCars())
		{
			double distance = car.getPosition().distance(this.getPosition());
			if (distance < 2.5 * car.getLength())
			{
				setSpeed(0);
				caped = true;
				break;
			}
		}*/
		if (!caped)
		{
			if (fSpeed < fOriginalSpeed)
			{
				double delta = fOriginalSpeed - fSpeed;
				double inc = delta / 10;
				fSpeed = fSpeed + inc;
				if (fSpeed > fOriginalSpeed)
				{
					fSpeed = fOriginalSpeed;
				}

			}
			else
			{
				fSpeed = fOriginalSpeed;
			}
		}
		ITrajectory trajectory = fLane.getTrajectory();
		if (trajectory != null)
		{
			trajectory.relocate(this);
			Point2D fPosition1 = trajectory.calculatePosition(fPosition, getSpeed());
			setPosition(fPosition1);
		}
	}

	public double getSpeed()
	{
		return (getLane().getGlobals().getConfig().getTileSize() / 20) * fSpeed;
	}

	public double getSpeedModifier()
	{
		return fSpeed;
	}

	/**
	 * @param lane
	 */
	public void reset(Lane lane)
	{
		fLane = lane;
		if (fHappiness < 0.10 && fPathFinder != null && Math.random() > 0.10)
		{
			recalcPath(fPathFinder.getDestination());
		}
	}

	protected void recalcPath(Lane destination)
	{
		fLane.getGlobals().getMap().getPathFactory().createPath(this, destination);
	}

	public String getImagePath()
	{
		return fImagePath;
	}

	private Vector<Car> getFrontCars()
	{

		Vector<Car> toReturn = new Vector<>();
		Vector<Car> cars = fLane.getCars();
		int index = cars.indexOf(this);
		if (index > 0)
		{
			toReturn.add(cars.get(index - 1));
			return toReturn;
		}
		Collection<Lane> lanes = fLane.getEnd().getOutputLanes();
		//lanes.addAll(fLane.getStart().getOutputLanes());
		for (Lane lane : lanes)
		{
			Vector<Car> carsOnLane = lane.getCars();
			if (!carsOnLane.isEmpty())
			{
				toReturn.add(carsOnLane.get(carsOnLane.size() - 1));
			}

		}

		return toReturn;
	}

	public float getLength()
	{
		return fLength;
	}

	@Override
	public double getAngle()
	{
		return getLane().getTrajectory().getAngle(this);
	}

	public void setSpeed(double speed)
	{
		if (speed < 0)
		{
			fSpeed = 0;
		}
		else
		{
			fSpeed = speed;
		}
	}

	public IPathFindingAlgorithm getPathFinder()
	{
		return fPathFinder;
	}

	public void setPath(AbstractPathFinder path)
	{
		fPathFinder = path;
	}

	@Override
	public void handleEvent(IEvent event)
	{
		switch (event.getType())
		{
			case IEvent.EVENT_STREET_PLACEMENT:
				StreetPlacementEvent spEvent = (StreetPlacementEvent) event;
				Street street = spEvent.getStreet();
				boolean canRecalc = street != null && fPathFinder != null;
				if (canRecalc && fPathFinder.getDestination() != null && fPathFinder.getDestination().getStreet() != null && fPathFinder.getDestination().getStreet().equals(street))
				{
					recalcPath(null);
				}
				else if (canRecalc && fPathFinder.containsStreet(street))
				{
					recalcPath(fPathFinder.getDestination());
				}
				break;
			default:
		}
	}
}


