package org.grejpfrut.evaluation.dao;

import java.util.List;

import org.grejpfrut.evaluation.domain.Text;

/**
 * Basic DAO interface for managing {@link Text}s.
 * 
 * @author Adam Dudczak
 *
 */
public interface TextsManager {

	/**
	 * Gets {@link Text} by given id. 
	 * @param id - id of desired text.
	 * @return {@link Text}
	 */
	public Text getText(int id);

	
	/**
	 * Saves/updates text, this method should create 
	 * new {@link Text} object from given params.
	 * @param text - content of article.
	 * @param title - title of article.
	 */
	public void saveText(String text, String title);
	
	/**
	 * Saves text.
	 * @param art - {@link Text} object.
	 */
	public void saveText(Text art);

	/**
	 * Deletes {@link Text} with given id.
	 * @param id
	 */
	public void deleteText(int id);
	
	/**
	 * @return Gets all {@link Text} stored in database.
	 */
	public List<Text> getTexts();

	/**
	 * Gets all {@link Text}s stored in db except those on exceptThisIds list. 
	 * @param exceptThisIds - exceptions
	 * @return List of {@link Text} objects.
	 */
	public List<Text> getTexts(Integer[] exceptThisIds);
	
	/**
	 * @return Gets number of text defined in system.
	 */
	public int countTexts();
	


}
