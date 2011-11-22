import java.awt.*;
import java.awt.geom.Point2D;

public class Side implements IPrintable{

	protected Anchor fAnchorOne;

	protected Anchor fAnchorTwo;

	private Point2D fPosition;

	public Anchor getfAnchorOne() {
		return fAnchorOne;
	}


	public Anchor getfAnchorTwo() {
		return fAnchorTwo;
	}

	public Point2D getfPosition() {
		return fPosition;
	}

	public Globals getfGlobals() {
		return fGlobals;
	}

	private Globals fGlobals;

	public Side(Globals globals, Point2D position) {
		fPosition = position;
		fGlobals = globals;
		setAnchors();
	}

	public void setAnchors() {
		// empty on purpose
	}

	public void print(Graphics2D g) {

		if(fGlobals.getfConfig().isDrawSides()){
			g.setColor(Color.red);
			g.drawRect((int)(fPosition.getX())-2,(int)fPosition.getY()-2,5,5);
		}
		if(fGlobals.getfConfig().isDrawAnchors()){
			fAnchorOne.print(g);
			fAnchorTwo.print(g);
		}


	}
}