/*
 * Copyright (C) Ulrich Tewes GmbH 2010-2014.
 */

package streetmap.car;

import streetmap.map.street.Lane;
import streetmap.SSGlobals;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * Factory for creating cars
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

    /**
     * create a car
     * @param globals Globals
     * @param lane lane to create the car on
     * @param pos position of the car
     * @return a new car on the desired lane
     */
    public static Car createCar(SSGlobals globals, Lane lane, Point2D pos)
    {
        ImageIcon carImage = getCarImage(globals);


        return new Car(lane, pos, carImage, globals.getConfig().getTileSize() / 4);
    }

    /**
     * Create a random image
     * @param globals globals
     * @return image icon for a car
     */
    private static ImageIcon getCarImage(SSGlobals globals)
    {
        Vector<ImageIcon> images = globals.getConfig().getCarImages();
        int rand = (int) (Math.random() * images.size());
        return images.get(rand);
    }
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------


} //CarFactory
