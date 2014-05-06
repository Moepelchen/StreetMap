package streetmap;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.renderer.lwjgl.time.LWJGLTimeProvider;
import de.lessvoid.nifty.screen.ScreenController;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import streetmap.gui.GLStreetPanel;
import streetmap.gui.IScreenNames;
import streetmap.gui.controller.DebugScreenController;
import streetmap.gui.controller.GameScreenController;
import streetmap.gui.controller.LoadScreenController;
import streetmap.gui.controller.MenuScreenController;
import streetmap.gui.controller.SaveScreenController;
import streetmap.gui.inputmapping.MenuInputMapping;
import streetmap.map.DataStorage2d;
import streetmap.map.Map;
import streetmap.utils.TextureCache;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Copyright Ulrich Tewes
 * Created by ulrichtewes on 03.12.13.
 */
public class Game
{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 1024;
    private final Player fPlayer;
	SSGlobals fGlobals;
    private GLStreetPanel fStreetPanel;
    private KeyHandler fKeyboardHandler;
	private boolean fPaused;
	private Vector2f fScalePoint;

    /** time at last frame */
    long lastFrame;

    /** frames per second */
    int fps;
    /** last fps time */
    long lastFPS;

    private DataStorage2d fFPSData = new DataStorage2d(300);
	private DataStorage2d fPathData = new DataStorage2d(300);

    public Nifty getNifty()
	{
		return fNifty;
	}

	private Nifty fNifty;

	public Player getPlayer()
	{
		return fPlayer;
	}

	public boolean isPaused()
	{
		return fPaused;
	}

	public Vector2f getScalePoint()
	{

		return fScalePoint;
	}

	public DataStorage2d getPathData()
	{
		return fPathData;
	}

	public Game(SSGlobals globals)
    {
        fGlobals = globals;
	    fPlayer = new Player(0,0);
        fGlobals.setGame(this);
        new Map(globals);
	    fScalePoint = new Vector2f(getWidth()/2, getHeight()/2);
    }

    public static void main(String[] args) throws Exception
    {
        SSGlobals globals = null;
        System.out.println(new File("./").getAbsoluteFile());
        System.out.println(System.getProperty("java.library.path"));

        try
        {
            globals = new SSGlobals();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        Game game = new Game(globals);
        game.start();

    }

	public void start() throws Exception
	{

         /*PixelFormat pixelFormat = new PixelFormat();
       ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);*/
		Display.setDisplayMode(new DisplayMode(getWidth(), getHeight()));
		Display.create();

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);

		GL11.glViewport(0, 0, getWidth(), getHeight());
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, getWidth(), getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		Keyboard.enableRepeatEvents(true);

		fStreetPanel = new GLStreetPanel(fGlobals);
		fKeyboardHandler = new KeyHandler(fGlobals);

        initNifty();
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

		// glEnable(GL11.GL_DEPTH_TEST);
		while (!Display.isCloseRequested())
		{
            int delta = getDelta();

            updateFPS();
            fGlobals.getTimeHandler().tickTime();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			// Clear the screen and depth buffer
			GL11.glPushMatrix();
			Vector2f scalePoint = getScalePoint();
            GL11.glTranslated(scalePoint.getX(), scalePoint.getY(),0);
            GL11.glScalef(fPlayer.getZoom(),fPlayer.getZoom(),0);
            GL11.glTranslated(-getScalePoint().getX(), -scalePoint.getY(),0);

            GL11.glTranslatef(fPlayer.getX(), fPlayer.getY(), 0);

			if (!this.isPaused())
			{
				fGlobals.getMap().simulate();
				int pathsRequested = fGlobals.getMap().getPathFactory().getPathsRequested();

				fPathData.add((double) pathsRequested);
			}
			fGlobals.getMap().paint();
			GL11.glPopMatrix();

			drawInterface();
			fNifty.update();
			fNifty.render(false);

			Display.update();
			Display.sync(60); // cap fps to 60fps

            String screenId = fNifty.getCurrentScreen().getScreenId();
            if(fNifty.getCurrentScreen() != null && screenId != null &&screenId.equals(IScreenNames.SCREEN_GAME))
			{
				processInput();
			}

		}
        TextureCache.releaseTextures();
        Display.destroy();
        System.exit(0);

	}
    /**
     * Calculate how many milliseconds have passed
     * since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fFPSData.add((double) fps);
            fps = 0;
            lastFPS += 1000;

        }
        fps++;
    }

    private void initNifty() throws Exception
    {
        LwjglInputSystem inputSystem = new LwjglInputSystem();
        inputSystem.startup();

        fNifty = new Nifty(new LwjglRenderDevice(), new NullSoundDevice(), inputSystem, new LWJGLTimeProvider());
        File menuDefinitions = new File("./resources/gui/nifty.xml");

        GameScreenController gameScreenController = new GameScreenController(fGlobals);
        MenuScreenController menuScreenController = new MenuScreenController(fGlobals);
        SaveScreenController saveScreenController = new SaveScreenController(fGlobals);
        ScreenController loadScreenController = new LoadScreenController(fGlobals);
        DebugScreenController debugScreenController = new DebugScreenController(fGlobals);

        fNifty.registerScreenController(debugScreenController);
        fNifty.registerScreenController(gameScreenController);
        fNifty.registerScreenController(menuScreenController);
        fNifty.registerScreenController(saveScreenController);
        fNifty.registerScreenController(loadScreenController);
        fNifty.registerEffect("carpanel", "streetmap.gui.effects.CarNumberPanel");
        fNifty.registerEffect("framepanel", "streetmap.gui.effects.FPSPanel");
	    fNifty.registerEffect("flowpanel", "streetmap.gui.effects.FlowDataPanel");
	    fNifty.registerEffect("pathpanel", "streetmap.gui.effects.PathDataPanel");

        fNifty.fromXml(menuDefinitions.getPath(), "game");

        fNifty.getScreen(IScreenNames.SCREEN_GAME).addKeyboardInputHandler(new MenuInputMapping(), gameScreenController);
        fNifty.getScreen(IScreenNames.SCREEN_MENU).addKeyboardInputHandler(new MenuInputMapping(), menuScreenController);
    }

    private void drawInterface()
    {
      fStreetPanel.draw();
    }

    private void processInput()
    {

	    fStreetPanel.handleClick();

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
	    Vector2f translationVec = new Vector2f(-fPlayer.getX(),-fPlayer.getY());
	    translationVec.translate(getWidth()/2,getHeight()/2);
	    translationVec.scale(fPlayer.getZoom());
	    translationVec.translate(-getWidth()/2,-getHeight()/2);

        toReturn.translate(translationVec.getX(),translationVec.getY());
        return toReturn;
    }

    public void pause()
    {
        fPaused = true;
    }

    public void unPause()
    {
        fPaused = false;
    }

    public DataStorage2d getFrameData()
    {
        return fFPSData;
    }
}
