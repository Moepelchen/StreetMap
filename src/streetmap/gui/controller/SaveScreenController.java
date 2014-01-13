/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.gui.controller;

import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.dynamic.PanelCreator;
import de.lessvoid.nifty.controls.dynamic.TextCreator;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.Color;
import streetmap.SSGlobals;
import streetmap.gui.IScreenNames;
import streetmap.saveandload.Saver;

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
public class SaveScreenController extends AbstractScreenController
{
	@Override
	protected String getEscapeScreen()
	{
		return IScreenNames.SCREEN_MENU;
	}

	@Override
	public void onStartScreen()
	{
		removePreviousFeedback();
	}

	@Override
	public void onEndScreen()
	{

	}

	public SaveScreenController(SSGlobals globals)
	{
		super(globals);
	}

	public void save()
	{
		Screen saveScreen = fNifty.getScreen("save");
		TextField niftyControl = saveScreen.findNiftyControl("name", TextField.class);
		String text = niftyControl.getDisplayedText();
		if(text != null)
		{
			boolean success = new Saver().save(getGlobals(), text);
			if(!success)
			{
				createFeedBack(saveScreen, niftyControl,"Your data could not be saved! Please check the file name!");
			}
			else
			{
				createFeedBack(saveScreen, niftyControl,"Your data has been saved!");
			}
		}
		else
		{
			createFeedBack(saveScreen, niftyControl,"Your data could not be saved! Please check the file name!");
		}
	}

	protected void createFeedBack(Screen saveScreen, TextField niftyControl, String text)
	{

		Element parent = niftyControl.getElement().getParent();
		removePreviousFeedback();
		PanelCreator panelCreator = new PanelCreator();
		panelCreator.setChildLayout("horizontal");
		panelCreator.setId("feedback");
		Element panel = panelCreator.create(fNifty, saveScreen, parent);
		TextCreator textCreator = new TextCreator(text);
		textCreator.setColor(String.valueOf(Color.WHITE));
		textCreator.setFont("aurulent-sans-16.fnt");
		panel.add(textCreator.create(fNifty, saveScreen, panel));
	}

	private void removePreviousFeedback()
	{
		Element toRemove = fNifty.getCurrentScreen().findElementByName("feedback");
		if(toRemove != null)
		{
			toRemove.markForRemoval();
			fNifty.update();
		}
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

} //SaveScreenController
