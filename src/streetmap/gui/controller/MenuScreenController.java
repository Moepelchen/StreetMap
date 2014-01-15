package streetmap.gui.controller;

import streetmap.SSGlobals;
import streetmap.gui.IScreenNames;

/**
 * Created by ulrichtewes on 12.01.14.
 */
public class MenuScreenController extends AbstractScreenController
{

	@Override
	protected String getEscapeScreen()
	{
		return IScreenNames.SCREEN_GAME;
	}

	@Override
	protected void postScreenActivation()
	{
		getGlobals().getGame().unPause();
	}

	public MenuScreenController(SSGlobals globals)
    {
	    super(globals);
    }

    public void onStartScreen()
    {

    }

    public void onEndScreen()
    {

    }
}
