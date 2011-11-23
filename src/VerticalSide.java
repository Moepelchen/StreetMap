import java.awt.geom.Point2D;

public class VerticalSide extends Side {


    public VerticalSide(Globals glob, Point2D position) {
        super(glob, position);
    }

    public void setAnchors() {
        fAnchorOne = new Anchor(new Point2D.Double(getfPosition().getX(), getfPosition().getY() + getfGlobals().getfConfig().getTileSize() / 4));
        fAnchorTwo = new Anchor(new Point2D.Double(getfPosition().getX(), getfPosition().getY() - getfGlobals().getfConfig().getTileSize() / 4));
    }

}