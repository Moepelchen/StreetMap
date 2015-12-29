package streetmap.pathfinding;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.util.concurrent.*;

/**
 * Created by ulrichtewes on 01.12.13.
 */
public class PathFactory extends Thread
{

	private final ExecutorService fExecutor;
	private final ArrayBlockingQueue<Runnable> fWorkQueue;

	public int getPathsRequested()
	{
		return fWorkQueue.size();
	}

	public PathFactory()
	{

		final Semaphore semaphore = new Semaphore(100);//or however you want max queued at any given moment
		fWorkQueue = new ArrayBlockingQueue<>(110);
		fExecutor = new ThreadPoolExecutor(1, 4, 1000, TimeUnit.MILLISECONDS, fWorkQueue)
		{
			public void execute(Runnable r)
			{
				try
				{
					semaphore.acquire();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				super.execute(r);
			}

			public void afterExecute(Runnable r, Throwable t)
			{
				semaphore.release();
				super.afterExecute(r, t);
			}
		};
	}

	public void createPath(Car car)
	{
		createPath(car, null);
	}

	public void createPath(Car car, Lane destination)
	{
		if (!car.hasRequestedPath())
		{
			car.setHasRequestedPath(true);

			IPathFindingAlgorithm algo = new AStarAlgorithm(car);
			algo.setEnd(destination);
			fExecutor.execute(algo);
		}
	}

	public void release()
	{
		fExecutor.shutdown();
	}
}
