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

	private boolean isEnd;
    private String fTo;
    private String fFrom;

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
			g.drawLine((int) fStartAnchor.getPosition().getX(), (int) fStartAnchor.getPosition().getY(), (int) fEndAnchor.getPosition().getX(), (int) fEndAnchor.getPosition().getY());
		}
		//drawCars(g);
	}

    private void drawCars(Graphics2D g) {
        for (Car fCar : fCars) {
            fCar.print(g);
        }
    }

    public void simulate()
	{
		Vector<Car> toRemoveCars = new Vector<Car>();
		if (Math.random() < 0.05 && this.isStartLane()&& fCars.size()<1)
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
				Lane randomOtherLane = fEndAnchor.getRandomLane();
				if (randomOtherLane != null)
					randomOtherLane.addCar(fCar);
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

    /**
     * determines whether the car is still on this lane
     * @param fCar car to test with
     * @return
     */
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
		return false;  
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
    
    public void setTo(String to){
        fTo = to;
    }

    public String getTo() {
        return fTo;
    }

    public String getFrom() {
        return fFrom;
    }

    public void setFrom(String fFrom) {
        this.fFrom = fFrom;
    }
}