package streetmap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import streetmap.interfaces.config.IChangeableConfig;

/**
 * Created by ulrichtewes on 03.12.13.
 */
public class Game
{
	private final Player fPLayer;
	SSGlobals fGlobals;
    public Game(SSGlobals globals)
    {
        fGlobals = globals;
	    fPLayer = new Player(0,0);
    }


    public void start() throws LWJGLException
    {

	    IChangeableConfig config = fGlobals.getConfig();
	    Display.setDisplayMode(new DisplayMode(config.getWidth().intValue(), config.getHeight().intValue()));
	    Display.create();

	    GL11.glEnable(GL11.GL_TEXTURE_2D);

	   // GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	    // enable alpha blending
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);

	    GL11.glViewport(0,0,config.getWidth().intValue(), config.getHeight().intValue());
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);

	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, config.getWidth().intValue(), config.getHeight().intValue(), 0, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	   // glEnable(GL11.GL_DEPTH_TEST);
	    while (!Display.isCloseRequested())
        {
	        GL11.glPopMatrix();
            // Clear the screen and depth buffer
	        GL11.glTranslatef(fPLayer.getX(),fPLayer.getY(),0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            fGlobals.getMap().simulate();
            fGlobals.getMap().paint(null);
	        GL11.glDisable( GL11.GL_BLEND);
	        Display.update();
	        Display.sync(30); // cap fps to 60fps

	        processInput();
	        GL11.glPushMatrix();

        }
        Display.destroy();
    }

	private void processInput()
	{
		while (Keyboard.next()) {

			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_A)
				{
					fPLayer.updateX((float) fGlobals.getMap().getTileWidth());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					fPLayer.updateY(-(float) fGlobals.getMap().getTileWidth());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					fPLayer.updateX(-(float) fGlobals.getMap().getTileWidth());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_W)
				{
					fPLayer.updateY((float) fGlobals.getMap().getTileWidth());
				}
			}
			else
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_A)
				{
					fPLayer.setX(0f);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					fPLayer.setY(0f);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					fPLayer.setX(0f);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_W)
				{
					fPLayer.setY(0f);
				}
			}
		}

	}

}
