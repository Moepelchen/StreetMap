package streetmap.gui.controller;

import de.lessvoid.nifty.controls.TextField;
import streetmap.SSGlobals;
import streetmap.gui.IScreenNames;

/**
 * Created by ulrichtewes on 19.01.14.
 */
public class DebugScreenController extends AbstractScreenController
{
    public DebugScreenController(SSGlobals globals)
    {
        super(globals);
    }

    @Override
    protected String getEscapeScreen()
    {
        return IScreenNames.SCREEN_MENU;
    }

    @Override
    protected void postScreenActivation()
    {

    }

    @Override
    public void onStartScreen()
    {
        super.onStartScreen();
        TextField numberOfCars = getNumOfCarsInput();
        numberOfCars.setText(String.valueOf(getGlobals().getConfig().getMaximumNumOfCars()));
        TextField hetMod = getHeatModInput();
        hetMod.setText(String.valueOf(getGlobals().getConfig().getHeatMapModifier()));
    }

    private TextField getHeatModInput()
    {
        return fNifty.getCurrentScreen().findNiftyControl("heatMod", TextField.class);
    }

    private TextField getNumOfCarsInput()
    {
        return fNifty.getCurrentScreen().findNiftyControl("numOfCars", TextField.class);
    }

    @Override
    public void onEndScreen()
    {

    }

    public void save()
    {
        String heatInput = getHeatModInput().getDisplayedText();
        String numOfCarsInput = getNumOfCarsInput().getDisplayedText();
        try
        {
            getGlobals().getConfig().setHeatMapModifier(Double.parseDouble(heatInput));
        } catch (NumberFormatException e)
        {
            createFeedBack(fNifty.getCurrentScreen(), getHeatModInput(), "Please enter a number!");
        }
        try
        {
            getGlobals().getConfig().setMaximumNumOfCars(Integer.parseInt(numOfCarsInput));
        } catch (NumberFormatException e)
        {
            createFeedBack(fNifty.getCurrentScreen(), getNumOfCarsInput(), "Please enter a number!");
        }


    }
}
