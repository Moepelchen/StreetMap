package streetmap.car;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;
import streetmap.Interfaces.ITrajectory;
import streetmap.Lane;
import streetmap.Utils.DrawHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * This class represents cars moving on the map
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

	Car(Lane lane, Point2D pos, ImageIcon carImage)
	{
		fLane = lane;
		fPosition = pos;
		fColor = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
        fImage = carImage;
	}

	public void print(Graphics2D g)
	{
		DrawHelper.drawCar(g, this, fColor);
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
		ITrajectory trajectory = fLane.getTrajectory();
		if (trajectory != null)
			setPosition(trajectory.calculatePosition(fPosition, getLane().getGlobals().getConfig().getTileSize() / 50));
	}

    /**
     *
     * @param lane
     */
	public void reset(Lane lane){
		fLane = lane;
	}

    public ImageIcon getImage() {
        return fImage;
    }


}