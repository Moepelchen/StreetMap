/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.interfaces;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
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
	private LinkedList<Lane> fOpenListLanes;
	private List<Lane> fClosedList;

	@Override
	protected boolean createPath(Lane start)
	{
		fClosedList = new ArrayList<Lane>();
		fOpenList = new SortedNodeList();
		fOpenListLanes = new LinkedList<Lane>();
		Graphics2D g = start.getEnd().getSide().getTile().getMap().getTheGraphics();
        double fromStartToEnd = start.getStart().getPosition().distance(fEnd.getEnd().getPosition());
        Candidate current = new Candidate(start);
        current.distance = fromStartToEnd;
        fOpenList.add(current);
        while (fOpenList.size() != 0)
		{
			current = fOpenList.getFirst();
			if(current.candidate.equals(fEnd))
			{
				getPath(current);
				return true;
			}

			fOpenList.remove(current);
			fOpenListLanes.remove(current.candidate);

			fClosedList.add(current.candidate);
			for (Lane lane : current.candidate.getEnd().getLanes())
			{

				boolean neighborIsBetter;
				Candidate neighbour = new Candidate(lane);
				if(fClosedList.contains(lane))
					continue;

				double distanceToEnd =lane.getStart().getPosition().distance(fEnd.getEnd().getPosition());

				double heatMapReading = getHeatMapReading(lane);
				distanceToEnd = distanceToEnd+ 5*heatMapReading;


				if(!fOpenListLanes.contains(neighbour.candidate))
				{
                    neighbour.distance = distanceToEnd;
					fOpenList.add(neighbour);
					fOpenListLanes.add(neighbour.candidate);
					neighborIsBetter = true;
				}
				else if(distanceToEnd < current.distance)
				{
					neighborIsBetter = true;
				}
				else
				{
					neighborIsBetter = false;
				}
				if(neighborIsBetter)
				{
					neighbour.setPrevious(current);
					neighbour.distance = distanceToEnd;
					neighbour.setDistanceToGoal(lane.getStart().getPosition().distance(fEnd.getEnd().getPosition()));
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
		heatMapReading = lane.getEnd().getSide().getGlobals().getMap().getHeatMapReading(arrayPosition);
		}
		return 1*heatMapReading * Math.min(1,lane.getCars().size());

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

    private class SortedNodeList {

               private ArrayList<Candidate> list = new ArrayList<Candidate>();

               public Candidate getFirst() {
                       return list.get(0);
               }

               public void clear() {
                       list.clear();
               }

               public void add(Candidate Candidate) {
                       list.add(Candidate);
                       Collections.sort(list);
               }

               public void remove(Candidate n) {
                       list.remove(n);
               }

               public int size() {
                       return list.size();
               }

               public boolean contains(Candidate n) {
                       return list.contains(n);
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
