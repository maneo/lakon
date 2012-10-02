package org.grejpfrut.evaluation.controllers;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.grejpfrut.evaluation.controllers.cmds.IdCommand;
import org.grejpfrut.evaluation.dao.EvaluationsManager;
import org.grejpfrut.evaluation.domain.Evaluation;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

/**
 * This controller manages list of performed {@link Evaluation}s.
 * 
 * @author Adam Dudczak
 * 
 */
public class EvaluationsListController extends AbstractCommandController {

	/**
	 * Request parameter indicating whether output should be xml or normal list.
	 */
	private static final String R_AS_XML = "asXml";

	private static final String EXPORT_CONTENT_TYPE = "text/xml; charset=UTF-8";

	private static final String EXPORT_TEMPLATE_NAME = "evals-export.vm";

	/**
	 * Template variable with list of performed {@link Evaluation}s. 
	 */
	private static final String T_EVALS = "evals";
	
	private EvaluationsManager evaluationsManager;
	
	private VelocityEngine velocityEngine;

	public EvaluationsListController() {
		setCommandClass(IdCommand.class);
	}
	
	@Override
	protected ModelAndView handle(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception {

		final String asXml = req.getParameter(R_AS_XML);
		Map model = new HashMap();
		model.put(T_EVALS, this.evaluationsManager.getEvaluations());
		
		if ( Boolean.parseBoolean(asXml) ){
			res.setContentType(EXPORT_CONTENT_TYPE);
			Writer writer = res.getWriter();
			String text = VelocityEngineUtils.mergeTemplateIntoString(
					velocityEngine, EXPORT_TEMPLATE_NAME, model);
			writer.write(text);
			writer.close();
			return null; 
		}
		
		return new ModelAndView("evaluations-list", "model", model);
		
	}

	public void setEvaluationsManager(EvaluationsManager evaluationsManager) {
		this.evaluationsManager = evaluationsManager;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
}
