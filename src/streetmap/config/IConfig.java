package streetmap.config;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: shifter
 * Date: 11/23/11
 * Time: 7:28 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IConfig
{
	boolean isDrawTiles();

	boolean isDrawAnchors();

	Double getTileSize();

	Double getHeight();

	Double getWidth();

	boolean isDrawSides();

	boolean isDrawLanes();

	Vector<ImageIcon> getCarImages();

    Double getHeatMapModifier();

    Integer getMaximumNumOfCars();

    boolean isShowHeatMap();

	boolean isSimulateNightCycle();

	float getMaximumCarSpeed();

	float getCarLength();
}
