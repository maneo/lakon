package org.grejpfrut.evaluation.validators;

import org.grejpfrut.evaluation.AbstractUserTest;
import org.grejpfrut.evaluation.domain.User;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

/**
 * 
 * @author Adam Dudczak
 */
public class LoginFormValidatorTest extends AbstractUserTest {

	protected static final String BEAN_LOGIN_FORM_VALIDATOR = "loginFormValidator";

	private LoginFormValidator validator;

	protected void setUp() throws Exception {
		super.setUp();
		this.validator = (LoginFormValidator) ctx
				.getBean(BEAN_LOGIN_FORM_VALIDATOR);
	}

	public void testCorrectLogin() {

		User user = new User(AbstractUserTest.TEST_EXISTING_USER_EMAIL,
				AbstractUserTest.TEST_EXISTING_USER_PASS);
		user.setType(1);
		Errors errors = new BindException(user, "user");
		validator.validate(user, errors);
		assertFalse(errors.hasErrors());

	}

	public void testEmptyForm() {

		User user = new User("", "");
		user.setType(1);
		Errors errors = new BindException(user, "user");
		validator.validate(user, errors);
		assertTrue(errors.hasErrors());
		assertEquals(errors.getErrorCount(), 4);
		assertEquals(errors.getFieldError("login").getCode(),
				LoginFormValidator.E_LOGIN_REQUIRED);
		assertEquals(errors.getFieldError("pass").getCode(),
				LoginFormValidator.E_PASS_REQUIRED);

	}

	public void testIncorrectPass() {

		User user = new User(AbstractUserTest.TEST_EXISTING_USER_EMAIL, "s");
		user.setType(1);
		Errors errors = new BindException(user, "user");
		validator.validate(user, errors);
		assertTrue(errors.hasErrors());
		assertEquals(errors.getErrorCount(), 1);
		assertEquals(errors.getFieldError("pass").getCode(),
				LoginFormValidator.E_PASS_INCORRECT);

	}

	public void testIncorrectLogin() {

		User user = new User("badlogin", "s");
		user.setType(1);
		Errors errors = new BindException(user, "user");
		validator.validate(user, errors);
		assertTrue(errors.hasErrors());
		assertEquals(errors.getErrorCount(), 2);
		assertEquals(errors.getFieldError("login").getCode(),
				LoginFormValidator.E_LOGIN_INCORRECT_FORMAT);
	}
	
	public void testInactiveUser() {

		User user = new User(AbstractUserTest.TEST_EXISTING_USER_EMAIL,
				AbstractUserTest.TEST_EXISTING_USER_PASS);
		user.setType(0);
		Errors errors = new BindException(user, "user");
		validator.validate(user, errors);
		assertFalse(errors.hasErrors());
	}
	

}
