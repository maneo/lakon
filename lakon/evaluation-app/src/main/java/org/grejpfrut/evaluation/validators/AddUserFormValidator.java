package org.grejpfrut.evaluation.validators;

import org.grejpfrut.evaluation.domain.User;
import org.springframework.validation.Errors;

/**
 * Validates add user form.
 * 
 * @author Adam Dudczak
 *
 */
public class AddUserFormValidator extends LoginFormValidator  {
	
	public void validate(Object command, Errors errors) {

		User user = (User) command;
		this.validateUser(errors, user.getLogin());

	}

}
