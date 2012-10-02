package org.grejpfrut.evaluation.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.controllers.cmds.IdCommand;
import org.grejpfrut.evaluation.dao.UsersManager;
import org.grejpfrut.evaluation.domain.User;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

/**
 * This controller manages {@link User}s list.
 * 
 * @author Adam Dudczak
 *
 */
public class UsersListController extends AbstractCommandController {

	/**
	 * Template variable with users list.
	 */
	private static final String T_USERS = "users";
	
	private UsersManager usersManager;

	public UsersListController() {
		setCommandClass(IdCommand.class);
	}

	@Override
	protected ModelAndView handle(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception {
		
		IdCommand idc = (IdCommand)command;
		if ( IdCommand.DELETE_ACTION.equals(idc.getAction()) )
		{
			this.usersManager.deleteUser(idc.getIdAsInt());
		} 
		
		Map model = new HashMap();
		model.put(T_USERS, this.usersManager.getUsers());		
		return new ModelAndView("users-list","model", model);
	}

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

}
