import java.awt.*;
import java.awt.geom.Point2D;

public class Side implements IPrintable{

	protected Anchor fAnchorOne;

	protected Anchor fAnchorTwo;

	private Point2D fPosition;


	private Globals fGlobals;

	public Anchor getfAnchorOne() {
		return fAnchorOne;
	}


	public Anchor getAnchorTwo() {
		return fAnchorTwo;
	}

	public Point2D getPosition() {
		return fPosition;
	}

	public Globals getGlobals() {
		return fGlobals;
	}

	public Side(Globals globals, Point2D position) {
		fPosition = position;
		fGlobals = globals;
		setAnchors();
	}

	public void setAnchors() {
		// empty on purpose
	}

	public void print(Graphics2D g) {

		if(fGlobals.getConfig().isDrawSides()){
			g.setColor(Color.red);
			g.drawRect((int)(fPosition.getX())-2,(int)fPosition.getY()-2,5,5);
		}
		if(fGlobals.getConfig().isDrawAnchors()){
			fAnchorOne.print(g);
			fAnchorTwo.print(g);
		}


	}
}