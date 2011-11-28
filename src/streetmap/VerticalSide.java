package streetmap;

import java.awt.geom.Point2D;

public class VerticalSide extends Side {


    /**
     * This class describes a side with vertical orientation, meaning east or west
     * @param glob globals
     * @param position center of this side
     */
    public VerticalSide(SSGlobals glob, Point2D position, String compassPoint) {
        super(glob, position,compassPoint);
    }

    public void setAnchors() {
        fAnchorOne = new Anchor(new Point2D.Double(getPosition().getX(), getPosition().getY() + getGlobals().getConfig().getTileSize() / 4),fCompassPoint);
        fAnchorTwo = new Anchor(new Point2D.Double(getPosition().getX(), getPosition().getY() - getGlobals().getConfig().getTileSize() / 4),fCompassPoint);
    }

}