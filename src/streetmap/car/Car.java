package streetmap.car;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;
import streetmap.Interfaces.ITrajectory;
import streetmap.Lane;
import streetmap.Utils.DrawHelper;

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

	public void setLane(Lane fLane)
	{
		this.fLane = fLane;
	}

	Car(Lane lane, Point2D pos, ImageIcon carImage, double length)
	{
		fLength = length;
		fLane = lane;
		fPosition = pos;
		fColor = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
		fImage = carImage;
		double v = Math.random() + length/20;
		fSpeed = v;
		fOriginalSpeed = v;
	}

	public void print(Graphics2D g)
	{
		DrawHelper.drawCar(this, fColor);
		//DrawHelper.drawFronCars(this, getFrontCars());

	}

	public void simulate()
	{
		move();
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
			if (distance < 2.5 * car.getLength() )
			{

				setSpeed(car.getSpeedModifier() - 0.3 * car.getSpeedModifier());
				caped = true;
				break;


			}
		}
		if (!caped)
			fSpeed = fOriginalSpeed;
		ITrajectory trajectory = fLane.getTrajectory();
		if (trajectory != null)
		{


                trajectory.relaocate(this);

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
		if (!(index == 0))
		{
			toReturn.add(cars.get(index - 1));
			return toReturn;
		}
		Collection<Lane> lanes = fLane.getEnd().getLanes();
		for (Lane lane : lanes)
		{
			Vector<Car> carsOnLane = lane.getCars();
			if (!carsOnLane.isEmpty())
				toReturn.add(carsOnLane.get(carsOnLane.size() - 1));

		}


		return toReturn;
	}


	public double getLength()
	{
		return fLength;
	}

    public void setSpeed(double speed) {
        if(speed <0)
            fSpeed = 0;
        else
            fSpeed = speed;
    }
}