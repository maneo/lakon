package org.grejpfrut.evaluation.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.controllers.cmds.Register;
import org.grejpfrut.evaluation.dao.UsersManager;
import org.grejpfrut.evaluation.domain.User;
import org.grejpfrut.evaluation.utils.ActivationMailSender;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;


public class RegistrationFormController extends SimpleFormController {

	private ActivationMailSender activationMailSender;

	private UsersManager usersManager;
	
	public RegistrationFormController() {
		setCommandClass(Register.class);
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception {

		final Register registerRequest = (Register) command;
		User user = new User(registerRequest.getLogin(),registerRequest.getPass());
		usersManager.saveUser(user);
		this.activationMailSender.sendActivation(user);

		return new ModelAndView(new RedirectView(getSuccessView()));

	}

	public void setActivationMailSender(
			ActivationMailSender activationMailSender) {
		this.activationMailSender = activationMailSender;
	}

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

}
