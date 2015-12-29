package streetmap.heatmap;

import streetmap.SSGlobals;
import streetmap.interfaces.ISimulateable;
import streetmap.map.tile.Tile;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Ulrich.Tewes on 24.12.2014.
 */
public abstract class HeatMap implements IHeatMap, Runnable


{
    private final SSGlobals fGlobals;
    private final int fTilesX;
    private final int fTilesY;
    private double[][] fHeatMapData;
    private List fObjects;
    private boolean isUpdating;
    private final ArrayBlockingQueue<Runnable> fWorkQueue = new ArrayBlockingQueue<>(11);
    private final Executor fExecutor = new ThreadPoolExecutor(1, 1, 13, TimeUnit.MILLISECONDS, fWorkQueue);
	private double fMin;
	private double fMax;


	public HeatMap(SSGlobals globals, int tilesX, int tilesY)
    {
        fGlobals = globals;
        fTilesX = tilesX;
        fTilesY = tilesY;
        fHeatMapData = new double[tilesX][tilesY];
        for (int i = 0; i < tilesX; i++)
        {
            for (int y = 0; y < tilesY; y++)
            {
                fHeatMapData[i][y] = 0;
            }
        }
    }

    @Override
    public void update()
    {

        if(!isUpdating)
        {
            fExecutor.execute(this);
        }
    }

    @Override
    public synchronized void run()
    {
        this.isUpdating = true;
        updateData(collectData());
        isUpdating = false;
    }

    private void updateData(Map<Tile,Double> map)
    {
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for (Tile printable : map.keySet())
        {
            Point2D arrayPos = printable.getArrayPosition();
            int x = (int) arrayPos.getX();
            int y = (int) arrayPos.getY();
            fHeatMapData[x][y] = Math.max(fHeatMapData[x][y] - fHeatMapData[x][y]/100d,0);
            fHeatMapData[x][y] = fHeatMapData[x][y]+(map.get(printable))/100d;
            if(fHeatMapData[x][y] > max)
                max = fHeatMapData[x][y];
            else if(fHeatMapData[x][y] <= min)
                min = fHeatMapData[x][y];
        }
        if(max >0)
        {
            for (int i = 0; i < fHeatMapData.length; i++)
            {
                for (int j = 0; j < fHeatMapData[i].length; j++)
                {
                    fHeatMapData[i][j] = Math.max((fHeatMapData[i][j]-min)/max,0);
                }
            }

        }
		fMax = max;
		fMin = min;
    }

    public synchronized double getReading(Point2D point)
    {
        return fHeatMapData[((int) point.getX())][((int) point.getY())];
    }

    protected abstract Map<Tile,Double> collectData();

    public synchronized void setData(List objects)
    {
        fObjects= objects;
    }

    public List getData()
    {
        return fObjects;
    }

	@Override
	public double getMax()
	{
		return fMax;
	}
}
