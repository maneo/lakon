package org.grejpfrut.evaluation.validators;

import org.grejpfrut.evaluation.controllers.cmds.Register;
import org.grejpfrut.evaluation.dao.UsersManager;
import org.grejpfrut.evaluation.domain.User;
import org.grejpfrut.evaluation.utils.EmailValidationTool;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates registration form.
 * 
 * @author Adam Dudczak
 * 
 */
public class RegistrationFormValidator implements Validator {

	public static final String E_REPASS_REQUIRED = "repass.required";

	public static final String E_PASS_REQUIRED = "pass.required";

	public static final String E_LOGIN_USER_EXISTS = "login.user.exists";

	public static final String E_REPASS_INCORRECT = "repass.incorrect";

	public static final String E_LOGIN_REQUIRED = "login.required";

	public static final String E_LOGIN_INCORRECT_FORMAT = "login.incorrect.format";

	protected UsersManager usersManager;

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(Register.class);
	}

	public void validate(Object command, Errors errors) {
		Register register = (Register) command;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login",
				E_LOGIN_REQUIRED);
		if (!EmailValidationTool.validate(register.getLogin()))
			errors.rejectValue("login", E_LOGIN_INCORRECT_FORMAT);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pass",
				E_PASS_REQUIRED);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repass",
				E_REPASS_REQUIRED);

		if (!register.getPass().equals(register.getRepass()))
			errors.rejectValue("repass", E_REPASS_INCORRECT);

		User validUser = this.usersManager.getUser(register.getLogin());
		if (validUser != null)
			errors.rejectValue("login", E_LOGIN_USER_EXISTS);

	}

}
