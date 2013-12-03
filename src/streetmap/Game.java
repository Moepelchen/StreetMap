/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

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
public class Game
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
	public Game() throws LWJGLException
	{

	}


	public void start() throws LWJGLException
	{
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();

		while (!Display.isCloseRequested())
		{


			init();
			simulate();
			Display.update();
		}

		Display.destroy();
	}

	private void simulate()
	{
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// set the color of the quad (R,G,B,A)
		GL11.glColor3f(0.5f,0.5f,1.0f);

		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(100,100);
		GL11.glVertex2f(100+200,100);
		GL11.glVertex2f(100+200,100+200);
		GL11.glVertex2f(100,100+200);
		GL11.glEnd();
	}

	private void init()
	{
		// init OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
// -----------------------------------------------------
// methods
// -----------------------------------------------------

	public static void main(String[] args) throws LWJGLException
	{
		new Game().start();
	}
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //Game
