package streetmap.map.side;

import streetmap.SSGlobals;
import streetmap.map.tile.Tile;

import java.awt.geom.Point2D;

public class VerticalSide extends Side
{

	/**
	 * This class describes a side with vertical orientation, meaning east or west
	 *
	 * @param glob     globals
	 * @param position center of this side
	 */
	public VerticalSide(SSGlobals glob, Tile tile, Point2D position, String compassPoint)
	{
		super(glob, tile, position, compassPoint);
	}

	public void setAnchors()
	{
		fAnchorOne = new Anchor(this, new Point2D.Double(getPosition().getX(), getPosition().getY() + fTile.getWidth() / 8), fCompassPoint);
		fAnchorTwo = new Anchor(this, new Point2D.Double(getPosition().getX(), getPosition().getY() - fTile.getWidth() / 8), fCompassPoint);
	}

}