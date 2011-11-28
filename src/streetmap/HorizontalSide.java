package streetmap;

import java.awt.geom.Point2D;

public class HorizontalSide extends Side {

    /**
     * This class describes a side with horizontal orientation, meaning south or north
     * @param glob globals
     * @param position center of this side
     */
    public HorizontalSide(SSGlobals glob, Point2D position,String compassPoint) {
        super(glob, position,compassPoint);
    }

    public void setAnchors() {
        fAnchorOne = new Anchor(new Point2D.Double(getPosition().getX() - getGlobals().getConfig().getTileSize() / 4, getPosition().getY()),fCompassPoint);
        fAnchorTwo = new Anchor(new Point2D.Double(getPosition().getX() + getGlobals().getConfig().getTileSize() / 4, getPosition().getY()),fCompassPoint);

    }

}