/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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
    float scaleDelta = 0.1f;
    private Vector3f fZoom = new Vector3f(1, 1, 1);
    private float z;

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
		fPosition.setX(x);
	}

	private void setY(float y)
	{
		fPosition.setY(y);
	}

    public void increaseZoom()
    {
        fZoom = new Vector3f(fZoom.getX() *1.1f,fZoom.getY()*1.1f,fZoom.getZ() *1.1f);
        if (fZoom.getX() > 100)
        {
            fZoom = new Vector3f(99, 99, 99);
        }
    }

    public void decreaseZoom()
    {
        fZoom = new Vector3f(fZoom.getX() /1.1f,fZoom.getY() /1.1f,fZoom.getZ()  /1.1f);
        if (fZoom.getX() <= 0.4)
        {
            fZoom = new Vector3f(0.4f, 0.4f, 0.4f);
        }
    }

    public void reset()
    {
        setY(0);
        setX(0);
        setZ(-1);

        fZoom = new Vector3f(1, 1, 1);
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
		fPosition.set(fPosition.getX()+x, fPosition.getY());
	}

	public void updateY(float y)
	{
		fPosition.set(fPosition.getX(), fPosition.getY() + y);
	}

    public Vector3f getZoom()
    {
        return fZoom;
    }

    public Vector3f getPos()
    {
        return new Vector3f(getX(), getY(), getZ());
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public float getZ()
    {
        return z;
    }
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //Player
