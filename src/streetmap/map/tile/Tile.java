package streetmap.map.tile;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import streetmap.SSGlobals;
import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.map.Map;
import streetmap.map.side.HorizontalSide;
import streetmap.map.side.Side;
import streetmap.map.side.VerticalSide;
import streetmap.map.street.IPlaceable;
import streetmap.map.street.Lane;
import streetmap.map.street.Street;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Vector;

public class Tile implements ISimulateable, IPrintable
{

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
	private IPlaceable fPlaceable;
	private Point2D fPosition;
    private int fNumberOfCars = 0;
    private Rectangle2D fRect;

    public HorizontalSide getNorthSide()
	{
		return (HorizontalSide) getSide(ICompassPoint.COMPASS_POINT_N);
	}

	public HorizontalSide getSouthSide()
	{
		return (HorizontalSide) getSide(ICompassPoint.COMPASS_POINT_S);
	}

	public VerticalSide getWestSide()
	{
		return (VerticalSide) getSide(ICompassPoint.COMPASS_POINT_W);
	}

	public VerticalSide getEastSide()
	{
		return (VerticalSide) getSide(ICompassPoint.COMPASS_POINT_E);
	}

	public Tile getNorthNeighbor()
	{
		return fNeighbors.get(ICompassPoint.COMPASS_POINT_N);
	}

	public Tile getEastNeighbor()
	{
		return fNeighbors.get(ICompassPoint.COMPASS_POINT_E);
	}

	public Tile getSouthNeighbor()
	{
		return fNeighbors.get(ICompassPoint.COMPASS_POINT_S);
	}

	public Tile getWestNeighbor()
	{
		return fNeighbors.get(ICompassPoint.COMPASS_POINT_W);
	}

	public Point2D getArrayPosition()
	{
		return fArrayPosition;
	}

	public IPlaceable getPlaceable()
	{
		return fPlaceable;
	}

	public synchronized void setPlaceable(Street fStreet)
	{
		this.fPlaceable = fStreet;
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

	public float getWidth()
	{
		return fWidth;
	}

	public void setWidth(float fWidth)
	{
		this.fWidth = fWidth;
	}

	private SSGlobals fGlobals;

	private Map fMap;

	private float fWidth;

	public Point2D getPosition()
	{
		return fPosition;
	}

	@Override
	public float getLength()
	{
		return getWidth();
	}

	@Override
	public double getAngle()
	{
		return 0;
	}

	@Override
	public ReadableColor getColor()
	{
		return new Color(0,255,0,120);
	}

	@Override
	public Integer getImageId()
	{
		return 0;
	}

	@Override
	public String getImagePath()
	{
		return null;
	}

	@Override
	public float getStepWidth()
	{
		return 0;
	}

	public Tile(SSGlobals globals, Map map, Point2D arrayPosition, double width)
	{
		fWidth = (float) width;
		init(globals, map, arrayPosition);

	}

	public Tile(SSGlobals globals, Map map, Point2D arrayPosition)
	{
		fWidth = new Float(globals.getConfig().getTileSize());
		init(globals, map, arrayPosition);
	}

	private void init(SSGlobals globals, Map map, Point2D arrayPosition)
	{
		fArrayPosition = arrayPosition;
		fSides = new HashMap<>(4);
		fNeighbors = new HashMap<>(4);
		fGlobals = globals;
		fMap = map;

		if (map != null)
		{
			fNeighbors.put(ICompassPoint.COMPASS_POINT_N, map.getTile(arrayPosition.getX(), arrayPosition.getY() - 1));
			fNeighbors.put(ICompassPoint.COMPASS_POINT_S, map.getTile(arrayPosition.getX(), arrayPosition.getY() + 1));
			fNeighbors.put(ICompassPoint.COMPASS_POINT_W, map.getTile(arrayPosition.getX() - 1, arrayPosition.getY()));
			fNeighbors.put(ICompassPoint.COMPASS_POINT_E, map.getTile(arrayPosition.getX() + 1, arrayPosition.getY()));
		}
		fPosition = new Point2D.Double(fArrayPosition.getX() * fWidth, fArrayPosition.getY() * fWidth);
        if(fMap != null)
            fRect = new Rectangle2D.Double(fPosition.getX(),fPosition.getY(),fMap.getTileWidth(),fMap.getTileWidth());
		generateSides();
	}

	public void generateSides()
	{

		//North
		if (getNorthNeighbor() != null && getNorthNeighbor().getSouthSide() != null)
		{
			fSides.put(ICompassPoint.COMPASS_POINT_N, getNorthNeighbor().getSouthSide());
		}
		else
		{
			fSides.put(ICompassPoint.COMPASS_POINT_N, new HorizontalSide(fGlobals, this, new Point2D.Double(fPosition.getX() + fWidth / 2, fPosition.getY()), ICompassPoint.COMPASS_POINT_N));
		}

		// South
		if (getSouthNeighbor() != null && getSouthNeighbor().getNorthSide() != null)
		{
			fSides.put(ICompassPoint.COMPASS_POINT_S, getSouthNeighbor().getNorthSide());
		}
		else
		{
			fSides.put(ICompassPoint.COMPASS_POINT_S, new HorizontalSide(fGlobals, this, new Point2D.Double(fPosition.getX() + fWidth / 2, fPosition.getY() + fWidth), ICompassPoint.COMPASS_POINT_S));
		}

		// West
		if (getWestNeighbor() != null && getWestNeighbor().getEastSide() != null)
		{
			fSides.put(ICompassPoint.COMPASS_POINT_W, getWestNeighbor().getEastSide());
		}
		else
		{
			fSides.put(ICompassPoint.COMPASS_POINT_W, new VerticalSide(fGlobals, this, new Point2D.Double(fPosition.getX(), fPosition.getY() + fWidth / 2), ICompassPoint.COMPASS_POINT_W));
		}

		// East
		if (getEastNeighbor() != null && getEastNeighbor().getWestSide() != null)
		{
			fSides.put(ICompassPoint.COMPASS_POINT_E, getEastNeighbor().getWestSide());
		}
		else
		{
			fSides.put(ICompassPoint.COMPASS_POINT_E, new VerticalSide(fGlobals, this, new Point2D.Double(fPosition.getX() + fWidth, fPosition.getY() + fWidth / 2), ICompassPoint.COMPASS_POINT_E));
		}

	}

	public void simulate()
	{
		if (fPlaceable != null)
		{
			fPlaceable.simulate();
            fNumberOfCars = fPlaceable.getNumberOfCars();

        }
	}

	public Side getSide(String direction)
	{
		return fSides.get(direction);

	}

	public Vector<Lane> getLanes()
	{
		if (fPlaceable != null)
		{
			return fPlaceable.getLanes();
		}
		return new Vector<>();
	}

    public int getNumberOfCars() {

        return fNumberOfCars;
    }

    public Rectangle2D getRect()
    {
        return fRect;
    }
}