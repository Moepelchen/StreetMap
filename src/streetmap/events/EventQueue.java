/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.events;

import java.util.Vector;

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
public class EventQueue
{
// -----------------------------------------------------
// constants
// -----------------------------------------------------
// -----------------------------------------------------
// variables
// -----------------------------------------------------
	Vector<IEvent> fStreetEvents;

	public void addEvent(IEvent event)
	{
		switch (event.getType())
		{
			case IEvent.EVENT_STREET_PLACEMENT:
				fStreetEvents.add(event);
				break;
			default:
				break;
		}
	}

	public Vector<IEvent> getEvents()
	{
		return fStreetEvents;
	}
// -----------------------------------------------------
// inner classes
// -----------------------------------------------------
// -----------------------------------------------------
// constructors
// -----------------------------------------------------

	public EventQueue()
	{
		fStreetEvents = new Vector<IEvent>();
	}
// -----------------------------------------------------
// methods
// -----------------------------------------------------

	public void clearEventQueues()
	{
		fStreetEvents.clear();
	}
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //EventQueue
