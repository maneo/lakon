package org.grejpfrut.evaluation.utils;

import java.util.regex.Pattern;

/**
 * 
 * @author Adam Dudczak
 */
public class EmailValidationTool {

	private static final String EMAIL_REGEXP = "[a-z0-9]+([_\\.-][a-z0-9]+)*"
			+ "@([a-z0-9]+([\\.-][a-z0-9]+)*)+\\.[a-z]{2,4}";

	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEXP);

	/**
	 * Validates email address.
	 * 
	 * @param email -
	 *            email address.
	 */
	public static synchronized boolean validate(String email) {
		if (email == null)
			return false;
		return EMAIL_PATTERN.matcher(email).matches();
	}

}
