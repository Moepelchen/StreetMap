package streetmap.map.tile;

import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.SSGlobals;
import streetmap.map.side.HorizontalSide;
import streetmap.map.street.Lane;
import streetmap.map.Map;
import streetmap.map.side.Side;
import streetmap.map.street.Street;
import streetmap.map.side.VerticalSide;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Vector;

public class Tile implements IPrintable, ISimulateable
{
	public static final String COMPASS_POINT_N = "N";
	public static final String COMPASS_POINT_S = "S";
	public static final String COMPASS_POINT_W = "W";
	public static final String COMPASS_POINT_E = "E";

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
	 * + = Side reference
	 */

	/**
	 * HashMap of the four sides
	 */
	private HashMap<String, Side> fSides;

	private HashMap<String, Tile> fNeighbors;

	private Point2D fArrayPosition;

	private boolean fSidesGenerated;
	public Street fStreet;
	private Point2D fPosition;

	public HorizontalSide getNorthSide()
	{
		return (HorizontalSide) getSide(COMPASS_POINT_N);
	}

	public HorizontalSide getSouthSide()
	{
		return (HorizontalSide) getSide(COMPASS_POINT_S);
	}

	public VerticalSide getWestSide()
	{
		return (VerticalSide) getSide(COMPASS_POINT_W);
	}

	public VerticalSide getEastSide()
	{
		return (VerticalSide) getSide(COMPASS_POINT_E);
	}

	public Tile getNorthNeighbor()
	{
		return fNeighbors.get(COMPASS_POINT_N);
	}

	public Tile getEastNeighbor()
	{
		return fNeighbors.get(COMPASS_POINT_E);
	}

	public Tile getSouthNeighbor()
	{
		return fNeighbors.get(COMPASS_POINT_S);
	}

	public Tile getWestNeighbor()
	{
		return fNeighbors.get(COMPASS_POINT_W);
	}

	public Point2D getArrayPosition()
	{
		return fArrayPosition;
	}

	public void setArrayPosition(Point2D fArrayPosition)
	{
		this.fArrayPosition = fArrayPosition;
	}

	public Street getStreet()
	{
		return fStreet;
	}

	public void setStreet(Street fStreet)
	{
		this.fStreet = fStreet;
	}

	public SSGlobals getGlobals()
	{
		return fGlobals;
	}

	public void setGlobals(SSGlobals fGlobals)
	{
		this.fGlobals = fGlobals;
	}

	public Map getMap()
	{
		return fMap;
	}

	public void setMap(Map fMap)
	{
		this.fMap = fMap;
	}

	public double getWidth()
	{
		return fWidth;
	}

	public void setWidth(double fWidth)
	{
		this.fWidth = fWidth;
	}

	private SSGlobals fGlobals;

	private Map fMap;

	private double fWidth;

	public Tile(SSGlobals globals, Map map, Point2D arrayPosition, double width)
	{
		fWidth = width;
		init(globals, map, arrayPosition);

	}

	public Tile(SSGlobals globals, Map map, Point2D arrayPosition)
	{
		fWidth = globals.getConfig().getTileSize();
		init(globals, map, arrayPosition);
	}

	private void init(SSGlobals globals, Map map, Point2D arrayPosition)
	{
		fArrayPosition = arrayPosition;
		fSides = new HashMap<String, Side>(4);
		fNeighbors = new HashMap<String, Tile>(4);
		fSidesGenerated = false;
		fGlobals = globals;
		fMap = map;

		if (map != null)
		{
			fNeighbors.put(COMPASS_POINT_N, map.getTile(arrayPosition.getX(), arrayPosition.getY() - 1));
			fNeighbors.put(COMPASS_POINT_S, map.getTile(arrayPosition.getX(), arrayPosition.getY() + 1));
			fNeighbors.put(COMPASS_POINT_W, map.getTile(arrayPosition.getX() - 1, arrayPosition.getY()));
			fNeighbors.put(COMPASS_POINT_E, map.getTile(arrayPosition.getX() + 1, arrayPosition.getY()));
		}
		fPosition = new Point2D.Double(fArrayPosition.getX() * fWidth, fArrayPosition.getY() * fWidth);
		generateSides();
	}

	public void generateSides()
	{

		//North
		if (getNorthNeighbor() != null && getNorthNeighbor().getSouthSide() != null)
		{
			fSides.put(COMPASS_POINT_N, getNorthNeighbor().getSouthSide());
		}
		else
		{
			fSides.put(COMPASS_POINT_N, new HorizontalSide(fGlobals, this, new Point2D.Double(fPosition.getX() + fWidth / 2, fPosition.getY()), COMPASS_POINT_N));
		}

		// South
		if (getSouthNeighbor() != null && getSouthNeighbor().getNorthSide() != null)
		{
			fSides.put(COMPASS_POINT_S, getSouthNeighbor().getNorthSide());
		}
		else
		{
			fSides.put(COMPASS_POINT_S, new HorizontalSide(fGlobals, this, new Point2D.Double(fPosition.getX() + fWidth / 2, fPosition.getY() + fWidth), COMPASS_POINT_S));
		}

		// West
		if (getWestNeighbor() != null && getWestNeighbor().getEastSide() != null)
		{
			fSides.put(COMPASS_POINT_W, getWestNeighbor().getEastSide());
		}
		else
		{
			fSides.put(COMPASS_POINT_W, new VerticalSide(fGlobals, this, new Point2D.Double(fPosition.getX(), fPosition.getY() + fWidth / 2), COMPASS_POINT_W));
		}

		// East
		if (getEastNeighbor() != null && getEastNeighbor().getWestSide() != null)
		{
			fSides.put(COMPASS_POINT_E, getEastNeighbor().getWestSide());
		}
		else
		{
			fSides.put(COMPASS_POINT_E, new VerticalSide(fGlobals, this, new Point2D.Double(fPosition.getX() + fWidth, fPosition.getY() + fWidth / 2), COMPASS_POINT_E));
		}

	}

	public void print(Graphics2D g)
	{
		if (fGlobals.getConfig().isDrawTiles())
		{
			g.setColor(Color.black);

			g.drawRect((int) fPosition.getX(), (int) fPosition.getY(), (int) fWidth, (int) fWidth);
		}
		if (fStreet != null)
		{
			fStreet.print(g);
		}

		for (String s : fSides.keySet())
		{
			fSides.get(s).print(g);
		}

	}

	public void simulate()
	{
		if (fStreet != null)
		{
			fStreet.simulate();
		}
	}

	public Side getSide(String direction)
	{
		return fSides.get(direction);

	}

	public Vector<Lane> getLanes()
	{
		if (fStreet != null)
		{
			return fStreet.getLanes();
		}
		return new Vector<Lane>();
	}
}