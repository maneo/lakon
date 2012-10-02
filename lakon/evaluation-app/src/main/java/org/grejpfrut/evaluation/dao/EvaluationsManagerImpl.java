package org.grejpfrut.evaluation.dao;

import java.util.List;

import org.grejpfrut.evaluation.domain.Evaluation;
import org.grejpfrut.evaluation.domain.Text;
import org.grejpfrut.evaluation.domain.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Simple DAO for accessing {@link Evaluation}s.
 * 
 * @author Adam Dudczak
 * 
 */
public class EvaluationsManagerImpl extends HibernateDaoSupport implements
		EvaluationsManager {

	private TextsManager textsManager;

	public void setTextsManager(TextsManager textsManager) {
		this.textsManager = textsManager;
	}

	/**
	 * Gets list of all evaluations.
	 */
	public List<Evaluation> getEvaluations() {
		return getHibernateTemplate().find("from Evaluation");
	}

	/**
	 * Saves given evaluation and increase number of evaluations for evaluated
	 * text.
	 */
	public void save(Evaluation eval) {

		Text txt = this.textsManager.getText(eval.getText().getId());
		txt.setEvaluationCounr(txt.getEvaluationCounr() + 1);
		this.textsManager.saveText(txt);

		this.getHibernateTemplate().save(eval);
		this.getHibernateTemplate().flush();
	}

	/**
	 * Gets {@link Evaluation} with given id.
	 */
	public Evaluation getEvaluations(int id) {
		List res = this.getHibernateTemplate().find(
				"from Evaluation where id=?", id);
		if (res.size() == 0)
			return null;
		return (Evaluation) res.get(0);
	}

	/**
	 * Gets lists of {@link Text}s which given {@link User} already scored.
	 */
	public List<Text> getTexts(User user) {
		List ids = this.getHibernateTemplate().find(
				"select text from Evaluation where user = ?", user.getId());
		Integer[] idsInt = new Integer[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			Evaluation eval = (Evaluation) ids.get(i);
			idsInt[i] = eval.getText().getId();
		}
		return this.textsManager.getTexts(idsInt);
	}

	/**
	 * Gets {@link Evaluation} for this text.
	 */
	public List<Evaluation> getEvaluationsForText(int textId, int userId) {
		return this.getHibernateTemplate().find(
				"select text from Evaluation where user = " + userId
						+ " and text=" + textId);
	}

}
