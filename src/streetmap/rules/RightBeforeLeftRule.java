/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.rules;

import streetmap.map.street.Lane;
import streetmap.map.tile.ICompassPoint;
import streetmap.utils.RingList;

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
public class RightBeforeLeftRule
{

	public static final RingList<String> gCompassPoints = new RingList<String>()
	{{
			add(ICompassPoint.COMPASS_POINT_N);
			add(ICompassPoint.COMPASS_POINT_W);
			add(ICompassPoint.COMPASS_POINT_S);
			add(ICompassPoint.COMPASS_POINT_E);
		}};
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

	public static boolean doesApply(Lane lane1, Lane lane2)
	{

		return false;
	}

	public static boolean isStraight(Lane lane)
	{
		return isStraight(lane.getFrom(),lane.getTo());
	}

	public static boolean crosses(Lane lane, Lane lane2)
	{
		if (lane.getFrom().equals(lane2.getFrom()))
		{
			return false;
		}
		else if (lane.getFrom().equals(lane2.getTo()) && lane.getTo().equals(lane2.getFrom()))
		{
			return false;
		}

		return true;
	}

	public static boolean isLeftTurn(String start, String end)
	{
		return !isStraight(start, end) && !gCompassPoints.get(gCompassPoints.indexOf(start) + 1).equals(end);

	}

	public static boolean isRightTurn(String start, String end)
	{
		return !isStraight(start,end) && !isLeftTurn(start,end);
	}

	public static boolean isStraight(String start, String end)
	{
		return gCompassPoints.get(gCompassPoints.indexOf(start) + 2).equals(end);

	}

	public static boolean comesFromTheLeft(String from1, String to1, String from2, String to2)
	{
		return to1.equals(to2);
	}

	public static boolean comesFromTheRight(String s, String to, String from, String from1)
	{
		return false;
	}
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //RightBeforeLeftRule
