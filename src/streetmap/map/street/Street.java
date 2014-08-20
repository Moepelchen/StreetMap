package streetmap.map.street;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import streetmap.SSGlobals;
import streetmap.heatmap.Gradient;
import streetmap.map.tile.Tile;
import streetmap.utils.DrawHelper;
import streetmap.xml.jaxb.StreetTemplate;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * This is the implementation of a Street in the simulation. A Street sonsists of a Number of lanes
 * and is located on a tile
 */
public class Street implements IPlaceable
{

	/**
	 * lanes this street consists of
	 */
	private Vector<Lane> fLanes;
	/**
	 * the tile on which this street is located on
	 */
	private Tile fTile;
	/**
	 * Name of the StreetTemplate used
	 */
	private String fName;
	/**
	 * Current globals
	 */
	private SSGlobals fGlobals;
	/**
	 * indicates that this street can spawn new cars
	 */
	private boolean fisStartEnd;

    /**
     * Number of cars on this street
     */
    private int fNumberOfCars;

    /**
     * Image of this Street
     */

    private Image fImage;
	private boolean fSpecial;

	public SSGlobals getGlobals()
	{
		return fGlobals;
	}

	public Tile getTile()
	{
		return fTile;
	}

	public boolean isSpecial()
	{
		return fSpecial;
	}

	/**
	 * Constructor
	 *
	 * @param glob            current globals
	 * @param tile            the tile this street is located on
	 * @param name            name of the streettemplate which was used to construct this street
	 * @param isSpecial indicates that this street can spawn new cars
	 */
	public Street(SSGlobals glob, Tile tile, String name, boolean isSpecial)
	{
		fName = name;
		fSpecial = isSpecial;
		fGlobals = glob;
		fTile = tile;
		fLanes = new Vector<>();
	}

	public Vector<Lane> getLanes()
	{
		return fLanes;
	}

	public void setLanes(Vector<Lane> fLanes)
	{
		this.fLanes = fLanes;
	}

	public void print()
	{
		DrawHelper.drawStreet(this);
      /*  for (Lane lane : this.getLanes())
        {
            lane.print();
        }*/

	}

	@Override
	public Point2D getPosition()
	{
		return getTile().getPosition();
	}

	@Override
	public float getLength()
	{
		return (float) getTile().getWidth();
	}

	@Override
	public double getAngle()
	{
		return 0;
	}

	@Override
	public ReadableColor getColor()
	{
		double heatMapReading = this.getGlobals().getMap().getHeatMapReading(this.getTile().getArrayPosition());
		java.awt.Color color = Gradient.GRADIENT_RAINBOW[(int) Math.floor(heatMapReading * (Gradient.GRADIENT_HOT.length - 1))];
		int alpha = 255;
		if(!fGlobals.getConfig().isShowHeatMap())
		{
			color = java.awt.Color.WHITE;
		}
		return new Color(color.getRed(),color.getGreen(),color.getBlue(), alpha);
	}

	public void simulate()
	{
        fNumberOfCars = 0;
		for (Lane fLane : fLanes)
		{
			fLane.simulate();
            fNumberOfCars = fNumberOfCars + fLane.getCars().size();
		}
	}

	public void addLane(Lane lane)
	{
		fLanes.add(lane);
	}

	public String toString()
	{
		return fName;
	}

	public String getName()
	{
		return fName;
	}

	@Override
	public int getType()
	{
		return IPlaceable.TYPE_STREET;
	}

	public boolean isStartEnd()
	{
		return fisStartEnd;
	}

    public int getNumberOfCars()
    {
        return fNumberOfCars;
    }

    public Integer getImagePath()
    {

	    String imagePath = fGlobals.getStreetConfig().getTemplate(getName()).getImagePath();
	    if(imagePath == null)
		    return getMenuImagePath();
	    return Integer.parseInt(imagePath);
    }

    public Integer getMenuImagePath()
    {
        StreetTemplate template = getGlobals().getStreetConfig().getTemplate(getName());
        Integer imagePath = Integer.valueOf(template.getMenuImage());
        if (imagePath == null)
        {
            imagePath = getImagePath();
        }
        return imagePath;
    }

	public float getStepWidth()
	{
		return 0.015625f;
	}
}