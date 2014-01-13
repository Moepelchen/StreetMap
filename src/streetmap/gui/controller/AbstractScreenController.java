/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.gui.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import streetmap.SSGlobals;

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
public abstract class AbstractScreenController implements KeyInputHandler, ScreenController
{
	private final SSGlobals fGlobals;
	protected Nifty fNifty;

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
	public AbstractScreenController(SSGlobals globals)
	{
		fGlobals = globals;
	}
// -----------------------------------------------------
// methods
// -----------------------------------------------------
// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------

	public void bind(Nifty nifty, Screen screen)
	{
		fNifty = nifty;
	}

	@Override
	public boolean keyEvent(NiftyInputEvent niftyInputEvent)
	{
		if(niftyInputEvent != null)
		{
			if (niftyInputEvent.equals(NiftyInputEvent.Escape))
			{
				fNifty.gotoScreen(getEscapeScreen());
				return true;
			}
		}
		return false;
	}

	protected abstract String getEscapeScreen();


	public void activateScreen(String name)
 {
     fNifty.gotoScreen(name);
     getGlobals().getGame().pause();
 }
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

	public SSGlobals getGlobals()
    {
        return fGlobals;
    }

} //AbstractScreenController
