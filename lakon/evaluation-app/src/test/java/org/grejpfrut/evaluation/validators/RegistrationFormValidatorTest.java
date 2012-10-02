package org.grejpfrut.evaluation.validators;

import org.grejpfrut.evaluation.AbstractUserTest;
import org.grejpfrut.evaluation.controllers.cmds.Register;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

/**
 * 
 * @author Adam Dudczak
 */
public class RegistrationFormValidatorTest extends AbstractUserTest {

	private static final String VALIDATOR_BEAN_NAME = "registrationFormValidator";

	RegistrationFormValidator validator;

	protected void setUp() throws Exception {
		super.setUp();
		this.validator = (RegistrationFormValidator) ctx
				.getBean(VALIDATOR_BEAN_NAME);
	}

	public void testCorrectRegistration() {

		Register reg = new Register("login@login.pl", "pass", "pass");
		Errors errors = new BindException(reg, "register");
		validator.validate(reg, errors);
		assertFalse(errors.hasErrors());

	}

	public void testIncorrectLogin() {

		Register reg = new Register("login", "pass", "pass");
		Errors errors = new BindException(reg, "register");
		validator.validate(reg, errors);
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("login").getCode(),
				RegistrationFormValidator.E_LOGIN_INCORRECT_FORMAT);

	}

	public void testNoRepass() {

		Register reg = new Register("login@login.pl", "pass", "");
		Errors errors = new BindException(reg, "register");
		validator.validate(reg, errors);
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("repass").getCode(),
				RegistrationFormValidator.E_REPASS_REQUIRED);

	}

	public void testIncorrectRepass() {

		Register reg = new Register("login@login.pl", "pass", "psss");
		Errors errors = new BindException(reg, "register");
		validator.validate(reg, errors);
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("repass").getCode(),
				RegistrationFormValidator.E_REPASS_INCORRECT);

	}

	public void testNoPass() {

		Register reg = new Register("login@login.pl", "", "psss");
		Errors errors = new BindException(reg, "register");
		validator.validate(reg, errors);
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("pass").getCode(),
				RegistrationFormValidator.E_PASS_REQUIRED);

	}

	public void testUserExists() {

		Register reg = new Register(TEST_EXISTING_USER_EMAIL, "pass", "pass");
		Errors errors = new BindException(reg, "register");
		validator.validate(reg, errors);
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("login").getCode(),
				RegistrationFormValidator.E_LOGIN_USER_EXISTS);

	}

	public void testAllErrors() {

		Register reg = new Register(TEST_EXISTING_USER_EMAIL, "", "pass");
		Errors errors = new BindException(reg, "register");
		validator.validate(reg, errors);
		assertTrue(errors.hasErrors());
		assertEquals(errors.getErrorCount(), 3);
		assertEquals(errors.getFieldError("pass").getCode(),
				RegistrationFormValidator.E_PASS_REQUIRED);
		assertEquals(errors.getFieldError("repass").getCode(),
				RegistrationFormValidator.E_REPASS_INCORRECT);
		assertEquals(errors.getFieldError("login").getCode(),
				RegistrationFormValidator.E_LOGIN_USER_EXISTS);

	}

}
