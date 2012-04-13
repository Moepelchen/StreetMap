package streetmap;

import flanagan.interpolation.CubicSpline;
import streetmap.Interfaces.ITrajectory;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: ulrichtewes
 * Date: 23.01.12
 * Time: 07:28
 * To change this template use File | Settings | File Templates.
 */

public class BendTrajectory implements ITrajectory{
    CubicSpline fSpline;

    public BendTrajectory(Lane lane){
        double[] x = {lane.getStart().getPosition().getX(),lane.getStart().getPosition().getX()+10,lane.getEnd().getPosition().getX()+2};
        double[] y = {lane.getStart().getPosition().getY(),lane.getEnd().getPosition().getY()-10,lane.getEnd().getPosition().getY()};
       fSpline = new CubicSpline(x,y);
        fSpline.setDeriv(new double[]{Double.MAX_VALUE, 1, 0});
    }

    @Override
    public Point2D calculatePosition(Point2D pos, double speed) {
        double newX = pos.getX()+speed;
        //fSpline.interpolate(newX);
        return new Point2D.Double(newX,fSpline.interpolate(newX));  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getAngle() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
