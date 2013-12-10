package streetmap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import streetmap.gui.GLStreetPanel;
import streetmap.interfaces.config.IChangeableConfig;

/**
 * Created by ulrichtewes on 03.12.13.
 */
public class Game
{
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 1000;
    private final Player fPlayer;
	SSGlobals fGlobals;
    private GLStreetPanel fStreetPanel;

	public Player getPlayer()
	{
		return fPlayer;
	}

	public Game(SSGlobals globals)
    {
        fGlobals = globals;
	    fPlayer = new Player(0,0);
        fGlobals.setGame(this);
    }


    public void start() throws LWJGLException
    {

	    IChangeableConfig config = fGlobals.getConfig();
	    Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
	    Display.create();

	    GL11.glEnable(GL11.GL_TEXTURE_2D);

	   // GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	    // enable alpha blending
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);

	    GL11.glViewport(0,0, WIDTH, HEIGHT);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);

	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
        Keyboard.enableRepeatEvents(true);

        fStreetPanel = new GLStreetPanel(fGlobals);
	   // glEnable(GL11.GL_DEPTH_TEST);
        while (!Display.isCloseRequested())
        {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            // Clear the screen and depth buffer
            GL11.glPushMatrix();
            GL11.glTranslatef(fPlayer.getX(), fPlayer.getY(), 0);

            fGlobals.getMap().simulate();
            fGlobals.getMap().paint(null);
            GL11.glPopMatrix();

            drawInterface();

            Display.update();
            Display.sync(30); // cap fps to 60fps

            processInput();

        }
        Display.destroy();
    }

    private void drawInterface()
    {
      fStreetPanel.draw();
    }

    private void processInput()
	{
        if(Mouse.isButtonDown(0))
        {
            System.out.println("mouse left clicked");
            fStreetPanel.handleClick();
        }

		while (Keyboard.next()) {

			if (Keyboard.getEventKeyState() )
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_A)
				{
					fPlayer.updateX((float) fGlobals.getMap().getTileWidth());
                    System.out.println("A KEX PRESSED");
                }
				if (Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					fPlayer.updateY(-(float) fGlobals.getMap().getTileWidth());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					fPlayer.updateX(-(float) fGlobals.getMap().getTileWidth());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_W)
				{
					fPlayer.updateY((float) fGlobals.getMap().getTileWidth());
				}
			}
			else
			{
				/*if (Keyboard.getEventKey() == Keyboard.KEY_A)
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
				}*/
			}
		}

	}

    public int getWidth()
    {
        return WIDTH;
    }

    public int getHeight()
    {
        return HEIGHT;
    }

    public Vector2f getTranslatedCoords(int x, int y)
    {
        Vector2f toReturn = new Vector2f(x,y);
        toReturn.translate(-fPlayer.getX(),-fPlayer.getY());
        return toReturn;
    }
}
