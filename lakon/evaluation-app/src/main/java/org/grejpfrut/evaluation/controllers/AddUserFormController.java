package org.grejpfrut.evaluation.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.dao.UsersManager;
import org.grejpfrut.evaluation.domain.User;
import org.grejpfrut.evaluation.utils.ActivationMailSender;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controls form where we can add a user or modify existing one.
 * 
 * @author Adam Dudczak
 * 
 */
public class AddUserFormController extends SimpleFormController {

	private UsersManager usersManager;
	
	private ActivationMailSender activationMailSender; 

	public AddUserFormController() {
		setCommandClass(User.class);
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception {

		User user = (User) command;
		logger.info("adding new user " + user.getLogin());
		this.usersManager.saveUser(user);
		
		if (user.getType() == User.INACTIVE_USER )
			this.activationMailSender.sendActivation(user);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest req) throws Exception {

		final String idString = req.getParameter("id");
		if (idString != null) {
			int id = Integer.parseInt(idString);
			User user =  this.usersManager.getUser(id);
			return user;
		}
		return new User();
		
	}

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

	public void setActivationMailSender(ActivationMailSender activationMailSender) {
		this.activationMailSender = activationMailSender;
	}

}
