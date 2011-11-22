import java.awt.*;
import java.awt.geom.Point2D;

public class Tile implements IPrintable, ISimulateable {


	/**
	 * ___________+ ___________
	 * |                        |
	 * |                        |
	 * |                        |
	 * |                        |
	 * +                        +
	 * |                        |
	 * |                        |
	 * |                        |
	 * |                        |
	 * ___________+ ___________
	 * <p/>
	 * + = Side refernece
	 */


	private HorizontalSide fNorthSide;

	private HorizontalSide fSouthSide;

	private VerticalSide fWestSide;

	private VerticalSide fEastSide;

	private Tile fNorthNeighbor;

	private Tile fEastNeighbor;

	private Tile fSouthNeighbor;

	private Tile fWestNeighbor;

	private Point2D fArrayPosition;

	private boolean fSidesGenerated;
	public Street fStreet;
	private Point2D fPosition;

	public HorizontalSide getfNorthSide() {
		return fNorthSide;
	}

	public void setfNorthSide(HorizontalSide fNorthSide) {
		this.fNorthSide = fNorthSide;
	}

	public HorizontalSide getfSouthSide() {
		return fSouthSide;
	}

	public void setfSouthSide(HorizontalSide fSouthSide) {
		this.fSouthSide = fSouthSide;
	}

	public VerticalSide getfWestSide() {
		return fWestSide;
	}

	public void setfWestSide(VerticalSide fWestSide) {
		this.fWestSide = fWestSide;
	}

	public VerticalSide getfEastSide() {
		return fEastSide;
	}

	public void setfEastSide(VerticalSide fEastSide) {
		this.fEastSide = fEastSide;
	}

	public Tile getfNorthNeighbor() {
		return fNorthNeighbor;
	}

	public void setfNorthNeighbor(Tile fNorthNeighbor) {
		this.fNorthNeighbor = fNorthNeighbor;
	}

	public Tile getfEastNeighbor() {
		return fEastNeighbor;
	}

	public void setfEastNeighbor(Tile fEastNeighbor) {
		this.fEastNeighbor = fEastNeighbor;
	}

	public Tile getfSouthNeighbor() {
		return fSouthNeighbor;
	}

	public void setfSouthNeighbor(Tile fSouthNeighbor) {
		this.fSouthNeighbor = fSouthNeighbor;
	}

	public Tile getfWestNeighbor() {
		return fWestNeighbor;
	}

	public void setfWestNeighbor(Tile fWestNeighbor) {
		this.fWestNeighbor = fWestNeighbor;
	}

	public Point2D getfArrayPosition() {
		return fArrayPosition;
	}

	public void setfArrayPosition(Point2D fArrayPosition) {
		this.fArrayPosition = fArrayPosition;
	}

	public Street getfStreet() {
		return fStreet;
	}

	public void setfStreet(Street fStreet) {
		this.fStreet = fStreet;
	}

	public Globals getfGlobals() {
		return fGlobals;
	}

	public void setfGlobals(Globals fGlobals) {
		this.fGlobals = fGlobals;
	}

	public Map getfMap() {
		return fMap;
	}

	public void setfMap(Map fMap) {
		this.fMap = fMap;
	}

	public double getfWidth() {
		return fWidth;
	}

	public void setfWidth(double fWidth) {
		this.fWidth = fWidth;
	}

	private Globals fGlobals;

	private Map fMap;

	private double fWidth;

	public Tile(Globals globals, Map map, Point2D arrayPosition) {
		fArrayPosition = arrayPosition;
		fSidesGenerated = false;
		fGlobals = globals;
		fMap = map;
		fWidth = fGlobals.getfConfig().getTileSize();
		fNorthNeighbor = map.getTile(arrayPosition.getX(), arrayPosition.getY() - 1);
		fSouthNeighbor = map.getTile(arrayPosition.getX(), arrayPosition.getY() + 1);
		fWestNeighbor = map.getTile(arrayPosition.getX() - 1, arrayPosition.getY());
		fEastNeighbor = map.getTile(arrayPosition.getX() + 1, arrayPosition.getY());
		fPosition = new Point2D.Double(fArrayPosition.getX() * fWidth, fArrayPosition.getY() * fWidth);
		generateSides();

	}

	public void generateSides() {

		//North
		if (getfNorthNeighbor() != null && getfNorthNeighbor().getfSouthSide() != null) {
			setfNorthSide(getfNorthNeighbor().getfSouthSide());
		} else
			fNorthSide = new HorizontalSide(fGlobals, new Point2D.Double(fPosition.getX() + fWidth / 2, fPosition.getY()));

		// South
		if (getfSouthNeighbor() != null && getfSouthNeighbor().getfNorthSide() != null) {
			setfSouthSide(getfSouthNeighbor().getfNorthSide());
		} else
			fSouthSide = new HorizontalSide(fGlobals, new Point2D.Double(fPosition.getX() + fWidth / 2, fPosition.getY() + fWidth));

		// West
		if (getfWestNeighbor() != null && getfWestNeighbor().getfEastSide() != null) {
			setfWestSide(getfWestNeighbor().getfEastSide());
		} else
			fWestSide = new VerticalSide(fGlobals, new Point2D.Double(fPosition.getX(), fPosition.getY() + fWidth / 2));

		// East
		if (getfEastNeighbor() != null && getfEastNeighbor().getfWestSide() != null) {
			setfEastSide(getfEastNeighbor().getfWestSide());
		} else
			fEastSide = new VerticalSide(fGlobals, new Point2D.Double(fPosition.getX() + fWidth, fPosition.getY() + fWidth / 2));

	}

	public void print(Graphics2D g) {
		if (fGlobals.getfConfig().isDrawTiles()) {
			g.setColor(Color.black);

			g.drawRect((int) fPosition.getX(), (int) fPosition.getY(), (int) fWidth, (int) fWidth);
		}

		if (fGlobals.getfConfig().isDrawSides()) {
			if (fNorthSide != null)
				fNorthSide.print(g);
			if (fSouthSide != null)
				fSouthSide.print(g);
			if (fWestSide != null)
				fWestSide.print(g);
			if (fEastSide != null)
				fEastSide.print(g);
		}
	}

	public void simulate() {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}