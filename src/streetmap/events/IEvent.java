/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.events;

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
public interface IEvent
{
// -----------------------------------------------------
// constants
// -----------------------------------------------------

	public static final int NO_TYPE = -1;

	public static int EVENT_STREET_PLACEMENT = 0;

	int getType();
} //IEvent
