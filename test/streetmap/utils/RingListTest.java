/*
 * Copyright (C) Ulrich Tewes   2010-2012.
 */

package streetmap.utils;

import org.junit.Test;

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

		buf.add("W");
		buf.add("S");
		buf.add("E");
		buf.add("N");

		assertEquals(buf.get(4), "W");
		assertEquals(buf.get(8), "W");
		assertEquals(buf.get(481), "S");
		assertEquals(buf.get(482), "E");
		assertEquals(buf.get(-5), "N");
		assertEquals(buf.get(-4), "W");
		assertEquals(buf.get(-400), "W");
		assertEquals(buf.get(-421), "N");
		assertEquals(buf.get(-422), "E");
		assertEquals(buf.get(-423), "S");

	}

} //RingListTest
