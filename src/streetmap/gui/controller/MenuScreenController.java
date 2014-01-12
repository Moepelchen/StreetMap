package streetmap.gui.controller;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import streetmap.SSGlobals;

/**
 * Created by ulrichtewes on 12.01.14.
 */
public class MenuScreenController implements ScreenController
{

    private final SSGlobals fGlobals;
    private Nifty fNifty;

    public MenuScreenController(SSGlobals globals)
    {
        fGlobals = globals;
    }

    @Override
    public void bind(Nifty nifty, Screen screen)
    {
        fNifty = nifty;
    }

    @Override
    public void onStartScreen()
    {

    }

    @Override
    public void onEndScreen()
    {

    }

    public void unPause()
    {
        fGlobals.getGame().unPause();
        fNifty.gotoScreen("game");
    }
}
