package streetmap.timehandling;

import java.util.Date;

/**
 * Created by ulrichtewes on 22.02.14.
 */
public interface ITimeHandler
{

    /**
     * set game speed
     * @param gameSpeed game speed to set
     */
    public void setGameSpeed(double gameSpeed);

    /**
     * Tick the time, actual time calculations are dependant on current game speed
     */
    public void tickTime();

    /**
     * get the current ingame date
     * @return current Date
     */
    public Date getCurrentDate();

    /**
     * get the hour of the day
     * @return hour of the day
     */
    public int getHourOfDay();


    /**
     * Method to determine whether it is night
     * @return true if it is night, else false
     */
    public boolean isNight();

    /**
     * Method to determine whether it is day
     * @return true if it is day, else false
     */
    public boolean isDay();


    public int getDay();

    public int getYear();
}
