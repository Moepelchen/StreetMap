package streetmap;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.tools.TimeProvider;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import streetmap.gui.GLStreetPanel;
import streetmap.gui.MainPanel;
import streetmap.interfaces.config.IChangeableConfig;

import java.io.File;
import java.io.FileNotFoundException;

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
    private MouseHandler fMouseHandler;
    private KeyHandler fKeyboardHandler;

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

    public static void main(String[] args) throws Exception
    {
        SSGlobals globals = null;
        try
        {
            globals = new SSGlobals();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        MainPanel main = new MainPanel(globals);
        Game game = new Game(globals);
        game.start();

    }

    public void start() throws Exception
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
        fMouseHandler = new MouseHandler(fGlobals);
        fKeyboardHandler = new KeyHandler(fGlobals);

	    LwjglInputSystem inputSystem = new LwjglInputSystem();
	    inputSystem.startup();

	    Nifty nifty = new Nifty(new LwjglRenderDevice(),new NullSoundDevice(),inputSystem,new TimeProvider());
	    File menuDefinitions = new File("./resources/gui/nifty.xml");

		nifty.fromXml(menuDefinitions.getPath(),"debug");
	   // glEnable(GL11.GL_DEPTH_TEST);
        while (!Display.isCloseRequested())
        {
	        nifty.update();
	        nifty.render(true);

            //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            // Clear the screen and depth buffer
            GL11.glPushMatrix();
            GL11.glTranslatef(fPlayer.getX(), fPlayer.getY(), 0);

            fGlobals.getMap().simulate();
            fGlobals.getMap().paint(null);
            GL11.glPopMatrix();

            drawInterface();

            Display.update();
            Display.sync(60); // cap fps to 60fps

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
            fStreetPanel.handleClick();
        }

		fKeyboardHandler.handleInput();

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
