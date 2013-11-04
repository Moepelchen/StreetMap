package streetmap.interfaces;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 01.11.13
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class CrappyPathfinder implements IPathFindingAlgorithm
{
    private final Lane fStart;
    private Lane fEnd;

    private Car fCar;
    private LinkedList<Lane> fPath;

    public CrappyPathfinder(Car car)
    {
        fPath = new LinkedList<Lane>();
        fCar = car;
        fStart = car.getLane();
        ArrayList<Lane> lanes = new ArrayList<Lane>(fStart.getStreet().getLanes());
        ArrayList<Lane> endLanes = new ArrayList<Lane>(fStart.getGlobals().getMap().getEndLanes());
        endLanes.removeAll(lanes);
        fEnd = null;
        if(endLanes.size() >0)
        {
            int index = (int) (Math.random() * endLanes.size());
            fEnd = endLanes.get(index);
            if(fEnd != null)
            {
                createPath(fStart);
            }
        }

    }

    private boolean createPath(Lane next)
    {
        fPath.add(next);
        Graphics2D g = (Graphics2D) next.getGlobals().getMap().getGraphics();
        if(next.equals(fEnd))
        {
            return true;
        }
        Collection<Lane> candidates = next.getEnd().getLanes();
        List<Candidate> candidateCollection = new ArrayList<Candidate>();
        for (Lane lane : candidates)
        {
            double distance = lane.getEnd().getPosition().distance(fEnd.getStart().getPosition());
            Candidate candidate = new Candidate();
            candidate.candidate = lane;
            candidate.distance = distance;
            candidateCollection.add(candidate);
        }
        Collections.sort(candidateCollection);
        for (Candidate candidate : candidateCollection)
        {
            if(!fPath.contains(candidate.candidate) &&createPath(candidate.candidate))
            {
                return true;
            }
        }
        int wrongIndex = fPath.indexOf(next);
        for (int i = wrongIndex; i< fPath.size();i++)
        {
            fPath.remove(i);
        }

        return false;
    }


    @Override
    public LinkedList getPath(Lane start, Lane end)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Lane getNextLane()
    {
        Lane nextLane = null;
        if (fEnd != null)
        {
            Lane current = fCar.getLane();
            int currentIndex = fPath.indexOf(current);

            if (currentIndex >= 0 && currentIndex + 1 < fPath.size() - 1)
            {
                nextLane = fPath.get(currentIndex + 1);
            }

        }
        return nextLane;
    }
}
