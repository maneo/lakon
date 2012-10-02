package org.grejpfrut.evaluation.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.dao.TextsManager;
import org.grejpfrut.evaluation.domain.Text;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controls form where we can add a text or modify existing one.
 * 
 * @author Adam Dudczak
 * 
 */
public class AddTextFormController extends SimpleFormController {

	private TextsManager textsManager;

	public AddTextFormController() {
		setCommandClass(Text.class);
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception {

		Text art = (Text) command;
		logger.info("adding new article");
		this.textsManager.saveText(art);
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	public void setTextsManager(TextsManager textsManager) {
		this.textsManager = textsManager;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest req) throws Exception {

		final String idString = req.getParameter("id");
		if (idString != null) {
			int id = Integer.parseInt(idString);
			Text ar = this.textsManager.getText(id); 
			return ar;
		}
		return new Text();

	}

}
