package org.grejpfrut.evaluation.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grejpfrut.evaluation.controllers.cmds.EvaluationCommand;
import org.grejpfrut.evaluation.dao.EvaluationsManager;
import org.grejpfrut.evaluation.dao.TextsManager;
import org.grejpfrut.evaluation.domain.Evaluation;
import org.grejpfrut.evaluation.domain.Text;
import org.grejpfrut.evaluation.domain.User;
import org.grejpfrut.evaluation.utils.UserSessionManager;
import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * This controls summary generation process.
 * 
 * @author Adam Dudczak
 */
public class EvaluationController extends SimpleFormController {

	/**
	 * Template variable with number of sentence which user have to choose.
	 */
	private static final String T_MAX_SENTENCES = "maxSentences";

	/**
	 * Template variable with article, which will be used to create extract.
	 */
	private static final String T_ARTICLE = "article";

	/**
	 * Template variable with article id.
	 */
	private static final String T_ART_ID = "artId";

	private EvaluationsManager evaluationsManager;

	private ArticleBuilder articleFactory;

	private TextsManager textsManager;

	public EvaluationController() {
		setCommandClass(EvaluationCommand.class);
	}

	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors, Map model)
			throws Exception {

		if (model == null)
			model = new HashMap();

		User user = UserSessionManager.getUser(request);
		List texts = this.evaluationsManager.getTexts(user);
		Article art = null;
		int maxSentences = 0;
		if (texts.size() > 0) {
			Text text = (Text) texts.get(0);
			art = this.articleFactory.getArcticle(text.getText(), text
					.getTitle());
			maxSentences = text.getExtractLength();
			model.put(T_ART_ID, new Integer(text.getId()));
			model.put(T_ARTICLE, art);
			model.put(T_MAX_SENTENCES, new Integer(maxSentences));
			return super.showForm(request, response, errors, model);
		}
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception {

		User user = UserSessionManager.getUser(req);

		EvaluationCommand evCmd = (EvaluationCommand) command;
		Evaluation evaluation = new Evaluation();

		evaluation.setSelectedIds(evCmd.getIds());
		evaluation.setUser(user);
		Integer artId = Integer.parseInt(evCmd.getArtId());
		Text text = this.textsManager.getText(artId);
		evaluation.setText(text);

		this.evaluationsManager.save(evaluation);
		logger.info("Evaluation added, by " + user.getLogin()
				+ " for text with id:" + text.getId());

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	public void setEvaluationsManager(EvaluationsManager evaluationsManager) {
		this.evaluationsManager = evaluationsManager;
	}

	public void setArticleFactory(ArticleBuilder articleFactory) {
		this.articleFactory = articleFactory;
	}

	public void setTextsManager(TextsManager textsManager) {
		this.textsManager = textsManager;
	}

}
