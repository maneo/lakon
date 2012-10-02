package org.grejpfrut.evaluation.utils;

import junit.framework.TestCase;

/**
 * 
 * @author Adam Dudczak
 */
public class EmailValidationToolTest extends TestCase {

	private final static String input1 = "a.d@tlen.pl";

	private final static String input2 = "adam@tlen.costam.com";

	private final static String input3 = "adam_19@tlen.pl";

	private final static String input4 = "adam.andrzej.dudczak@gmail.com";

	private final static String badinput1 = "adam-andrzej.dudczak@gmail.com";

	private final static String badinput2 = "adamgmail.com";

	public void testValidation() {

		assertTrue(EmailValidationTool.validate(input1));
		assertTrue(EmailValidationTool.validate(input2));
		assertTrue(EmailValidationTool.validate(input3));
		assertTrue(EmailValidationTool.validate(input4));
		assertTrue(EmailValidationTool.validate(badinput1));
		assertFalse(EmailValidationTool.validate(badinput2));

	}

}
