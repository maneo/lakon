package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.List;
import java.util.Map;

import org.grejpfrut.lakon.summarizer.evaluation.EvaluationsParser;

/**
 * Prints emails of all people that took a part in experiment.
 * @author Adam Dudczak
 */
public class ListUsersTask extends EvaluationTask {
	

	
	/**
	 * 
	 * @param values
	 */
	public ListUsersTask(Map values) {
		super(values);
	}

	@Override
	public String execute(int articleId) {
		
		List<String> users = parser.getUsers();
		StringBuffer sb = new StringBuffer();
		for (String user : users) {
			sb.append(user+"\n");
		}		
		return sb.toString();
	}

}
