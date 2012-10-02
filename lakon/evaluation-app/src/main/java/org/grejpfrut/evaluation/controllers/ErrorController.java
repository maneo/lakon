package org.grejpfrut.evaluation.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Simple errors handler.
 * 
 * @author Adam Dudczak
 */
public class ErrorController implements Controller {
	

	/**
	 * Template variable with error code for exception.
	 */
	private static final String T_CODE = "code";

	/**
	 * Template variable with url where exception occured.
	 */
	private static final String T_URL = "url";
	
	/**
	 * Template variable with exception.
	 */
	private static final String T_EXCEPTION = "exception";

	public final static String ERROR_REQUEST_URL_ATTRIBUTE = "javax.servlet.error.request_uri";
	
	public final static String ERROR_CODE_REQUEST_ATTRIBUTE = "javax.servlet.error.status_code";
	
	public final static String ERROR_REQUEST_ATTRIBUTE = "javax.servlet.error.exception";

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		Map model = new HashMap();
		model.put(T_URL, req.getAttribute(ERROR_REQUEST_URL_ATTRIBUTE));
		model.put(T_CODE, req.getAttribute(ERROR_CODE_REQUEST_ATTRIBUTE));
		model.put(T_EXCEPTION, req.getAttribute(ERROR_REQUEST_ATTRIBUTE));
		return new ModelAndView("error", "model", model);
	}

}
