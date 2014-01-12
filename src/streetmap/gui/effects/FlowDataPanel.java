package streetmap.gui.effects;

import streetmap.map.DataStorage2d;

/**
 * Created by ulrichtewes on 12.01.14.
 */
public class FlowDataPanel extends LogPanel
{

    protected DataStorage2d getData()
    {
        return fGlobals.getMap().getFlowData();
    }

    @Override
    public double getMax()
    {
        return 0.5;
    }
}
