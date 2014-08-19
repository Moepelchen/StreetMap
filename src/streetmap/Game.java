package streetmap;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.render.batch.BatchRenderConfiguration;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import de.lessvoid.nifty.render.batch.spi.BatchRenderBackend;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglBatchRenderBackendCoreProfileFactory;
import de.lessvoid.nifty.renderer.lwjgl.time.LWJGLTimeProvider;
import de.lessvoid.nifty.screen.ScreenController;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;
import org.xml.sax.SAXException;
import streetmap.car.PrintableRenderBuffer;
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
import streetmap.saveandload.config.ConfigLoader;
import streetmap.saveandload.map.MapLoader;
import streetmap.utils.TextureCache;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Copyright Ulrich Tewes
 * Created by ulrichtewes on 03.12.13.
 */
public class Game
{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 680;
	private final Player fPlayer;
	SSGlobals fGlobals;
	private GLStreetPanel fStreetPanel;
	private KeyHandler fKeyboardHandler;
	private boolean fPaused;
	private Vector2f fScalePoint;

	/**
	 * time at last frame
	 */
	long lastFrame;

	/**
	 * frames per second
	 */
	int fps;
	/**
	 * last fps time
	 */
	long lastFPS;

	private DataStorage2d fFPSData = new DataStorage2d(300);
	private DataStorage2d fPathData = new DataStorage2d(300);
	private int fFSID;
	private int indicesCount;
	private int vaoId;
	private int vboId;
	private int vbocId;
	private int vboiId;

	public int getFSID()
	{
		return fFSID;
	}

	public int getVSID()
	{
		return fVSID;
	}

	public int getPID()
	{
		return fPID;
	}

	private int fVSID;
	private int fPID;

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
		fPlayer = new Player(0, 0);
		fGlobals.setGame(this);
		new Map(globals);
		fScalePoint = new Vector2f(getWidth() / 2, getHeight() / 2);
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

		// Setup an OpenGL context with API version 3.2
		try
		{
			PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                    .withForwardCompatible(true)
                    .withProfileCore(true);

			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Game");
			Display.create(pixelFormat, contextAtrributes);

			GL11.glViewport(0, 0, getWidth(), getHeight());
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}


		// Setup an XNA like background color
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

		// Map the internal OpenGL coordinate system to the entire screen
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
        setupShaders();
		Keyboard.enableRepeatEvents(true);

		fStreetPanel = new GLStreetPanel(fGlobals);
		fKeyboardHandler = new KeyHandler(fGlobals);

		initNifty();
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		// glEnable(GL11.GL_DEPTH_TEST);
        MapLoader mapLoader = new MapLoader();
        ConfigLoader configLoader = new ConfigLoader();
        try
        {
            File file = new File("./save/lane.xml");
            configLoader.load(file, fGlobals);
            mapLoader.load(file, fGlobals);

            fGlobals.handleLoading();
        }
        catch (ParserConfigurationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | SAXException | IOException e1)
        {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		while (!Display.isCloseRequested())
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			updateFPS();
			fGlobals.getTimeHandler().tickTime();
			// Clear the screen and depth buffer

			if (!this.isPaused())
			{
				fGlobals.getMap().simulate();
				int pathsRequested = fGlobals.getMap().getPathFactory().getPathsRequested();

				fPathData.add((double) pathsRequested);
			}
			fGlobals.getMap().paint();

			drawInterface();
			fNifty.update();
			fNifty.render(false);
			Display.update();
			//Display.sync(30); // cap fps to 60fps

			String screenId = fNifty.getCurrentScreen().getScreenId();
			if (fNifty.getCurrentScreen() != null && screenId != null && screenId.equals(IScreenNames.SCREEN_GAME))
			{
				processInput();
			}

		}
		TextureCache.releaseTextures();
		release();
		Display.destroy();
		System.exit(0);

	}

	private void release()
	{
		// Delete the shaders
		GL20.glUseProgram(0);
		GL20.glDetachShader(fPID, fVSID);
		GL20.glDetachShader(fPID, fFSID);

		GL20.glDeleteShader(fVSID);
		GL20.glDeleteShader(fFSID);
		GL20.glDeleteProgram(fPID);
	}

	private void setupShaders()
    {
        int errorCheckValue = GL11.glGetError();


	    fPID = GL20.glCreateProgram();
	    fVSID = PrintableRenderBuffer.loadShader("./config/shaders/vertex.glsl", GL20.GL_VERTEX_SHADER);
	    // Load the fragment shader
	    fFSID = PrintableRenderBuffer.loadShader("./config/shaders/fragment.glsl", GL20.GL_FRAGMENT_SHADER);
// Create a new shader program that links both shaders
	    GL20.glAttachShader(fPID, fVSID);
	    GL20.glAttachShader(fPID, fFSID);

// Position information will be attribute 0
        GL20.glBindAttribLocation(fPID, 0, "in_Position");
// Color information will be attribute 1
        GL20.glBindAttribLocation(fPID, 1, "in_Color");
	    GL20.glBindAttribLocation(fPID, 2, "in_TextureCoord");

        GL20.glLinkProgram(fPID);
	    int status = GL20.glGetProgrami(fPID,GL20.GL_LINK_STATUS);
	    if(status == GL11.GL_FALSE)
	    {
		    System.out.println("PROGRAM NOT LINKED!");
		    System.exit(-1);
	    }
	    GL20.glValidateProgram(fPID);
	    status = GL20.glGetProgrami(fPID, GL20.GL_VALIDATE_STATUS);
	    if (status == GL11.GL_FALSE)
	    {
		    System.out.println("PROGRAM NOT VALID!");
		    System.exit(-1);
	    }

        errorCheckValue = GL11.glGetError();
        if (errorCheckValue != GL11.GL_NO_ERROR)
        {
            System.out.println("ERROR - Could not create the shaders:" + GLU.gluErrorString(errorCheckValue));
            System.exit(-1);
        }
    }

	/**
	 * Calculate how many milliseconds have passed
	 * since last frame.
	 *
	 * @return milliseconds passed since last frame
	 */
	public int getDelta()
	{
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
	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS()
	{
		if (getTime() - lastFPS > 1000)
		{
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
		BatchRenderBackend renderBackend = LwjglBatchRenderBackendCoreProfileFactory.create();
		BatchRenderConfiguration config = new BatchRenderConfiguration();
		BatchRenderDevice renderDevice = new BatchRenderDevice(renderBackend, config);
		fNifty = new Nifty(renderDevice, new NullSoundDevice(), inputSystem, new LWJGLTimeProvider());
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
		Vector2f toReturn = new Vector2f(x, y);
		Vector2f translationVec = new Vector2f(-fPlayer.getX(), -fPlayer.getY());
		translationVec.translate(getWidth() / 2, getHeight() / 2);
		translationVec.scale(fPlayer.getZoom());
		translationVec.translate(-getWidth() / 2, -getHeight() / 2);

		toReturn.translate(translationVec.getX(), translationVec.getY());
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
