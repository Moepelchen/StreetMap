import java.awt.geom.Point2D;

public class HorizontalSide extends Side {


	public HorizontalSide(Globals glob,Point2D position) {
		super(glob,position);
	}

	public void setAnchors() {
		fAnchorOne = new Anchor(new Point2D.Double(getPosition().getX()+ getGlobals().getConfig().getTileSize()/4, getPosition().getY()));
		fAnchorTwo = new Anchor(new Point2D.Double(getPosition().getX()- getGlobals().getConfig().getTileSize()/4, getPosition().getY()));

  }

}