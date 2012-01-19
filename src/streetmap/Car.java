package streetmap;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;
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
    private Image fImage;


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

	public Car(Lane lane, Point2D pos)
	{
		fLane = lane;
		fPosition = pos;
		fColor = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
        fImage = new ImageIcon("./images/cop_car.png").getImage();
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
		StraightTrajectory trajectory = fLane.getTrajectory();
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

    public Image getImage() {
        return fImage;
    }
}