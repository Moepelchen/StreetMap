package streetmap.car;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
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
import streetmap.rules.RightBeforeLeftRule;
import streetmap.utils.DrawHelper;

import java.awt.*;
import java.awt.geom.Point2D;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Collection;
import java.util.Vector;

/**
 * This class represents one car moving on the map
 */
public class
        Car implements IPrintable, ISimulateable, IEventHandler
{

    public static final int COLOR_HAPPINESS = 120;
    private int fIndicesCount;

    public int getVBOId2()
    {
        return fVBOId2;
    }

    public int getVBOId()
    {
        return fVBOId;
    }

    public int getVAOId()
    {
        return fVAOId;
    }

    private int fVBOId2;
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
	private double fLength;
	private double fHappiness;
    private IPathFindingAlgorithm fPathFinder;
    private int fVBOId = 0;
    private int fVAOId = 0;

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


    public Car(Lane lane, Point2D pos, String carImagePath, float length)
    {
        fLength = length;
        fLane = lane;
        fPosition = pos;
        fColor = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
        fImagePath = carImagePath;
        double v = Math.random() + lane.getGlobals().getConfig().getMaximumCarSpeed();
        fSpeed = v;
        fOriginalSpeed = v;
        fLane.getGlobals().getMap().getPathFactory().createPath(this);
        //initBuffers(length);
    }

    private void initBuffers(float length)
    {
        float x = (float) (this.getPosition().getX() - length / 2);
        float y = (float) (this.getPosition().getY() - length / 2);
        float[] vertices = {x, y, 0,
                x + length, y, 0,
                x + length, y + length, 0,
                x, y + length};
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        byte[] indices = {0, 1, 2, 2, 3, 0};
        fIndicesCount = indices.length;
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(fIndicesCount);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        fVAOId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(fVAOId);

        fVBOId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, fVBOId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);

        // Put the VBO in the attributes list at index 0

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        // Deselect (bind to 0) the VBO

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);


        // Deselect (bind to 0) the VAO

        GL30.glBindVertexArray(0);

        // Create a new VBO for the indices and select it (bind)
        fVBOId2 = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, fVBOId2);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);

        // Deselect (bind to 0) the VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void print()
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
		for (IEvent event : fLane.getGlobals().getMap().getEvents())
		{
			handleEvent(event);
		}
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
		if(fHappiness < 0.10&& fPathFinder != null && Math.random() >0.10)
	            recalcPath();
	}

	protected void recalcPath()
	{
		fLane.getGlobals().getMap().getPathFactory().createPath(this, fPathFinder.getDestination());
	}

	public String getImagePath()
	{
		return fImagePath;
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

    @Override
    public void handleEvent(IEvent event)
    {
        switch (event.getType())
        {
            case IEvent.EVENT_STREET_PLACEMENT:
                StreetPlacementEvent spEvent = (StreetPlacementEvent) event;
                Street street = spEvent.getStreet();
                if (street != null && fPathFinder != null && fPathFinder.getDestination().getStreet() != null && fPathFinder.getDestination().getStreet().equals(street))
                {
                    fLane.getGlobals().getMap().getPathFactory().createPath(this);
                } else if (street != null && fPathFinder != null && fPathFinder.containsStreet(street))
                {
                    recalcPath();
                }
                break;
            default:
        }
    }

    public int getIndicesCount()
    {
        return fIndicesCount;
    }
}