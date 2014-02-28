/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.rules;

import streetmap.map.street.Lane;
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
			add("N");
			add("W");
			add("S");
			add("E");
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

	public static boolean doesApply(Lane lane, String direction)
	{
		String compassPoint = lane.getStart().getSide().getCompassPoint();
		return compassPoint.equals(gCompassPoints.get(gCompassPoints.indexOf(direction)+1));
	}
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //RightBeforeLeftRule
