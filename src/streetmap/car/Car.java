package streetmap.car;

import streetmap.interfaces.IPathFindingAlgorithm;
import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.map.street.Lane;
import streetmap.map.street.trajectory.ITrajectory;
import streetmap.pathfinding.AbstractPathFinder;
import streetmap.rules.RightBeforeLeftRule;
import streetmap.utils.DrawHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Vector;

/**
 * This class represents one car moving on the map
 */
public class Car implements IPrintable, ISimulateable
{

    public static final int COLOR_HAPPINESS = 120;
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
	private ImageIcon fImage;
	private double fSpeed;
	private final double fOriginalSpeed;
	private double fLength;
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


	public Car(Lane lane, Point2D pos, ImageIcon carImage, double length)
	{
		fLength = length;
		fLane = lane;
		fPosition = pos;
		fColor = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
		fImage = carImage;
		double v = Math.random() + length / 2;
		fSpeed = v;
		fOriginalSpeed = v;
		fLane.getGlobals().getMap().getPathfactory().createPath(this);

	}

	public void print(Graphics2D g)
	{

		try
		{
			int red = Math.min((int) (COLOR_HAPPINESS * (1 - fHappiness)), COLOR_HAPPINESS);
			int green = 0;
			if(fHappiness == 1)
			{
				green = Math.min((int) (COLOR_HAPPINESS * (fHappiness)), COLOR_HAPPINESS);
			}
			Color color = new Color(red, green, 0);
			DrawHelper.drawCar(this, fColor);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}

		//DrawHelper.drawFronCars(this, getFrontCars());

	}

	public void simulate()
	{
		if(fLane.getGlobals().getMap().isRecalcPaths() && fPathFinder != null)
			fLane.getGlobals().getMap().getPathfactory().createPath(this, fPathFinder.getDestination());
		move();
		fHappiness = Math.min(1,fSpeed / fOriginalSpeed);
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
				if(fSpeed > fOriginalSpeed)
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
		return (getLane().getGlobals().getConfig().getTileSize() / 50) * fSpeed;
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
		if(fHappiness < 0.5)
	            fLane.getGlobals().getMap().getPathfactory().createPath(this, fPathFinder.getDestination());
	}

	public ImageIcon getImage()
	{
		return fImage;
	}

	private Vector<Car> getFrontCars()
	{

		Vector<Car> toReturn = new Vector<Car>();
		Vector<Car> cars = fLane.getCars();
		int index = cars.indexOf(this);
		if (index > 0)
		{
			toReturn.add(cars.get(index - 1));
			return toReturn;
		}
		Collection<Lane> lanes = fLane.getEnd().getOutputLanes();
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

	private Vector<Car> getCrossingCars()
	{
		Vector<Car> toReturn = new Vector<Car>();
		Collection<Lane> lanes = fLane.getEnd().getInputLanes();
		String compassPoint = fLane.getStart().getSide().getCompassPoint();
		for (Lane lane : lanes)
		{
			if (!lane.equals(fLane) && RightBeforeLeftRule.doesApply(lane, compassPoint))
			{
				Vector<Car> carsOnLane = lane.getCars();
				if (!carsOnLane.isEmpty())
				{
					toReturn.addAll(carsOnLane);
				}
			}

		}
		return toReturn;
	}

	public double getLength()
	{
		return fLength;
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
}