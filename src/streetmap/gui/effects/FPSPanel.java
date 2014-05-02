package streetmap.gui.effects;

import streetmap.map.DataStorage2d;

/**
 * Created by ulrichtewes on 12.01.14.
 */
public class FPSPanel extends LogPanel
{

    protected DataStorage2d getData()
    {
        return fGlobals.getGame().getFrameData();
    }

    @Override
    public double getMax()
    {
        return 200;
    }
}
