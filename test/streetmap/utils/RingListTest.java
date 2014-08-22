/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.utils;

import org.junit.Test;
import streetmap.map.tile.ICompassPoint;

import static org.junit.Assert.assertEquals;

/**
 * Short description in a complete sentence.
 * <p/>
 * Purpose of this class / interface
 * Say how it should be and should'nt be used
 * <p/>
 * Known bugs: None
 * Historical remarks: None
 *
 * @author Ulrich Tewes
 * @version 1.0
 * @since Release
 */
public class RingListTest
{
	@Test
	public void testCircularAcces()
	{
		RingList<String> buf = new RingList<String>();

		buf.add(ICompassPoint.COMPASS_POINT_W);
		buf.add(ICompassPoint.COMPASS_POINT_S);
		buf.add(ICompassPoint.COMPASS_POINT_E);
		buf.add(ICompassPoint.COMPASS_POINT_N);

		assertEquals(buf.get(4), ICompassPoint.COMPASS_POINT_W);
		assertEquals(buf.get(8), ICompassPoint.COMPASS_POINT_W);
		assertEquals(buf.get(481), ICompassPoint.COMPASS_POINT_S);
		assertEquals(buf.get(482), ICompassPoint.COMPASS_POINT_E);
		assertEquals(buf.get(-5), ICompassPoint.COMPASS_POINT_N);
		assertEquals(buf.get(-4), ICompassPoint.COMPASS_POINT_W);
		assertEquals(buf.get(-400), ICompassPoint.COMPASS_POINT_W);
		assertEquals(buf.get(-421), ICompassPoint.COMPASS_POINT_N);
		assertEquals(buf.get(-422), ICompassPoint.COMPASS_POINT_E);
		assertEquals(buf.get(-423), ICompassPoint.COMPASS_POINT_S);

	}

} //RingListTest
