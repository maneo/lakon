package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Gets number of summaries created by users for given text 
 * @author Adam Dudczak
 */
public class NumberOfEvalsTask extends EvaluationTask {

	public NumberOfEvalsTask(Map values) {
		super(values);
	}

	@Override
	public String execute(int articleId) {
		
		Map<Integer, Integer> evaluations =  parser.getEvaluationsSummary();
		StringBuffer sb = new StringBuffer();
		for (Entry<Integer, Integer> entry : evaluations.entrySet()) {
			sb.append(entry.getKey() + "\t" + entry.getValue()+"\n");
		}
		return sb.toString();

	}

}
