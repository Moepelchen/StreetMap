package streetmap;

import streetmap.Interfaces.*;

import java.awt.*;
import java.awt.geom.Point2D;

public class Side implements IPrintable {

    protected Anchor fAnchorOne;

    protected Anchor fAnchorTwo;

    private Point2D fPosition;

    public Anchor getAnchorOne() {
        return fAnchorOne;
    }


    public Anchor getAnchorTwo() {
        return fAnchorTwo;
    }

    public Point2D getPosition() {
        return fPosition;
    }

    public SSGlobals getGlobals() {
        return fGlobals;
    }

    private SSGlobals fGlobals;

    public Side(SSGlobals globals, Point2D position) {
        fPosition = position;
        fGlobals = globals;
        setAnchors();
    }

    public void setAnchors() {
        // empty on purpose
    }

    public void print(Graphics2D g) {

        if (fGlobals.getConfig().isDrawSides()) {
            g.setColor(Color.red);
            g.drawRect((int) (fPosition.getX()) - 2, (int) fPosition.getY() - 2, 5, 5);
        }
        if (fGlobals.getConfig().isDrawAnchors()) {
            fAnchorOne.print(g);
            fAnchorTwo.print(g);
        }


    }
}