package org.grejpfrut.evaluation.dao;

import java.util.List;

import org.grejpfrut.evaluation.domain.Evaluation;
import org.grejpfrut.evaluation.domain.Text;
import org.grejpfrut.evaluation.domain.User;
import org.grejpfrut.tiller.entities.Article;

/**
 * Basic DAO for {@link Evaluation} object.
 * 
 * @author Adam Dudczak
 * 
 */
public interface EvaluationsManager {

	public List<Evaluation> getEvaluations();

	public Evaluation getEvaluations(int id);

	public void save(Evaluation eval);

	/**
	 * This method gets all {@link Text} which given {@link User} didn't
	 * evaluated so far.
	 * 
	 * @param user - current {@link User}.
	 * @return List of {@link Text} ids.
	 */
	public List<Text> getTexts(User user);
	
	
	/**
	 * Gets evaluations for given {@link Article} performed by given
	 * {@link User}. 
	 * @param textId - article id.
	 * @param userId - performed by this user.
	 * @return list of evaluations.
	 */
	public List<Evaluation> getEvaluationsForText(int textId, int userId);


}
