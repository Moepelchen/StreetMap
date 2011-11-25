package streetmap;

import streetmap.interfaces.*;

import java.awt.*;
import java.awt.geom.Point2D;

public class Anchor implements IPrintable {

    private Point2D fPosition;

    private boolean fBlocked;

    public Anchor(Point2D position) {
        fPosition = position;

    }

    public boolean isfBlocked() {
        return fBlocked;
    }

    public void setBlocked(boolean fBlocked) {
        this.fBlocked = fBlocked;
    }

    public boolean isBlocked() {
        return false;
    }

    public Point2D getPosition() {
        return fPosition;
    }

    public void setPosition(Point2D position) {
        this.fPosition = position;
    }

    public void print(Graphics2D g) {
        g.setColor(Color.green);
        g.drawRect((int) getPosition().getX() - 1, (int) getPosition().getY() - 1, 2, 2);
    }
}