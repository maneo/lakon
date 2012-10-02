package org.grejpfrut.evaluation.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.dao.EvaluationsManager;
import org.grejpfrut.evaluation.dao.TextsManager;
import org.grejpfrut.evaluation.domain.Text;
import org.grejpfrut.evaluation.domain.User;
import org.grejpfrut.evaluation.utils.UserSessionManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * This controller checks
 * 
 * @author Adam Dudczak
 */
public class SubmitEvaluationController implements Controller {

	/**
	 * Template variable which indicates that there are no texts in db.
	 */
	private static final String T_NO_TEXTS_DEFINED = "zeroTextsInSystem";

	/**
	 * Template variable which indicates whether to show a link to another
	 * evaluation.
	 */
	private static final String T_SHOW_MORE_LINK = "showMoreLink";

	private EvaluationsManager evaluationsManager;

	private TextsManager textsManager;

	public void setTextsManager(TextsManager textsManager) {
		this.textsManager = textsManager;
	}

	public void setEvaluationsManager(EvaluationsManager evaluationsManager) {
		this.evaluationsManager = evaluationsManager;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map model = new HashMap();
		User user = UserSessionManager.getUser(request);
		List<Text> texts = this.evaluationsManager.getTexts(user);
		if (texts.size() > 0)
			model.put(T_SHOW_MORE_LINK, Boolean.TRUE);
		if (this.textsManager.countTexts() == 0)
			model.put(T_NO_TEXTS_DEFINED, Boolean.TRUE);
		return new ModelAndView("submit-eval", "model", model);
	}

}
