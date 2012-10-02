package org.grejpfrut.evaluation.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.dao.UsersManager;
import org.grejpfrut.evaluation.domain.User;
import org.grejpfrut.evaluation.utils.ContentBean;
import org.grejpfrut.evaluation.utils.UserSessionManager;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * This controls login form.
 * 
 * @author Adam Dudczak
 * 
 */
public class LoginController extends SimpleFormController {

	/**
	 * Template variable with text of introduction.
	 */
	private static final String T_INTRODUCTION = "introduction";

	/**
	 * If user tried access page which he doesn't have access this should set.
	 */
	private static final String T_PRIVILEGES_ERROR = "privilegesError";

	/**
	 * Template variable for resolving is user logged in.
	 */
	private static final String T_LOGGED_IN = "loggedIn";

	/**
	 * Template variable with logged user.
	 */
	private static final String T_USER = "user";

	private UsersManager usersManager;
	
	private ContentBean contentBean;

	public LoginController() {
		setCommandClass(User.class);
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		User log = (User) command;
		logger.info("user " + log.getLogin() + " logged in");

		User user = this.usersManager.getUser(log.getLogin());
		UserSessionManager.setUser(request, user);

		return new ModelAndView(new RedirectView(getSuccessView()));

	}

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors, Map model)
			throws Exception {

		User user = UserSessionManager.getUser(request);
		if (model == null)
			model = new HashMap();
		if (request.getAttribute(ErrorController.ERROR_CODE_REQUEST_ATTRIBUTE) != null)
			model.put(T_PRIVILEGES_ERROR, Boolean.TRUE);
		if (user != null) {
			model.put(T_USER, user);
			model.put(T_LOGGED_IN, Boolean.TRUE);
		}
		model.put(T_INTRODUCTION, contentBean.get(T_INTRODUCTION));
		model.put("selection", contentBean.get("selection"));
		model.put("register", contentBean.get("register"));
		return super.showForm(request, response, errors, model);
	}

	public void setContentBean(ContentBean contentBean) {
		this.contentBean = contentBean;
	}

}
