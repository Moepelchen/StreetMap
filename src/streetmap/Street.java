package streetmap;

import streetmap.Interfaces.IPrintable;
import streetmap.Interfaces.ISimulateable;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * This is the implementation of a Street in the simulation. A Street Consists of a Number of lanes
 */
public class Street implements IPrintable, ISimulateable
{


	private Vector<Lane> fLanes;
	private Tile fTile;
	private String fName;
	private SSGlobals fGlobals;

	public Street(SSGlobals glob, Tile tile, String name)
	{
		fName = name;
		fGlobals = glob;

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

		if (fGlobals.getConfig().isDrawLanes())
		{
			for (Lane lane : fLanes)
			{
				lane.print(g);

			}
		}
	}

	public void simulate()
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void addLane(Lane lane)
	{
		fLanes.add(lane);
	}

	public String toString()
	{
		return fName;
	}

    public String getName() {
        return fName;
    }
}