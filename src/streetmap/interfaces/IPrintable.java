package streetmap.interfaces;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: ulrich.tewes
 * Date: 11/22/11
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IPrintable
{
	Point2D getPosition();

	float getLength();

	double getAngle();

	org.lwjgl.util.ReadableColor getColor();

	public Integer getImageId();

	public String getImagePath();

	float getStepWidth();

	float getWidth();
}
