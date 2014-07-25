/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.gui.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.dynamic.PanelCreator;
import de.lessvoid.nifty.controls.dynamic.TextCreator;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyStandardInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
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
    public void onStartScreen()
    {
        removeAllPreviousFeedback();
    }

	@Override
	public boolean keyEvent(NiftyInputEvent niftyInputEvent)
	{
		if(niftyInputEvent != null)
		{
			if (niftyInputEvent.equals(NiftyStandardInputEvent.Escape))
			{
				activateScreenWithCall(getEscapeScreen(), true);
				return true;
			}
		}
		return false;
	}

	protected abstract String getEscapeScreen();

	public void activateScreen(String name)
	{
		activateScreenWithCall(name, false);
	}

    public void activateScreenAndPause(String name)
    {
        activateScreen(name);
        fGlobals.getGame().pause();
    }

    public void activateScreenAndUnPause(String name)
    {
        activateScreen(name);
        fGlobals.getGame().unPause();
    }

	public void activateScreenWithCall(String name, boolean callPostAction)
 {
     fNifty.gotoScreen(name);
	 if(callPostAction)
	 {
		 postScreenActivation();
	 }
 }

    protected void createFeedBack(Screen saveScreen, TextField niftyControl, String text)
    {

        Element parent = niftyControl.getElement().getParent();
        removePreviousFeedback(parent);
        PanelCreator panelCreator = new PanelCreator();
        panelCreator.setChildLayout("horizontal");
        panelCreator.setId("feedback");
        Element panel = panelCreator.create(fNifty, saveScreen, parent);
        TextCreator textCreator = new TextCreator(text);
        textCreator.setColor(String.valueOf(Color.WHITE));
        textCreator.setFont("aurulent-sans-16.fnt");
        panel.add(textCreator.create(fNifty, saveScreen, panel));
    }

    private void removePreviousFeedback(Element parent)
    {
        removeElement(parent.findElementByName("feedback"));
    }

    private void removeAllPreviousFeedback()
    {
        Element toRemove = fNifty.getCurrentScreen().findElementByName("feedback");
        removeElement(toRemove);
    }

    private void removeElement(Element toRemove)
    {
        if(toRemove != null)
        {
            toRemove.markForRemoval();
            fNifty.update();
        }
    }

    protected abstract void postScreenActivation();
// -----------------------------------------------------
// accessors
// -----------------------------------------------------

	public SSGlobals getGlobals()
    {
        return fGlobals;
    }

} //AbstractScreenController
