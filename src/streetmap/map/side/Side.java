package streetmap.map.side;

import streetmap.SSGlobals;
import streetmap.map.tile.Tile;

import java.awt.geom.Point2D;

public abstract class Side
{

	Anchor fAnchorOne;
	Anchor fAnchorTwo;
	private final Point2D fPosition;
	final String fCompassPoint;
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

	Point2D getPosition()
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

	Side(SSGlobals globals, Tile tile, Point2D position, String compassPoint)
	{
		fPosition = position;
		fGlobals = globals;
		fCompassPoint = compassPoint;
		fTile = tile;
		setAnchors();
	}

	void setAnchors()
	{
		// empty on purpose
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