package streetmap.interfaces.config;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 12/22/11
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IChangeableConfig
{

	void setDrawTiles(boolean isDrawTiles);

	void setDrawAnchors(boolean isDrawAnchors);

	void setTileSize(Double tileSize);

	void setHeight(Double height);

	void setWidth(Double width);

	void setDrawSides(boolean isDrawSides);

	void setDrawLanes(boolean isDrawLanes);
}
