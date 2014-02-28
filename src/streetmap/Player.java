/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap;

import org.lwjgl.util.vector.Vector2f;

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
public class Player
{
	private Vector2f fPosition;

    private double fZoom = 1;

    public float getX()
	{
		return fPosition.getX();
	}

	public float getY()
	{
		return fPosition.getY();
	}

	private void setX(float x)
	{
		fPosition.setX(x * getZoom());
	}

	private void setY(float y)
	{
		fPosition.setY(y * getZoom());
	}

    public void increaseZoom()
    {
        fZoom = fZoom * 1.1;
    }

    public void decreaseZoom()
    {
            fZoom = fZoom /1.1;
    }

	public void reset()
	{
		setY(0);
		setX(0);
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
	public Player(float x, float y)
	{
		fPosition = new Vector2f(x,y);
	}
// -----------------------------------------------------
// methods
// -----------------------------------------------------

	public void updateX(float x)
	{
		fPosition.set(fPosition.getX()+x/getZoom(), fPosition.getY());
	}

	public void updateY(float y)
	{
		fPosition.set(fPosition.getX(), fPosition.getY() + y / getZoom());
	}

    public float getZoom()
    {
        return (float) fZoom;
    }
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //Player
