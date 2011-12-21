package streetmap;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;

import java.awt.*;
import java.util.Vector;

/**
 * This is the implementation of a Street in the simulation. A Street Consists of a Number of lanes
 */
public class Street implements IPrintable, ISimulateable {


    private Vector<Lane> fLanes;

	private String fName;

    public Street(String name) {
	    fName = name;
        fLanes = new Vector<Lane>();
    }


    public Vector<Lane> getLanes() {
        return fLanes;
    }

    public void setLanes(Vector<Lane> fLanes) {
        this.fLanes = fLanes;
    }

    public void print(Graphics2D g) {
        for (Lane lane : fLanes) {
            lane.print(g);

        }
    }

    public void simulate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addLane(Lane lane) {
        fLanes.add(lane);
    }

	 public String toString() {
        return fName;
    }
}