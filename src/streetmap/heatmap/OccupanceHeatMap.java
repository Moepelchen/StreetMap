package streetmap.heatmap;

import streetmap.SSGlobals;
import streetmap.map.tile.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ulrich.Tewes on 24.12.2014.
 */
public class OccupanceHeatMap extends HeatMap
{
    private double fMaxNumberOfCarsOnOneTile;

    public OccupanceHeatMap(SSGlobals globals, int tilesX, int tilesY)
    {
        super(globals, tilesX, tilesY);
        fMaxNumberOfCarsOnOneTile = 0;
    }

    @Override
    protected Map<Tile, Double> collectData()
    {
        java.util.Map<Tile, Double> map = new HashMap<>();
        for (Object fOccupiedTile : getData())
        {
            map.put( ((Tile)fOccupiedTile), (double) ((Tile)fOccupiedTile).getNumberOfCars()/fMaxNumberOfCarsOnOneTile);
        }
        return map;
    }

    public void setMaxNumberOfCarsOnOneTile(double fMaxNumberOfCarsOnOneTile)
    {
        this.fMaxNumberOfCarsOnOneTile = fMaxNumberOfCarsOnOneTile;
    }


}
