/*
 * Copyright (C) veenion GmbH 1999-2012.
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
	private Vector2f fPostion;
	private float fX;

	public float getX()
	{
		return fPostion.getX();
	}

	public float getY()
	{
		return fPostion.getY();
	}

	public void setX(float x)
	{
		fPostion.setX(x);
	}

	public void setY(float y)
	{
		fPostion.setY(y);
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
		fPostion = new Vector2f(x,y);
	}
// -----------------------------------------------------
// methods
// -----------------------------------------------------

	public void updateX(float x)
	{
		fPostion.set(fPostion.getX()+x,fPostion.getY());
	}

	public void updateY(float y)
	{
		fPostion.set(fPostion.getX(),fPostion.getY()+y);
	}
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //Player
