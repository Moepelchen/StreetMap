package streetmap.map.street;

import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.SSGlobals;
import streetmap.map.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * This is the implementation of a Street in the simulation. A Street sonsists of a Number of lanes
 * and is located on a tile
 */
public class Street implements IPrintable, ISimulateable
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
	 * Constructor
	 *
	 * @param glob            current globals
	 * @param tile            the tile this street is located on
	 * @param name            name of the streettemplate which was used to construct this street
	 * @param isStartEndPoint indicates that this street can spawn new cars
	 */
	public Street(SSGlobals glob, Tile tile, String name, boolean isStartEndPoint)
	{
		fName = name;
		fGlobals = glob;
		fisStartEnd = isStartEndPoint;
		fTile = tile;
		fLanes = new Vector<Lane>();
	}

	public Vector<Lane> getLanes()
	{
		return fLanes;
	}

	public void setLanes(Vector<Lane> fLanes)
	{
		this.fLanes = fLanes;
	}

	public void print(Graphics2D g)
	{

		String imagePath = fGlobals.getStreetConfig().getTemplate(fName).getImagePath();
		if (imagePath != null)
		{
			Image image = new ImageIcon(imagePath).getImage();
			Double tileSize = fTile.getWidth();
			g.drawImage(image, (int) (fTile.getArrayPosition().getX() * tileSize), (int) (fTile.getArrayPosition().getY() * tileSize), tileSize.intValue(), tileSize.intValue(), null);
		}

		for (Lane lane : fLanes)
		{
			lane.print(g);

		}

	}

	public void simulate()
	{
		for (Lane fLane : fLanes)
		{
			fLane.simulate();
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

	public boolean isStartEnd()
	{
		return fisStartEnd;
	}
}