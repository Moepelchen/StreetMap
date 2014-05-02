/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.gui.effects;

import streetmap.map.DataStorage2d;

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
public class PathDataPanel extends LogPanel
{
	@Override
	protected DataStorage2d getData()
	{
		return fGlobals.getGame().getPathData();
	}

	@Override
	public double getMax()
	{
		return 20;
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

} //PathDataPanel
