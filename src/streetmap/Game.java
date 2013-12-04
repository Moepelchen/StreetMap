package streetmap;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import streetmap.interfaces.config.IChangeableConfig;
import streetmap.map.Map;

/**
 * Created by ulrichtewes on 03.12.13.
 */
public class Game
{
    private final Map fMap;
    SSGlobals fGlobals;
    public Game(SSGlobals globals)
    {
        fGlobals = globals;
        fMap = globals.getMap();
    }


    public void start() throws LWJGLException
    {

	    IChangeableConfig config = fGlobals.getConfig();
	    Display.setDisplayMode(new DisplayMode(config.getWidth().intValue(), config.getHeight().intValue()));
	    Display.create();

	    GL11.glEnable(GL11.GL_TEXTURE_2D);

	    GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

	    // enable alpha blending
	    //GL11.glEnable(GL11.GL_BLEND);
	    //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

	    GL11.glViewport(0, 0,config.getWidth().intValue(), config.getHeight().intValue());
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);

	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, config.getWidth().intValue(), config.getHeight().intValue(), 0, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);

	    while (!Display.isCloseRequested())
        {
            // Clear the screen and depth buffer
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            fGlobals.getMap().simulate();
            fGlobals.getMap().paint(null);
            Display.update();
            Display.sync(160); // cap fps to 60fps
        }
        Display.destroy();
    }

}
