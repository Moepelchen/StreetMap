package streetmap.map.side;

import streetmap.map.street.Lane;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An anchor is a point where a lane can connect to
 * currently each Side as two Anchors, this might be changed
 * in future releases
 */
public class Anchor
{
    public Side getSide()
    {
        return fSide;
    }

    private final Side fSide;

    /**
     * Position of this anchor
     */
    private Point2D fPosition;
    /**
     * Lanes connected to this anchor
     */
    private ArrayList<Lane> fOutputLanes;
    private ArrayList<Lane> fInputLanes;
    /**
     * indicates that the anchor is currently blocked, meaning that the cars can not move any further
     */
    private boolean fBlocked;

    public Anchor(Side horizontalSide, Point2D position, String compassPoint)
    {
        fSide = horizontalSide;
        fPosition = position;
        fOutputLanes = new ArrayList<Lane>();
        fInputLanes = new ArrayList<Lane>();

    }

    public void setBlocked(boolean fBlocked)
    {
        this.fBlocked = fBlocked;
    }

    public boolean isBlocked()
    {
        return this.fBlocked;
    }

    public Point2D getPosition()
    {
        return fPosition;
    }

    public void setPosition(Point2D position)
    {
        this.fPosition = position;
    }

	public void addOutputLane(Lane lane)
    {

        fOutputLanes.add(lane);
    }

    public Lane getRandomLane()
    {
        int index = (int) (Math.floor(fOutputLanes.size() * Math.random()));
		if (fOutputLanes.size() > index)
		{

			Lane lane = fOutputLanes.get(index);
            if (!lane.isBlocked())
            {
                return lane;
            }

        }
        return null;
    }

    public void removeOutputLane(Lane lane)
    {
        fOutputLanes.remove(lane);
    }

    public Collection<Lane> getOutputLanes()
    {
        return fOutputLanes;
    }

    public Lane getParallelLane()
    {
        Anchor parallelAnchor = fSide.getParallelAnchor(this);
        return parallelAnchor.getRandomLane();
    }

	public ArrayList<Lane> getInputLanes()
	{
		return fInputLanes;
	}

	public void addInputLane(Lane lane)
    {
        fInputLanes.add(lane);
    }

    public void removeInputLane(Lane lane)
    {
        fInputLanes.remove(lane);
    }
}