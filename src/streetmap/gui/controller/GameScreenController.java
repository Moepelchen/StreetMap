/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.gui.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
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
public class GameScreenController implements ScreenController
{
    private final SSGlobals fGlobals;

    private Nifty fNifty;

    public SSGlobals getGlobals()
    {
        return fGlobals;
    }

    public GameScreenController(SSGlobals globals)
    {
        fGlobals = globals;
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
	public void onStartScreen()
	{

	}

	@Override
	public void onEndScreen()
	{

	}

    public void activateMenu()
    {
        fNifty.gotoScreen("menu");
        fGlobals.getGame().pause();
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
