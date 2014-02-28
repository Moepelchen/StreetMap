package streetmap.timehandling;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by ulrichtewes on 22.02.14.
 */
public class TimeHandlerTest
{

    private ITimeHandler fTimeHandler;

    @Before
    public void setUp()
    {
        fTimeHandler = new TimeHandler();
    }

    @Test
    public void testTickHour()
    {
        for (int i = 0; i < 360; i++)
        {
            fTimeHandler.tickTime();

        }
        assertEquals(13,fTimeHandler.getHourOfDay());
    }

    @Test
    public void testTickDay()
    {
        for (int i = 0; i < 360*24; i++)
        {
            fTimeHandler.tickTime();
        }
        assertEquals(12,fTimeHandler.getHourOfDay());
        assertEquals(1,fTimeHandler.getDay());

    }

    @Test
    public void testTickYear()
    {
        for (int i = 0; i < 360*24*365; i++)
        {
            fTimeHandler.tickTime();
        }
        assertEquals(12,fTimeHandler.getHourOfDay());
        assertEquals(0,fTimeHandler.getDay());
        assertEquals(1,fTimeHandler.getYear());

    }

}
