package streetmap.interfaces;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 01.11.13
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class CrappyPathfinder extends AbstractPathFinder
{

	private HashSet<Integer> fNoGo = new HashSet<Integer>();

    public CrappyPathfinder(Car car)
    {
		init(car);
    }

    protected boolean createPath(Lane next)
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
	        Candidate candidate = new Candidate(lane);
	        candidate.candidate = lane;
	        Lane cand = candidate.candidate;
	        Lane randomLane = cand.getEnd().getRandomLane();
	        double heatMapReading = 0;
	        if(randomLane != null)
	        {
		        heatMapReading = getHeatMapReading(startEndDistance, lane, cand, randomLane);
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
}
