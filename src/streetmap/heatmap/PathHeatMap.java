package streetmap.heatmap;

import streetmap.SSGlobals;
import streetmap.car.Car;
import streetmap.map.street.Lane;
import streetmap.map.tile.Tile;
import streetmap.pathfinding.IPathFindingAlgorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Ulrich.Tewes on 24.12.2014.
 */
public class PathHeatMap extends HeatMap
{


    public PathHeatMap(SSGlobals globals, int tilesX, int tilesY)
    {
        super(globals, tilesX, tilesY);
    }


    protected Map<Tile, Double> collectData()
    {
        java.util.Map<Tile, Double> map = new HashMap<>();
        for (Object carObj : getData())
        {
            Car car = (Car) carObj;
            IPathFindingAlgorithm pathFinder = car.getPathFinder();
            if (pathFinder != null)
            {
                LinkedList<Lane> path = pathFinder.getPath();
                for (Lane lane : path)
                {
                    Tile tile = lane.getStreet().getTile();
                    if (map.containsKey(tile))
                    {
                        map.put(tile, map.get(tile) + 1);
                    } else
                    {
                        map.put(tile, 1d);
                    }
                }

            }
        }
        return map;
    }
}
