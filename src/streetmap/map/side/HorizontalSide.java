package streetmap.map.side;

import streetmap.SSGlobals;
import streetmap.map.tile.Tile;

import java.awt.geom.Point2D;

public class HorizontalSide extends Side
{

	/**
	 * This class describes a side with horizontal orientation, meaning south or north
	 *
	 * @param glob     globals
	 * @param position center of this side
	 */
	public HorizontalSide(SSGlobals glob, Tile tile, Point2D position, String compassPoint)
	{
		super(glob, tile, position, compassPoint);
	}

	public void setAnchors()
	{
		fAnchorOne = new Anchor(new Point2D.Double(getPosition().getX() - fTile.getWidth() / 8, getPosition().getY()), fCompassPoint);
		fAnchorTwo = new Anchor(new Point2D.Double(getPosition().getX() + fTile.getWidth() / 8, getPosition().getY()), fCompassPoint);

	}

}