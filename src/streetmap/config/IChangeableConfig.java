package streetmap.config;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IChangeableConfig extends IConfig
{

	void setDrawTiles(boolean isDrawTiles);

	void setDrawAnchors(boolean isDrawAnchors);

	void setTileSize(Double tileSize);

	void setHeight(Double height);

	void setWidth(Double width);

	void setDrawSides(boolean isDrawSides);

	void setDrawLanes(boolean isDrawLanes);

    void setHeatMapModifier(Double modifier);

    void setMaximumNumOfCars(Integer numberOfCars);

    void setShowHeatMap(boolean toSet);

	void setSimulateNightCycle(boolean toSet);

    void setShowCars(boolean b);

    void setShowHappiness(boolean b);
}
