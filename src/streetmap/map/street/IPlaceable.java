/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.map.street;

import streetmap.interfaces.IPrintable;
import streetmap.interfaces.ISimulateable;

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
public interface IPlaceable extends IPrintable, ISimulateable
{
	int TYPE_STREET = 0;

	int getNumberOfCars();

	Vector<Lane> getLanes();

	boolean isSpecial();

	String getName();

	int getType();
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

} //IPlaceable
