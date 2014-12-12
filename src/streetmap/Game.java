package streetmap;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.render.batch.BatchRenderConfiguration;
import de.lessvoid.nifty.render.batch.BatchRenderDevice;
import de.lessvoid.nifty.render.batch.spi.BatchRenderBackend;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglBatchRenderBackendCoreProfileFactory;
import de.lessvoid.nifty.renderer.lwjgl.time.LWJGLTimeProvider;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import streetmap.gui.GLStreetPanel;
import streetmap.gui.IScreenNames;
import streetmap.gui.controller.*;
import streetmap.gui.inputmapping.MenuInputMapping;
import streetmap.map.Map;
import streetmap.utils.DataStorage2d;
import streetmap.utils.PrintableRenderBuffer;
import streetmap.utils.TextureCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;

/**
 * Copyright Ulrich Tewes
 * Created by ulrichtewes on 03.12.13.
 */
public class Game
{
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 1024;
	private final Player fPlayer;
	private final SSGlobals fGlobals;
	private GLStreetPanel fStreetPanel;
	private KeyHandler fKeyboardHandler;
	private boolean fPaused;

	/**
	 * time at last frame
	 */
    private long lastFrame;

	/**
	 * frames per second
	 */
    private int fps;
	/**
	 * last fps time
	 */
    private long lastFPS;

	private final DataStorage2d fFPSData = new DataStorage2d(300);
	private final DataStorage2d fPathData = new DataStorage2d(300);
	private int fFSID;
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Matrix4f modelMatrix;
    private FloatBuffer matrix44Buffer;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;
    private int modelMatrixLocation;

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

	boolean isPaused()
	{
		return fPaused;
	}

	public DataStorage2d getPathData()
	{
		return fPathData;
	}

	private Game(SSGlobals globals)
	{
		fGlobals = globals;
		fPlayer = new Player(0, 0);
		fGlobals.setGame(this);
		new Map(globals);
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

	void start() throws Exception
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
        setupMatrices();
		Keyboard.enableRepeatEvents(true);

		initNifty();

		fStreetPanel = new GLStreetPanel(fGlobals);
		fKeyboardHandler = new KeyHandler(fGlobals);

		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

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
			fStreetPanel.draw();
			fNifty.update();
			fNifty.render(false);
            updateMatrises();
			Display.update();
			//Display.sync(60); // cap fps to 60fps

            Screen currentScreen = fNifty.getCurrentScreen();
            if (currentScreen != null) {
                String screenId = currentScreen.getScreenId();
                if (screenId.equals(IScreenNames.SCREEN_GAME)) {
                    processInput();
                }
            }

		}
		TextureCache.releaseTextures();
		release();
		Display.destroy();
		System.exit(0);

	}

    private void updateMatrises()
    {
        //-- Update matrices
        // Reset view and model matrices
        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();

        // Translate camera
		Vector3f pos = fPlayer.getPos();

		pos.set(fPlayer.getX()/getWidth(), fPlayer.getY()/getHeight());
		Matrix4f.translate(new Vector3f(0,0,-1), viewMatrix, viewMatrix);
		// Scale, translate and rotate model
		Matrix4f.scale(fPlayer.getZoom(), modelMatrix, modelMatrix);
		Matrix4f.translate(pos,modelMatrix,modelMatrix);

        // Upload matrices to the uniform variables
        GL20.glUseProgram(fPID);

        projectionMatrix.store(matrix44Buffer); matrix44Buffer.flip();
        GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
        viewMatrix.store(matrix44Buffer); matrix44Buffer.flip();
        GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
        modelMatrix.store(matrix44Buffer); matrix44Buffer.flip();
        GL20.glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);
        GL20.glUseProgram(0);

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
        int errorCheckValue;


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

	    }

        errorCheckValue = GL11.glGetError();
        if (errorCheckValue != GL11.GL_NO_ERROR)
        {
            System.out.println("ERROR - Could not create the shaders:" + GLU.gluErrorString(errorCheckValue));
            System.exit(-1);
        }

        // Get matrices uniform locations

        projectionMatrixLocation = GL20.glGetUniformLocation(fPID,"projectionMatrix");

        viewMatrixLocation = GL20.glGetUniformLocation(fPID, "viewMatrix");

        modelMatrixLocation = GL20.glGetUniformLocation(fPID, "modelMatrix");
    }

	/**
	 * Calculate how many milliseconds have passed
	 * since last frame.
	 *
	 * @return milliseconds passed since last frame
	 */
    int getDelta()
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
    long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
    void updateFPS()
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

        Screen gameScreen = fNifty.getScreen(IScreenNames.SCREEN_GAME);
        Screen menuScreen = fNifty.getScreen(IScreenNames.SCREEN_MENU);
        if (gameScreen != null) {
            gameScreen.addKeyboardInputHandler(new MenuInputMapping(), gameScreenController);
        }
        if (menuScreen != null) {
            menuScreen.addKeyboardInputHandler(new MenuInputMapping(), menuScreenController);
        }
        fGlobals.setNifty(fNifty);
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

	public Vector3f getTranslatedCoords(int x, int y)
	{
		Vector3f toReturn = new Vector3f(x, y, 0);
		float playerX = fPlayer.getX();
		float playerY = fPlayer.getY();
		Vector2f playerVec = new Vector2f(playerX,playerY);
		Vector2f windowVec = new Vector2f(WIDTH/2,HEIGHT/2);

		// 1.13 when zoomed in
		float scale2 =  1.3f+1/fPlayer.getZoom().getX();
		playerVec.scale(1/ scale2);
		toReturn.set(x - playerVec.getX(), y - playerVec.getY());
		toReturn.set(toReturn.getX() - windowVec.getX(), toReturn.getY() - windowVec.getY());
		toReturn.scale(scale2);
		toReturn.set(toReturn.getX(), toReturn.getY());

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

    private void setupMatrices() {
        // Setup projection matrix
        projectionMatrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = (float)HEIGHT / (float)HEIGHT;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = this.coTangent(this.degreesToRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projectionMatrix.m00 = y_scale;
        projectionMatrix.m11 = x_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
        projectionMatrix.m33 = 0;

        // Setup view matrix
        viewMatrix = new Matrix4f();

        // Setup model matrix
        modelMatrix = new Matrix4f();

        // Create a FloatBuffer with the proper size to store our matrices later
        matrix44Buffer = BufferUtils.createFloatBuffer(16);
    }

    private float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }

    private float degreesToRadians(float degrees) {
        return degrees * (float)(Math.PI / 180d);
    }

	public GLStreetPanel getPlaceablePanel() {
		return fStreetPanel;
	}
}
