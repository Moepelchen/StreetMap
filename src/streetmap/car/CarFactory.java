/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.car;

import streetmap.SSGlobals;
import streetmap.map.street.Lane;

import java.awt.geom.Point2D;


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
public class CarFactory
{

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

	public static Car createCar(SSGlobals globals, Lane lane, Point2D pos)
	{
		return new Car(lane, pos, "./images/cars/car.png", globals.getConfig().getCarLength());
	}

} //CarFactory
