/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.gui.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import streetmap.KeyHandler;
import streetmap.SSGlobals;
import streetmap.gui.ILayerNames;
import streetmap.gui.IScreenNames;

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
public class GameScreenController extends AbstractScreenController
{

	private final KeyHandler fKeyHandler;

	@Override
	public void bind(Nifty nifty, Screen screen)
	{
		super.bind(nifty,screen);
		Screen current = nifty.getCurrentScreen();
		if (current.getScreenId().equals(IScreenNames.SCREEN_GAME))
		{
			current.findElementByName(ILayerNames.LAYER_DEBUG).setVisible(false);
			nifty.update();
			nifty.render(false);

		}
	}


	@Override
	protected String getEscapeScreen()
	{
		return IScreenNames.SCREEN_MENU;
	}

	@Override
	protected void postScreenActivation()
	{
		getGlobals().getGame().pause();
	}

	@Override
	public void onEndScreen()
	{

	}

	public GameScreenController(SSGlobals globals)
	{
		super(globals);
		fKeyHandler = new KeyHandler(globals);
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
// -----------------------------------------------------
// methods
// -----------------------------------------------------
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //GameScreenController
