/*
 * Copyright (C) Ulrich Tewes GmbH 2010-2014.
 */

package streetmap.pathfinding;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.awt.*;
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
	private SortedNodeList fOpenList;
	private List<Lane> fClosedList;

	private static HashMap<String,LinkedList<Lane>> fPathList = new HashMap<String, LinkedList<Lane>>();

	@Override
	protected boolean createPath(Lane start)
	{
		/*if(fPathList.get(fStart.hashCode() + fEnd.hashCode()) != null)
		{
			fPath = new LinkedList<Lane>(fPathList.get(fStart.hashCode() + fEnd.hashCode()));
			return true;
		}*/
		fClosedList = new ArrayList<Lane>();
		fOpenList = new SortedNodeList();
		Graphics2D g = start.getEnd().getSide().getTile().getMap().getTheGraphics();
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
				getPath(current);
				fPathList.put(fStart.hashCode()+"" + fEnd.hashCode(),fPath);
				return true;
			}


			fClosedList.add(current.candidate);
			for (Lane lane : current.candidate.getEnd().getOutputLanes())
			{
				Candidate neighbour = new Candidate(lane);
				if(fClosedList.contains(lane))
					continue;

			double distanceToStart = current.fDistanceToStart + lane.getTrajectory().getLength();

				double distanceToEnd = lane.getEnd().getPosition().distance(fEnd.getEnd().getPosition());

				double heatMapReading = getHeatMapReading(lane);
				distanceToStart = distanceToStart + fGlobals.getConfig().getHeatMapModifier()* heatMapReading;


				if(fOpenList.contains(neighbour) && distanceToStart >= fOpenList.getByLane(neighbour).fDistanceToStart)
					continue;

				neighbour.setPrevious(current);
				neighbour.setDistanceToStart(distanceToStart);
				neighbour.fDistanceToGoal = distanceToEnd;

				if (fOpenList.contains(neighbour))
				{
					fOpenList.getByLane(neighbour).fDistanceToStart = distanceToStart;
				}
				else
				{
					fOpenList.add(neighbour);

				}
			}
		}
		return false;
	}

	private void draw(Graphics2D g, Candidate current)
	{

		for (Lane lane : fClosedList)
		{
			g.setColor(Color.BLUE);
			g.drawOval((int) lane.getEnd().getPosition().getX(), (int) lane.getEnd().getPosition().getY(), 5, 5);
		}


		for (Candidate openListLane : fOpenList.getList())
		{
			g.setColor(Color.GREEN);
			g.drawOval((int) openListLane.candidate.getEnd().getPosition().getX(), (int) openListLane.candidate.getEnd().getPosition().getY(), 5, 5);
		}

		g.setColor(Color.MAGENTA);
		g.drawOval((int) current.candidate.getEnd().getPosition().getX(), (int) current.candidate.getEnd().getPosition().getY(), 5, 5);

		g.setColor(Color.MAGENTA);
		g.drawOval((int) fEnd.getEnd().getPosition().getX(), (int) fEnd.getEnd().getPosition().getY(), 5, 5);
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

	private void getPath(Candidate current)
	{
		fPath.addFirst(current.candidate);
		if(current.previous() != null)
		{
			getPath(current.previous());
		}
	}

	public AStarAlgorithm(Car car)
	{
		init(car);
	}

    @Override
    public void update()
    {
        if(fEnd != null)
        {
            fPath.clear();
            createPath(fCar.getLane());
        }
    }

    private class SortedNodeList
	{

		private ArrayList<Candidate> list = new ArrayList<Candidate>();
		private HashMap<Integer, Candidate> hash = new HashMap<Integer, Candidate>();

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

		public List<Candidate> getList()
		{
			return list;
		}

		public void sort()
		{
			Collections.sort(list);
		}
	}

// -----------------------------------------------------
// constants
// -----------------------------------------------------
// -----------------------------------------------------
// variables
// -----------------------------------------------------
// -----------------------------------------------------
// inner classes
// -----------------------------------------------------
// -----------------------------------------------------
// constructors
// -----------------------------------------------------
// -----------------------------------------------------
// methods
// -----------------------------------------------------
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //AStarAlgorithm
