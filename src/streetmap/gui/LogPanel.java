package streetmap.gui;

import streetmap.map.DataStorage2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by ulrichtewes on 23.11.13.
 */
public class LogPanel extends JPanel implements ActionListener
{
    private final DataStorage2d fData;

    private double fMaxY = 0;

    public LogPanel(DataStorage2d flowData, double maxY)
    {
        this(flowData);
        fMaxY = maxY;
    }

    public LogPanel(DataStorage2d flowData)
    {
        fData = flowData;
        Timer timer;
        this.setSize(300,100);
        this.setVisible(true);
        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

    @Override
    public void paint(Graphics g)
    {

        g.setColor(Color.black);
        g.drawRect(0,0,300,100);
        super.paint(g);
        ArrayList<Point2D> points = fData.getData();
        Double max = Math.max(fData.getMax(), fMaxY);
        Polygon p = new Polygon();

        p.addPoint(0,100);
        if(max >0 && points.size() >0)
        {
            for (Point2D point : points)
            {
                double y = (point.getY()/max)*100;
                p.addPoint((int)point.getX(),100- (int)y);
            }
            p.addPoint(Math.min(300,(int)points.get(points.size()-1).getX()),100);
            g.setColor(Color.green);
            g.fillPolygon(p);
        }
    }
}
