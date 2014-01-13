/*
 * Copyright (C) veenion GmbH 1999-2012.
 */

package streetmap.gui.inputmapping;

import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;

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
public class MenuInputMapping extends de.lessvoid.nifty.input.mapping.MenuInputMapping
{

// -----------------------------------------------------
// overwritten methods from superclasses
// -----------------------------------------------------

	@Override
	public NiftyInputEvent convert(KeyboardInputEvent inputEvent)
	{
		if(inputEvent.getKey() == KeyboardInputEvent.KEY_ESCAPE && inputEvent.isKeyDown())
		{
			return NiftyInputEvent.Escape;
		}

		return super.convert(inputEvent);
	}

// -----------------------------------------------------
// accessors
// -----------------------------------------------------

} //MenuInputMapping
