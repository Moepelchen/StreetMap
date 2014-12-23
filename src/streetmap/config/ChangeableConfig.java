package streetmap.config;

import streetmap.SSGlobals;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class implements the interface IChangeableConfig, it is the first implementation
 */
public class ChangeableConfig extends Config implements IChangeableConfig
{
    /**
	 * Constructor  for setting up a ChangeableConfig
	 *
	 * @param glob current globals
	 * @throws FileNotFoundException thrown when the config file is not available
	 */
	public ChangeableConfig(SSGlobals glob) throws FileNotFoundException
	{
		super(glob);
	}

	@Override
	public void setDrawTiles(boolean isDrawTiles)
	{
		fConfig.setDrawtiles(isDrawTiles);
	}

	@Override
	public void setDrawAnchors(boolean isDrawAnchors)
	{
		fConfig.setDrawanchors(isDrawAnchors);
	}

	@Override
	public void setTileSize(Double tileSize)
	{
		fConfig.setTilesize(tileSize);
	}

	@Override
	public void setHeight(Double height)
	{
		fConfig.setHeight(height);
	}

	@Override
	public void setWidth(Double width)
	{
		fConfig.setWidth(width);
	}

	@Override
	public void setDrawSides(boolean isDrawSides)
	{
		fConfig.setDrawsides(isDrawSides);
	}

	@Override
	public void setDrawLanes(boolean isDrawLanes)
	{
		fConfig.setDrawlanes(isDrawLanes);
	}

    @Override
    public void setHeatMapModifier(Double modifier)
    {
        fHeatMapModifier = modifier;
    }

    @Override
    public void setMaximumNumOfCars(Integer numberOfCars)
    {
        fNumberOfCars = numberOfCars;
    }

    @Override
    public void setShowHeatMap(boolean toSet)
    {
        fShowHeatMap = toSet;
    }

	@Override
	public void setSimulateNightCycle(boolean toSet)
	{
		fSimulateNightCycle = toSet;
	}

    @Override
    public void setShowCars(boolean b)
    {
        fShowCars = b;
    }

    @Override
    public void setShowHappiness(boolean b) {
        fShowHappiness = b;
    }

	@Override
	public void setShowPathHeatMap(boolean fShowPathHeatMap)
	{
		this.fShowPathHeatMap = fShowPathHeatMap;
	}
}
