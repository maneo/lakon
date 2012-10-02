package org.grejpfrut.evaluation.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.controllers.cmds.IdCommand;
import org.grejpfrut.evaluation.dao.TextsManager;
import org.grejpfrut.evaluation.domain.Text;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

/**
 * Gets list of {@link Text} and allows to do simple administrative
 * operations
 * 
 * @author Adam Dudczak
 * 
 */
public class TextsListController extends AbstractCommandController {

	/**
	 * Template variable with list of articles.
	 */
	public static final String T_TEXTS = "texts";
	
	private TextsManager textsManager;

	public TextsListController() {
		setCommandClass(IdCommand.class);
	}

	@Override
	protected ModelAndView handle(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception {

		IdCommand idc = (IdCommand) command;
		if (IdCommand.DELETE_ACTION.equals(idc.getAction())) {
			this.textsManager.deleteText(idc.getIdAsInt());
		}
		Map model = new HashMap();
		model.put(T_TEXTS, this.textsManager.getTexts());
		return new ModelAndView("texts-list","model", model);
	}

	public void setTextsManager(TextsManager textsManager) {
		this.textsManager = textsManager;
	}

}
