package streetmap;

import streetmap.Interfaces.IPrintable;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Side implements IPrintable
{

	protected Anchor fAnchorOne;

	protected Anchor fAnchorTwo;

	protected Point2D fPosition;

	protected String fCompassPoint;

	protected Tile fTile;

	public Anchor getAnchorOne()
	{
		return fAnchorOne;
	}


	public Anchor getAnchorTwo()
	{
		return fAnchorTwo;
	}

	public Point2D getPosition()
	{
		return fPosition;
	}

	public SSGlobals getGlobals()
	{
		return fGlobals;
	}

	private SSGlobals fGlobals;

	public Side(SSGlobals globals, Tile tile, Point2D position, String compassPoint)
	{
		fTile = tile;
		fPosition = position;
		fGlobals = globals;
		fCompassPoint = compassPoint;
		setAnchors();
	}

	public void setAnchors()
	{
		// empty on purpose
	}

	public void print(Graphics2D g)
	{

		if (fGlobals.getConfig().isDrawSides())
		{
			g.setColor(Color.red);
			g.drawRect((int) (fPosition.getX()) - 2, (int) fPosition.getY() - 2, 5, 5);
		}
		if (fGlobals.getConfig().isDrawAnchors())
		{
			g.setColor(Color.green);
			fAnchorOne.print(g);
			g.setColor(Color.MAGENTA);
			fAnchorTwo.print(g);
		}


	}

	public boolean isHorizontal()
	{
		return this instanceof HorizontalSide;

	}

	public boolean isVertical()
	{
		return this instanceof VerticalSide;

	}
}