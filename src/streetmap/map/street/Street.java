package streetmap.map.street;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import streetmap.SSGlobals;
import streetmap.heatmap.Gradient;
import streetmap.map.tile.Tile;
import streetmap.xml.jaxb.streets.StreetTemplate;

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
	private final Tile fTile;
	/**
	 * Name of the StreetTemplate used
	 */
	private final String fName;
	/**
	 * Current globals
	 */
	private final SSGlobals fGlobals;
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

	private final boolean fSpecial;

	SSGlobals getGlobals()
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

	@Override
	public Point2D getPosition()
	{
		return getTile().getPosition();
	}

	@Override
	public float getLength()
	{
		return getTile().getWidth();
	}

	@Override
	public double getAngle()
	{
		return 0;
	}

	@Override
	public ReadableColor getColor()
	{
		double heatMapReading;
		int alpha = 255;
		java.awt.Color color;

		if(fGlobals.getConfig().isShowPathHeatMap() && fGlobals.getConfig().isShowHeatMap())
		{
			heatMapReading = this.getGlobals().getMap().getHeatMapReading(this.getTile().getArrayPosition());
			java.awt.Color color1 = Gradient.GRADIENT_RAINBOW[(int) Math.floor(heatMapReading * (Gradient.GRADIENT_HOT.length - 1))];
			heatMapReading = this.getGlobals().getMap().getHeatPathMapReading(this.getTile().getArrayPosition());
			java.awt.Color color2 = Gradient.GRADIENT_RAINBOW[(int) Math.floor(heatMapReading * (Gradient.GRADIENT_HOT.length - 1))];
			color = new java.awt.Color(color1.getRed()/2+color2.getRed()/2, color1.getGreen()/2+color2.getGreen()/2,color1.getBlue()/2+color2.getBlue()/2);
		}
		else if(fGlobals.getConfig().isShowPathHeatMap())
		{
			heatMapReading = this.getGlobals().getMap().getHeatPathMapReading(this.getTile().getArrayPosition());
			color = Gradient.GRADIENT_RAINBOW[(int) Math.floor(heatMapReading * (Gradient.GRADIENT_HOT.length - 1))];
		}
		else if(fGlobals.getConfig().isShowHeatMap())
		{
			heatMapReading = this.getGlobals().getMap().getHeatMapReading(this.getTile().getArrayPosition());
			color = Gradient.GRADIENT_RAINBOW[(int) Math.floor(heatMapReading * (Gradient.GRADIENT_HOT.length - 1))];
		}
		else
		{
			color = java.awt.Color.white;
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

    public Integer getImageId()
    {
	    Integer imageId = fGlobals.getStreetConfig().getTemplate(getName()).getImageId();
	    if(imageId < 0)
		    return getMenuImageId();
	    return imageId;
    }

	@Override
	public String getImagePath()
	{
		return fGlobals.getStreetConfig().getTemplate(getName()).getImagePath();
	}

	Integer getMenuImageId()
	{
		StreetTemplate template = getGlobals().getStreetConfig().getTemplate(getName());
		Integer imagePath = Integer.valueOf(template.getMenuImageId());
		if (imagePath == null)
		{
			imagePath = getImageId();
		}
		return imagePath;
	}

	public String getMenuImagePath()
	{
		StreetTemplate template = getGlobals().getStreetConfig().getTemplate(getName());
		String imagePath = template.getMenuImage();
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

	@Override
	public float getWidth()
	{
		return getLength();
	}
}