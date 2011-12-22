package streetmap.config;

import streetmap.Interfaces.config.IChangeableConfig;
import streetmap.SSGlobals;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChangeableConfig extends Config implements IChangeableConfig
{
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
}
