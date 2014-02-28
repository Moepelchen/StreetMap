package streetmap.timehandling;

import java.util.Date;

/**
 * Copyright Ulrich Tewes
 * Created by ulrichtewes on 22.02.14.
 */
public class TimeHandler implements ITimeHandler
{
    private Double fDays;

    private Double fHourOfDay;

    private Double fMinutesOfDay;

    private Double fYears;

    private Double fGameSpeed;

    public TimeHandler()
    {
        fGameSpeed = 1.0;
        fDays = 0.0;
        fHourOfDay = 12.0;
        fMinutesOfDay = 0.0;
        fYears = 0.0;
    }


    @Override
    public void setGameSpeed(double gameSpeed)
    {
        fGameSpeed = gameSpeed;
    }

    @Override
    public void tickTime()
    {
        fMinutesOfDay = fMinutesOfDay + fGameSpeed;

        if(fMinutesOfDay >= 360.0)
        {
            fMinutesOfDay = fMinutesOfDay %360.0;
            fHourOfDay = fHourOfDay+1.0;
            System.out.println(fYears.intValue() +" years "+ fDays.intValue() + " days "+ fHourOfDay.intValue()+":"+fMinutesOfDay/6);
            if(fHourOfDay >= 24.0)
            {
                fHourOfDay = fHourOfDay % 24.0;
                fDays = fDays +1.0;
                if(fDays >= 365.0)
                {
                    fDays = fDays %365.0;
                    fYears = fYears + 1.0;
                }
            }
        }
    }

    @Override
    public Date getCurrentDate()
    {
        return null;
    }

    @Override
    public int getHourOfDay()
    {
        return fHourOfDay.intValue();
    }

    @Override
    public boolean isNight()
    {
        return fHourOfDay > 20;
    }

    @Override
    public boolean isDay()
    {
        return fHourOfDay > 6 && !isNight();
    }

    @Override
    public int getDay()
    {
        return fDays.intValue();
    }

    @Override
    public int getYear()
    {
        return fYears.intValue();
    }
}
