package org.grejpfrut.evaluation.validators;

import org.grejpfrut.evaluation.dao.UsersManager;
import org.grejpfrut.evaluation.domain.User;
import org.grejpfrut.evaluation.utils.EmailValidationTool;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class LoginFormValidator implements Validator {

	private static final String E_LOGIN_INACTIVE_ACCOUNT = "login.inactive.account";

	public static final String E_PASS_REQUIRED = "pass.required";

	public static final String E_LOGIN_REQUIRED = "login.required";

	public static final String E_LOGIN_INCORRECT_FORMAT = "login.incorrect.format";

	public static final String E_PASS_INCORRECT = "pass.incorrect";

	protected UsersManager usersManager;

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(User.class);
	}

	public void validate(Object command, Errors errors) {

		User user = (User) command;

		validateUser(errors, user.getLogin());

		User validUser = this.usersManager.getUser(user.getLogin());
		if (validUser == null ) {
			errors.rejectValue("login", E_PASS_INCORRECT);
			return;
		}
		else if ( validUser.getType() == 0 )
		{
			errors.rejectValue("login", E_LOGIN_INACTIVE_ACCOUNT);
			return;
		}
		if (!validUser.getPass().equals(user.getPass()))
			errors.rejectValue("pass", E_PASS_INCORRECT);

	}

	protected void validateUser(Errors errors, String login) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login",
				E_LOGIN_REQUIRED);
		if (!EmailValidationTool.validate(login))
			errors.rejectValue("login", E_LOGIN_INCORRECT_FORMAT);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pass",
				E_PASS_REQUIRED);
	}

}
