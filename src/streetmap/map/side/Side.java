package streetmap.map.side;

import streetmap.SSGlobals;
import streetmap.interfaces.IPrintable;
import streetmap.map.tile.Tile;
import streetmap.utils.DrawHelper;

import java.awt.geom.Point2D;

public abstract class Side implements IPrintable
{

	protected Anchor fAnchorOne;
	protected Anchor fAnchorTwo;
	protected Point2D fPosition;
	protected String fCompassPoint;
	private SSGlobals fGlobals;
	private Tile fTile;

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

	public String getCompassPoint()
	{
		return fCompassPoint;
	}

	public Side(SSGlobals globals, Tile tile, Point2D position, String compassPoint)
	{
		fPosition = position;
		fGlobals = globals;
		fCompassPoint = compassPoint;
		fTile = tile;
		setAnchors();
	}

	public void setAnchors()
	{
		// empty on purpose
	}

	public void print()
	{

		DrawHelper.drawSide(this);

	}

	public boolean isHorizontal()
	{
		return this instanceof HorizontalSide;

	}

	public boolean isVertical()
	{
		return this instanceof VerticalSide;

	}

	public Anchor getParallelAnchor(Anchor anchor)
	{
		if (fAnchorOne == anchor)
		{
			return fAnchorTwo;
		}
		else
		{
			return fAnchorOne;
		}
	}

	public Tile getTile()
	{
		return fTile;
	}
}