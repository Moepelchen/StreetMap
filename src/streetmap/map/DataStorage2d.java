package streetmap.map;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by ulrichtewes on 23.11.13.
 */
public class DataStorage2d
{
    private final ArrayList<Double> fData;
    private final int fSize;

    public Double getMax()
    {
        return fMax;
    }

    private Double fMax;

    public DataStorage2d(int i)
    {
        fSize = i;
        fData = new ArrayList<Double>();
    }

    public void add(Double i)
    {
        fData.add(i);
        if(fSize < fData.size())
        {
            fData.remove(0);
        }
    }

    public ArrayList<Point2D> getData()
    {
        fMax = 0.0;
        ArrayList<Point2D> toReturn = new ArrayList<Point2D>();
        for (int i =0; i<fData.size();i++)
        {
            Double y = fData.get(i);
            toReturn.add(new Point2D.Double(i, y));
            if(fMax<y)
            {
                fMax = y;
            }
        }
        return toReturn;
    }
}
