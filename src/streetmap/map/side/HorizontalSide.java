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
		fAnchorOne = new Anchor(this,new Point2D.Double(getPosition().getX() - getGlobals().getMap().getTileWidth() / 8, getPosition().getY()));
		fAnchorTwo = new Anchor(this,new Point2D.Double(getPosition().getX() + getGlobals().getMap().getTileWidth() / 8, getPosition().getY()));

	}

}