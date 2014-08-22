package streetmap.gui.effects;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import streetmap.SSGlobals;
import streetmap.gui.controller.GameScreenController;
import streetmap.map.DataStorage2d;

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
      /*  Color color = Color.WHITE;

        DataStorage2d flowData = getData();
        ArrayList<Point2D> data = flowData.getData();
        GL11.glPushMatrix();
        GL11.glTranslated(element.getX(), element.getY(), 0);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        glColor3d((double) color.getRed() / 255, (double) color.getGreen() / 255, (double) color.getBlue() / 255);
        GL11.glVertex2d(0,element.getHeight())  ;
        if(!data.isEmpty())
        {
            double max = Math.max(flowData.getMax(), getMax());

            for (Point2D point2D : data)
            {
                double y = (point2D.getY()/max)*element.getHeight();
                GL11.glVertex2d(point2D.getX(),element.getHeight() - y);

            }
            GL11.glVertex2d(Math.min(element.getWidth(), (int) data.get(data.size() - 1).getX()), element.getHeight());
        }

        GL11.glEnd();
        GL11.glPopMatrix();

	    font.drawString(element.getX(), element.getY(), String.valueOf(getData().getCurrent()));
	    font.drawString(element.getX(), element.getY()+30, String.valueOf(getData().getMax()));*/

    }

    protected abstract DataStorage2d getData();



    @Override
    public void deactivate()
    {
    }

    public abstract double getMax();
}
