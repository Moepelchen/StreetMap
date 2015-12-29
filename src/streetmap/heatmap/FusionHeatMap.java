/*
 * Copyright (C) veenion GmbH 1999-2015.
 */

package streetmap.heatmap;

import streetmap.SSGlobals;
import streetmap.map.tile.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ulrich.Tewes
 * @version 1.0
 * @since Release *.*.*
 */
public class FusionHeatMap extends HeatMap
{
	private IHeatMap fOcuHeatMap;
	private IHeatMap fPathHeatMap;

	public FusionHeatMap(SSGlobals globals, int tilesX, int tilesY)
	{
		super(globals, tilesX, tilesY);
	}

	@Override
	protected Map<Tile, Double> collectData()
	{
		java.util.Map<Tile, Double> map = new HashMap<>();
		for (Object fOccupiedTile : getData())
		{
			Tile tile = (Tile) fOccupiedTile;

			Double aDouble = fOcuHeatMap.getReading(tile.getArrayPosition()) + fPathHeatMap.getReading(tile.getArrayPosition());
			map.put(tile, aDouble);
		}
		return map;
	}

	public void setOcuHeatMap(IHeatMap fOcuHeatMap)
	{
		this.fOcuHeatMap = fOcuHeatMap;
	}

	public void setPathHeatMap(IHeatMap fPathHeatMap)
	{
		this.fPathHeatMap = fPathHeatMap;
	}
}
