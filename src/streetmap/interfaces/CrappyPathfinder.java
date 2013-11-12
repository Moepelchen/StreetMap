package streetmap.interfaces;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
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
	private HashSet<Integer> fNoGo = new HashSet<Integer>();

    public CrappyPathfinder(Car car)
    {
        fPath = new LinkedList<Lane>();
        fCar = car;
        fStart = car.getLane();
        ArrayList<Lane> lanes = new ArrayList<Lane>(fStart.getStreet().getLanes());
        ArrayList<Lane> possibleEndLanes = new ArrayList<Lane>(fStart.getGlobals().getMap().getEndLanes());
        possibleEndLanes.removeAll(lanes);
        fEnd = null;
        if(possibleEndLanes.size() >0)
        {
	        while (possibleEndLanes.size() > 0)
	        {
		        int index = (int) (Math.random() * possibleEndLanes.size());
		        fEnd = possibleEndLanes.get(index);
		        if (fEnd != null)
		        {
			        if(createPath(fStart))
			        {
				        break;
			        }else
			        {
				        fNoGo.clear();
				        possibleEndLanes.remove(index);
			        }
		        }
	        }


        }

    }

    private boolean createPath(Lane next)
    {
        fPath.add(next);
        if(next.equals(fEnd))
        {
            return true;
        }
        Collection<Lane> candidates = next.getEnd().getLanes();
        List<Candidate> candidateCollection = new ArrayList<Candidate>();
	    double startEndDistance = fStart.getEnd().getPosition().distance(fEnd.getStart().getPosition());
	    for (Lane lane : candidates)
        {
	        double distance = lane.getEnd().getPosition().distance(fEnd.getStart().getPosition());
	        Candidate candidate = new Candidate();
	        candidate.candidate = lane;
	        Lane cand = candidate.candidate;
	        Lane randomLane = cand.getEnd().getRandomLane();
	        double heatMapReading = 0;
	        if(randomLane != null)
	        {
		        Point2D arrayPosition = randomLane.getEnd().getSide().getTile().getArrayPosition();
		        heatMapReading = cand.getCars().size()*cand.getEnd().getSide().getGlobals().getMap().getHeatMapReading(arrayPosition);
		        heatMapReading = (lane.getEnd().getPosition().distance(fEnd.getStart().getPosition()) / startEndDistance) *heatMapReading;
	        }

	        candidate.distance = distance +heatMapReading ;
            candidateCollection.add(candidate);
        }
        Collections.sort(candidateCollection);
        for (Candidate candidate : candidateCollection)
        {
            if(!fNoGo.contains(candidate.hashCode())&&!fPath.contains(candidate.candidate) &&createPath(candidate.candidate))
            {
                return true;
            }
        }
        int wrongIndex = fPath.indexOf(next);
        for (int i = wrongIndex; i< fPath.size();i++)
        {
	        fNoGo.add(fPath.get(i).hashCode());
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
