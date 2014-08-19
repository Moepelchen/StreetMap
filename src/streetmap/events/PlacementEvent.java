/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.events;

import streetmap.map.street.IPlaceable;

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
public class PlacementEvent extends AbstractEvent
{

	private final IPlaceable fPlaceable;

	public IPlaceable getPlaceable()
	{
		return fPlaceable;
	}

	public PlacementEvent(IPlaceable placeable)
	{
		fPlaceable = placeable;
		fType = IEvent.EVENT_STREET_PLACEMENT;
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

} //StreetPlacementEvent
