/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.interfaces;

import streetmap.car.Car;
import streetmap.map.street.Lane;

import java.awt.geom.Point2D;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.LinkedList;

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
public abstract class AbstractPathFinder implements IPathFindingAlgorithm
{
	protected LinkedList<Lane> fPath;
	protected Car fCar;
	protected Lane fStart;
	protected Lane fEnd;
	protected AbstractCollection fNoGo;

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
	public void AbstractPathFinder(Car car)
	{
		init(car);
	}

	protected void init(Car car)
	{
		fPath = new LinkedList<Lane>();
		fCar = car;
		fNoGo = new ArrayList();
		fStart = car.getLane();
		ArrayList<Lane> lanes = new ArrayList<Lane>(fStart.getStreet().getLanes());
		ArrayList<Lane> possibleEndLanes = new ArrayList<Lane>(fStart.getGlobals().getMap().getEndLanes());
		possibleEndLanes.removeAll(lanes);
		fEnd = null;
		if (possibleEndLanes.size() > 0)
		{
			while (possibleEndLanes.size() > 0)
			{
				int index = (int) (Math.random() * possibleEndLanes.size());
				fEnd = possibleEndLanes.get(index);
				if (fEnd != null)
				{
					if (!fEnd.isBlocked() && createPath(fStart))
					{
						break;
					}
					else
					{

						fNoGo.clear();
						fEnd.setIsBlocked(true);
						possibleEndLanes.remove(index);
					}
				}
			}
		}
	}

	protected abstract boolean createPath(Lane start);

	// -----------------------------------------------------
// methods
// -----------------------------------------------------
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
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

	protected double getHeatMapReading(double startEndDistance, Lane lane, Lane cand, Lane randomLane)
	{
		double heatMapReading;
		Point2D arrayPosition = randomLane.getEnd().getSide().getTile().getArrayPosition();
		heatMapReading = cand.getCars().size()*cand.getEnd().getSide().getGlobals().getMap().getHeatMapReading(arrayPosition);
		heatMapReading = (lane.getEnd().getPosition().distance(fEnd.getStart().getPosition()) / startEndDistance) *heatMapReading;
		return heatMapReading;
	}
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //AbstractPathFinder
