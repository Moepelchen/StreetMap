package streetmap.utils;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import streetmap.interfaces.IPrintable;

import java.awt.geom.Point2D;

/**
 * Copyright Ulrich Tewes
 * Created by ulrichtewes on 10.10.14.
 */
public class Rectangle implements IPrintable
{
    private Point2D.Double fPos;
    private float aFloat = 100f;

    public Rectangle(double y, double y1, int i)
    {
        fPos = new Point2D.Double(y,y1);
        aFloat = i;
    }

    @Override
    public Point2D getPosition()
    {
        return fPos;
    }

    @Override
    public float getLength()
    {
        return aFloat;
    }

    @Override
    public double getAngle()
    {
        return 0;
    }

    @Override
    public ReadableColor getColor()
    {
        return new Color(0,255,0,120);

    }

    @Override
    public Integer getImageId()
    {
        return 12;
    }

    @Override
    public String getImagePath()
    {
        return null;
    }

    @Override
    public float getStepWidth()
    {
        return 0.015625f;
    }

    public void setaFloat(float aFloat)
    {
        this.aFloat = aFloat;
    }

    @Override
    public float getWidth()
    {
        return aFloat;
    }

    public void setPosition(float deltaX, float deltaY, int i)
    {
        fPos = new Point2D.Double(deltaX,deltaY);
    }

    public void setPosition(double x, double y, int i)
    {
        fPos = new Point2D.Double(x,y);
    }
}
