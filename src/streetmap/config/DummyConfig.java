package streetmap.config;

import streetmap.Interfaces.config.IConfig;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 11/22/11
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This is a dummy for first tests, it could also be used to realize a build without
 * being able to change the config
 */
public class DummyConfig implements IConfig
{


	public boolean isDrawTiles()
	{
		return true;
	}

	public boolean isDrawAnchors()
	{
		return true;
	}

	public Double getTileSize()
	{
		return 50.0;
	}

	public Double getHeight()
	{
		return 300.0;
	}

	public Double getWidth()
	{
		return 800.0;
	}

	public boolean isDrawSides()
	{
		return true;
	}

	@Override
	public boolean isDrawLanes()
	{
		return true;
	}

	public String getStreetPath()
	{
		return "/home/shifter/workspace/StreetMap/config/Streets/";
	}
}
