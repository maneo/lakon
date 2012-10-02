package org.grejpfrut.evaluation.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.utils.ContentBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Simple page displaying project's description. Text of project description is defined
 * in messages.properties.utf8 file.
 *  
 * @author Adam Dudczak
 *
 */
public class ProjectInfoController implements Controller {
	
	/**
	 * Template variable with text displayed at project info page.
	 */
	private static final String T_ABOUT = "about";
	
	private ContentBean contentBean;

	public void setContentBean(ContentBean contentBean) {
		this.contentBean = contentBean;
	}

	public ModelAndView handleRequest(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		Map model = new HashMap();
		model.put(T_ABOUT, contentBean.get(T_ABOUT));
		return new ModelAndView("project-info", "model", model);
	}

}
