/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.pathfinding;

import streetmap.car.Car;
import streetmap.map.street.Lane;
import streetmap.map.street.Street;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Short description in a complete sentence.
 * <p/>
 * Purpose of this class / interface
 * Say how it should be and should'nt be used
 * <p/>
 * Known bugs: None
 * Historical remarks: None
 *
 * @author Ulrich Tewes
 * @version 1.0
 * @since Release
 */
public class AStarAlgorithm extends AbstractPathFinder
{

    private static HashMap<String,LinkedList<Lane>> fPathList = new HashMap<>();

	@Override
	protected boolean createPath(Lane start)
	{


        List<Lane> fClosedList = new ArrayList<>();
        SortedNodeList fOpenList = new SortedNodeList();
        double fromStartToEnd = start.getStart().getPosition().distance(fEnd.getEnd().getPosition());
        Candidate current = new Candidate(start);
        current.fDistanceToGoal = fromStartToEnd;
        fOpenList.add(current);
        while (fOpenList.size() != 0)
		{
			fOpenList.sort();
			current = fOpenList.getFirst();
			fOpenList.remove(current);
			//draw(g, current);
			if(current.candidate.equals(fEnd))
			{
				createPath(current);
				fPathList.put(fStart.hashCode()+"" + fEnd.hashCode(),fPath);
				return true;
			}


			fClosedList.add(current.candidate);
			for (Lane lane : current.candidate.getEnd().getOutputLanes())
			{
				Candidate neighbour = new Candidate(lane);
                if (!fClosedList.contains(lane))
                {

                    double distanceToStart = current.fDistanceToStart + lane.getTrajectory().getLength();

                    double distanceToEnd = lane.getEnd().getPosition().distance(fEnd.getEnd().getPosition());

                    double heatMapReading = getHeatMapReading(lane);
                    distanceToStart = distanceToStart + fGlobals.getConfig().getHeatMapModifier() * heatMapReading;


                    if (!fOpenList.contains(neighbour) || distanceToStart < fOpenList.getByLane(neighbour).fDistanceToStart)
                    {

                        neighbour.setPrevious(current);
                        neighbour.setDistanceToStart(distanceToStart);
                        neighbour.fDistanceToGoal = distanceToEnd;

                        if (fOpenList.contains(neighbour))
                        {
                            fOpenList.getByLane(neighbour).fDistanceToStart = distanceToStart;
                        } else
                        {
                            fOpenList.add(neighbour);
                        }
                    }
                }
			}
		}
		return false;
	}

	private double getHeatMapReading(Lane lane)
	{
		double heatMapReading= 0;
		Lane random = lane.getEnd().getRandomLane();
		if(random != null)
		{
		Point2D arrayPosition = random.getEnd().getSide().getTile().getArrayPosition();
		heatMapReading = fGlobals.getMap().getHeatMapReading(arrayPosition);
		}
		return heatMapReading * Math.min(1,lane.getCars().size());

	}

	private void createPath(Candidate current)
	{
		fPath.addFirst(current.candidate);
		if(current.previous() != null)
		{
			createPath(current.previous());
		}
	}

	public AStarAlgorithm(Car car)
	{
		init(car);
	}

	@Override
	public boolean containsStreet(Street street)
	{
        boolean toReturn = false;
        for (Lane lane : street.getLanes())
		{
            if(fPath.contains(lane))
            {
               toReturn = true;
            }
		}
		return toReturn;
	}

	private class SortedNodeList
	{

		private ArrayList<Candidate> list = new ArrayList<>();
		private HashMap<Integer, Candidate> hash = new HashMap<>();

		public Candidate getFirst()
		{
			return list.get(0);
		}

		public void clear()
		{
			list.clear();
		}

		public void add(Candidate candidate)
		{
			list.add(candidate);
			hash.put(candidate.hashCode(), candidate);

		}

		public void remove(Candidate n)
		{
			list.remove(n);
			hash.remove(n.hashCode());
		}

		public int size()
		{
			return list.size();
		}

		public boolean contains(Candidate n)
		{
			return hash.get(n.hashCode()) != null;
		}

		public Candidate getByLane(Candidate n)
		{
			return hash.get(n.hashCode());
		}

		public void sort()
		{
			Collections.sort(list);
		}
	}

} //AStarAlgorithm
