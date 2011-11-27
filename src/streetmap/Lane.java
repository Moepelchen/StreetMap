package streetmap;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;

import java.awt.*;
import java.util.Vector;

public class Lane implements IPrintable, ISimulateable {

    public Anchor fStartAnchor;

    public Anchor fEndAnchor;

    public Vector<Car> fCars;

    public void print(Graphics2D g) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void simulate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}