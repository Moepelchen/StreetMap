/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.events;

import streetmap.map.street.Street;

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
public class StreetPlacementEvent extends AbstractEvent
{

	private final Street fStreet;

	public Street getStreet()
	{
		return fStreet;
	}

	public StreetPlacementEvent(Street street)
	{
		fStreet = street;
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
