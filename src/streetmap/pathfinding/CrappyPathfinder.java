package streetmap.pathfinding;

import streetmap.car.Car;
import streetmap.map.street.Lane;
import streetmap.map.street.Street;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 01.11.13
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class CrappyPathfinder extends AbstractPathFinder
{

	private final HashSet<Lane> fNoGo = new HashSet<>();

	public CrappyPathfinder(Car car)
	{
		super(car);
	}

	@Override
	public boolean containsStreet(Street street)
	{
		return true;
	}

	protected boolean createPath(Lane next)
	{
		fPath.add(next);
		if (next.equals(fEnd))
		{
			return true;
		}

		Collection<Lane> candidates = next.getEnd().getOutputLanes();
		List<Candidate> candidateCollection = new ArrayList<>();
		double startEndDistance = fStart.getEnd().getPosition().distance(fEnd.getStart().getPosition());
		for (Lane lane : candidates)
		{
			double distance = lane.getEnd().getPosition().distance(fEnd.getStart().getPosition());
			Candidate candidate = new Candidate(lane);
			candidate.candidate = lane;
			Lane cand = candidate.candidate;
			Lane randomLane = cand.getEnd().getRandomLane();
			double heatMapReading = 0;
			if (randomLane != null)
			{
				heatMapReading = getHeatMapReading(startEndDistance, lane, cand, randomLane);
			}

			candidate.fDistanceToGoal = distance + 1.0 * heatMapReading;
			candidateCollection.add(candidate);
		}

		Collections.sort(candidateCollection);
		for (Candidate candidate : candidateCollection)
		{
			if (!fNoGo.contains(candidate.candidate) && !fPath.contains(candidate.candidate) && createPath(candidate.candidate))
			{
				return true;
			}
		}
		int wrongIndex = fPath.indexOf(next);
		for (int i = wrongIndex; i < fPath.size(); i++)
		{
			fNoGo.add(fPath.get(i));
			fPath.remove(i);
		}

		return false;
	}
}
