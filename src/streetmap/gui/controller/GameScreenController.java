/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.gui.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
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

    public GameScreenController(SSGlobals globals)
    {
	    super(globals);
    }

	@Override
	public void bind(Nifty nifty, Screen screen)
	{
        fNifty = nifty;
        Screen current = nifty.getCurrentScreen();
        if(current.getScreenId().equals(IScreenNames.SCREEN_GAME))
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
	public void onStartScreen()
	{

	}

	@Override
	public void onEndScreen()
	{

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
