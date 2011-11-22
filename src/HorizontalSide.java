import java.awt.geom.Point2D;

public class HorizontalSide extends Side {


	public HorizontalSide(Globals glob,Point2D position) {
		super(glob,position);
	}

	public void setAnchors() {
		fAnchorOne = new Anchor(new Point2D.Double(getfPosition().getX()+getfGlobals().getfConfig().getTileSize()/4,getfPosition().getY()));
		fAnchorTwo = new Anchor(new Point2D.Double(getfPosition().getX()-getfGlobals().getfConfig().getTileSize()/4,getfPosition().getY()));

  }

}