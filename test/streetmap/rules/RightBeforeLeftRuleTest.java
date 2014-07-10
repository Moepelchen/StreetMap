package streetmap.rules;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class RightBeforeLeftRuleTest
{

	@Test
	public void testIsLeftTurn()
	{
		assertTrue(RightBeforeLeftRule.isLeftTurn("N", "E"));
		assertTrue(RightBeforeLeftRule.isLeftTurn("W", "N"));
		assertTrue(RightBeforeLeftRule.isLeftTurn("S", "W"));
		assertTrue(RightBeforeLeftRule.isLeftTurn("E", "S"));

		assertFalse(RightBeforeLeftRule.isLeftTurn("E", "N"));
		assertFalse(RightBeforeLeftRule.isLeftTurn("N", "W"));
		assertFalse(RightBeforeLeftRule.isLeftTurn("W", "S"));
		assertFalse(RightBeforeLeftRule.isLeftTurn("S", "E"));

		assertFalse(RightBeforeLeftRule.isLeftTurn("S", "N"));
		assertFalse(RightBeforeLeftRule.isLeftTurn("E", "W"));
		assertFalse(RightBeforeLeftRule.isLeftTurn("N", "S"));
		assertFalse(RightBeforeLeftRule.isLeftTurn("W", "E"));
	}

	@Test
	public void testIsRightTurn()
	{
		assertFalse(RightBeforeLeftRule.isRightTurn("N", "E"));
		assertFalse(RightBeforeLeftRule.isRightTurn("W", "N"));
		assertFalse(RightBeforeLeftRule.isRightTurn("S", "W"));
		assertFalse(RightBeforeLeftRule.isRightTurn("E", "S"));

		assertTrue(RightBeforeLeftRule.isRightTurn("E", "N"));
		assertTrue(RightBeforeLeftRule.isRightTurn("N", "W"));
		assertTrue(RightBeforeLeftRule.isRightTurn("W", "S"));
		assertTrue(RightBeforeLeftRule.isRightTurn("S", "E"));

		assertFalse(RightBeforeLeftRule.isRightTurn("S", "N"));
		assertFalse(RightBeforeLeftRule.isRightTurn("E", "W"));
		assertFalse(RightBeforeLeftRule.isRightTurn("N", "S"));
		assertFalse(RightBeforeLeftRule.isRightTurn("W", "E"));
	}

	@Test
	public void testIsStraight()
	{
		assertTrue(RightBeforeLeftRule.isStraight("N", "S"));
		assertTrue(RightBeforeLeftRule.isStraight("S", "N"));
		assertTrue(RightBeforeLeftRule.isStraight("W", "E"));
		assertTrue(RightBeforeLeftRule.isStraight("E", "W"));

		assertFalse(RightBeforeLeftRule.isStraight("E", "S"));
		assertFalse(RightBeforeLeftRule.isStraight("E", "N"));

		assertFalse(RightBeforeLeftRule.isStraight("W", "S"));
		assertFalse(RightBeforeLeftRule.isStraight("W", "N"));

		assertFalse(RightBeforeLeftRule.isStraight("S", "W"));
		assertFalse(RightBeforeLeftRule.isStraight("S", "E"));

		assertFalse(RightBeforeLeftRule.isStraight("N", "W"));
		assertFalse(RightBeforeLeftRule.isStraight("N", "E"));

	}
}