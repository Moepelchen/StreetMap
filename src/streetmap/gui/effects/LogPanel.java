package streetmap.gui.effects;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import de.lessvoid.nifty.spi.render.RenderDevice;
import de.lessvoid.nifty.spi.render.RenderFont;
import de.lessvoid.nifty.tools.Color;
import streetmap.SSGlobals;
import streetmap.gui.controller.GameScreenController;
import streetmap.utils.DataStorage2d;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by ulrichtewes on 12.01.14.
 */
public abstract class LogPanel implements EffectImpl
{
    protected SSGlobals fGlobals;

	@Override
    public void activate(Nifty nifty, Element element, EffectProperties effectProperties)
    {
        GameScreenController controller = (GameScreenController) nifty.getCurrentScreen().getScreenController();
        fGlobals = controller.getGlobals();
    }

    @Override
    public void execute(Element element, float v, Falloff falloff, NiftyRenderEngine niftyRenderEngine)
    {

        DataStorage2d flowData = getData();
        ArrayList<Point2D> data = flowData.getData();
        RenderDevice device = niftyRenderEngine.getRenderDevice();

        if(!data.isEmpty())
        {
            double max = Math.max(flowData.getMax(), getMax());

            for (Point2D point2D : data)
            {
                double y = (point2D.getY()/max)*element.getHeight();
                device.renderQuad((int)point2D.getX()+element.getX(),(int)(element.getHeight() - y)+element.getY(),1,1, Color.WHITE);

            }
        }

        RenderFont font;
        font = niftyRenderEngine.createFont("aurulent-sans-16.fnt");
        if (font != null)
        {
            device.renderFont(font,String.valueOf(getData().getCurrent()),element.getX(),element.getY(),Color.WHITE,1,1);
            device.renderFont(font,String.valueOf(getData().getMax()),element.getX(),element.getY()+30,Color.WHITE,1,1);
        }


    }

    protected abstract DataStorage2d getData();


    @Override
    public void deactivate()
    {
    }

    public abstract double getMax();
}
