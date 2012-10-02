package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.Map;

import org.grejpfrut.lakon.summarizer.evaluation.EvaluationsParser;
import org.grejpfrut.tiller.entities.Article;

/**
 * This class represents basic task used to analyse 
 * results of summarizer work.
 *  
 * @author Adam Dudczak
 */
public abstract class EvaluationTask {

	public static final String LENGTHS_KEY = "lengths";

	public static final String PARSER_KEY = "parser";

	public static final String ARTICLES_KEY = "articles";
	
	protected Map values;

	/**
	 * text id to summary length for this given article.
	 */
	protected Map<Integer, Integer> summariesLength;

	/**
	 * Evaluation parser.
	 */
	protected EvaluationsParser parser;
	
	/**
	 * Articles used in experiment.
	 */
	protected Map<Integer,Article> articles;
	
	/**
	 * @param values - configuration parameters.
	 */
	public EvaluationTask(Map values){
		parser = (EvaluationsParser) values.get(PARSER_KEY);
		summariesLength = (Map<Integer, Integer>) values.get(LENGTHS_KEY);
		articles = (Map<Integer,Article>) values.get(ARTICLES_KEY);
	}
		
	/**
	 * Executes evaluation task eg. lists all users.
	 * @return results of analysis.
	 */
	public abstract String execute(int articleId);

}
