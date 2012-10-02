package org.grejpfrut.evaluation.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.controllers.cmds.ActivateCommand;
import org.grejpfrut.evaluation.dao.UsersManager;
import org.grejpfrut.evaluation.domain.User;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class ActivateController extends AbstractCommandController {

	private UsersManager usersManager;

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

	public ActivateController() {
		setCommandClass(ActivateCommand.class);
	}

	@Override
	protected ModelAndView handle(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception {

		ActivateCommand ac = (ActivateCommand) command;

		if (ac.getUser() == null)
			return new ModelAndView("activate", "success", Boolean.FALSE);

		int hashcode = ac.getUser().hashCode();
		if (hashcode != ac.getId())
			return new ModelAndView("activate", "success", Boolean.FALSE);

		User user = this.usersManager.getUser(ac.getUser());
		if (user == null)
			return new ModelAndView("activate", "success", Boolean.FALSE);
		user.setType(User.NORMAL_USER);
		this.usersManager.saveUser(user);

		return new ModelAndView("activate", "success", Boolean.TRUE);
	}

}
