package streetmap.rules;

import org.junit.Test;
import streetmap.map.tile.ICompassPoint;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class RightBeforeLeftRuleTest
{

	@Test
	public void testIsLeftTurn()
	{
		assertTrue(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_N, ICompassPoint.COMPASS_POINT_E));
		assertTrue(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_W, ICompassPoint.COMPASS_POINT_N));
		assertTrue(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_S, ICompassPoint.COMPASS_POINT_W));
		assertTrue(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_E, ICompassPoint.COMPASS_POINT_S));

		assertFalse(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_E, ICompassPoint.COMPASS_POINT_N));
		assertFalse(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_N, ICompassPoint.COMPASS_POINT_W));
		assertFalse(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_W, ICompassPoint.COMPASS_POINT_S));
		assertFalse(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_S, ICompassPoint.COMPASS_POINT_E));

		assertFalse(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_S, ICompassPoint.COMPASS_POINT_N));
		assertFalse(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_E, ICompassPoint.COMPASS_POINT_W));
		assertFalse(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_N, ICompassPoint.COMPASS_POINT_S));
		assertFalse(RightBeforeLeftRule.isLeftTurn(ICompassPoint.COMPASS_POINT_W, ICompassPoint.COMPASS_POINT_E));
	}

	@Test
	public void testIsRightTurn()
	{
		assertFalse(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_N, ICompassPoint.COMPASS_POINT_E));
		assertFalse(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_W, ICompassPoint.COMPASS_POINT_N));
		assertFalse(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_S, ICompassPoint.COMPASS_POINT_W));
		assertFalse(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_E, ICompassPoint.COMPASS_POINT_S));

		assertTrue(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_E, ICompassPoint.COMPASS_POINT_N));
		assertTrue(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_N, ICompassPoint.COMPASS_POINT_W));
		assertTrue(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_W, ICompassPoint.COMPASS_POINT_S));
		assertTrue(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_S, ICompassPoint.COMPASS_POINT_E));

		assertFalse(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_S, ICompassPoint.COMPASS_POINT_N));
		assertFalse(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_E, ICompassPoint.COMPASS_POINT_W));
		assertFalse(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_N, ICompassPoint.COMPASS_POINT_S));
		assertFalse(RightBeforeLeftRule.isRightTurn(ICompassPoint.COMPASS_POINT_W, ICompassPoint.COMPASS_POINT_E));
	}

	@Test
	public void testIsStraight()
	{
		assertTrue(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_N, ICompassPoint.COMPASS_POINT_S));
		assertTrue(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_S, ICompassPoint.COMPASS_POINT_N));
		assertTrue(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_W, ICompassPoint.COMPASS_POINT_E));
		assertTrue(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_E, ICompassPoint.COMPASS_POINT_W));

		assertFalse(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_E, ICompassPoint.COMPASS_POINT_S));
		assertFalse(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_E, ICompassPoint.COMPASS_POINT_N));

		assertFalse(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_W, ICompassPoint.COMPASS_POINT_S));
		assertFalse(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_W, ICompassPoint.COMPASS_POINT_N));

		assertFalse(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_S, ICompassPoint.COMPASS_POINT_W));
		assertFalse(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_S, ICompassPoint.COMPASS_POINT_E));

		assertFalse(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_N, ICompassPoint.COMPASS_POINT_W));
		assertFalse(RightBeforeLeftRule.isStraight(ICompassPoint.COMPASS_POINT_N, ICompassPoint.COMPASS_POINT_E));

	}
}