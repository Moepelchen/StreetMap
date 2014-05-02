package streetmap.pathfinding;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ulrichtewes on 01.12.13.
 */
public class PathFactory extends Thread
{

    private final ExecutorService fExecutor;

	public int getPathsRequested()
	{
		return fPathsRequested;
	}

	public void reset()
	{
		fPathsRequested = 0;
	}

	private int fPathsRequested;
    public PathFactory()
    {
        fExecutor = Executors.newFixedThreadPool(10);
    }

    public void createPath(Car car)
    {
	    fPathsRequested++;
        AStarAlgorithm alog = new AStarAlgorithm(car);
        fExecutor.execute(alog);
    }

    public void createPath(Car car, Lane destination)
    {
	    fPathsRequested++;
        AStarAlgorithm alog = new AStarAlgorithm(car);
        alog.setEnd(destination);
        fExecutor.execute(alog);
    }

    public void release()
    {
        fExecutor.shutdown();
    }
}
