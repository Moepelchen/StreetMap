/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.car;

import streetmap.Lane;
import streetmap.SSGlobals;

import javax.swing.*;
import java.awt.geom.Point2D;
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


	public static Car createCar(SSGlobals globals,Lane lane, Point2D pos)
	{
		ImageIcon carImage = getCarImage(globals);
		return new Car(lane, pos, carImage,globals.getConfig().getTileSize() / 4);
	}

	public static ImageIcon getCarImage(SSGlobals globals)
	{
		Vector<ImageIcon> images = globals.getConfig().getCarImages();
		int rand = (int) (Math.random()*images.size());
		return images.get(rand);
	}
} //CarFactory
