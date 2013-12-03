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

    public PathFactory()
    {
        fExecutor = Executors.newFixedThreadPool(5);
    }

    public void createPath(Car car)
    {
        AStarAlgorithm alog = new AStarAlgorithm(car);
        fExecutor.execute(alog);
    }

    public void createPath(Car car, Lane destination)
    {
        AStarAlgorithm alog = new AStarAlgorithm(car);
        alog.setEnd(destination);
        fExecutor.execute(alog);
    }
}
