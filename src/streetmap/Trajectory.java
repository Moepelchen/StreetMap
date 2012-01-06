package streetmap;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 1/6/12
 * Time: 8:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class Trajectory
{
	private static final int INT = 10000000;
	private double fA;

	private double fB;

	private double fDirection;

	private Lane fLane;

	public Trajectory(Lane lane){
		fLane = lane;
		Point2D p1 = lane.getStart().getPosition();
		Point2D p2 = lane.getEnd().getPosition();

		double deltaX = p2.getX() -p1.getX();
		double deltaY = p2.getY() -p1.getY();


		if(deltaX != 0){
			fA = (deltaY)/(deltaX);
			fB = p1.getY()- fA * p1.getX();
		}
		else{
			fA = INT;
		}




		double angle = Math.abs(Math.atan2(deltaY,deltaX));
		if(angle >= Math.PI/2&& angle <= Math.PI*6/4){
			fDirection = -1;
		}else
			fDirection = +1;


	}

	public Point2D calculatePosition(Point2D pos, double speed){
		Point2D newPos = new Point2D.Double();
		double x = pos.getX() + fDirection *speed;
		if(fA != INT)
			newPos.setLocation(x,fA*x +fB);
		else if(fA == INT){
			newPos.setLocation(pos.getX(),pos.getY()-fDirection*speed);
		}


		return newPos;
	}
}
