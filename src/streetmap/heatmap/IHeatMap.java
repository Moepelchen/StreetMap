package streetmap.heatmap;

import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;
import streetmap.map.tile.Tile;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

/**
 * Created by Ulrich.Tewes on 24.12.2014.
 */
public interface IHeatMap
{
    public void update();

    public double getReading(Point2D point);

    void setData(List tiles);

	double getMax();
}
