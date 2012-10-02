package org.grejpfrut.evaluation.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.domain.User;
import org.grejpfrut.evaluation.utils.UserSessionManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

/**
 * This is logout action, removes {@link User} object from session.
 * @author Adam Dudczak
 */
public class LogoutController implements Controller {


	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserSessionManager.setUser(request, null);
		return new ModelAndView(new RedirectView("index.html", true));
	}

}
