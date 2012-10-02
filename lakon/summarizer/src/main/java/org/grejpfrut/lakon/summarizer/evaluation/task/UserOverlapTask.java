package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.grejpfrut.lakon.summarizer.evaluation.Evaluation;

/**
 * Prepares matrix with users' evaluations overlap, also should calculate
 * standard deviation and average overlap.
 * 
 * @author Adam Dudczak
 */
public class UserOverlapTask extends EvaluationTask {

	/**
	 * @param values
	 */
	public UserOverlapTask(Map values) {
		super(values);
	}

	@Override
	public String execute(int articleId) {

		List<Double> rowSumm = new ArrayList<Double>();
		List<String> users = parser.getUsers(articleId);

		StringBuffer logger = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		sb.append("\n------------------------------------------------------");
		sb.append("\n artid:" + articleId + " summary lenght: "
				+ this.summariesLength.get(articleId) + "\n");

		int counter = 0;
		for (int i = 0; i < users.size(); i++) {
			double rowElements = 0.0;
			Evaluation eval = parser.getEvaluation(articleId, users.get(i));
			for (int e = 0; e < i+1; e++)
				sb.append("\t");
			for (int j = i+1; j < users.size(); j++) {
				List<String> positions = eval.getChosenList();
				Evaluation eval2 = parser
						.getEvaluation(articleId, users.get(j));
				List<String> positions2 = eval2.getChosenList();
				positions.retainAll(positions2);
				sb.append(positions.size() + "\t");
				rowElements += positions.size();
				counter++;
			}
			if (counter != 0)
				rowSumm.add(rowElements);
			sb.append("\n");
		}
		logger.append(sb.toString() + "\n");

		double sum = 0.0;
		for (Double ave : rowSumm) {
			sum += ave.doubleValue();
		}
		logger.append("Average overlap is: " + sum / counter
				/ this.summariesLength.get(articleId) + "\n");
		return logger.toString();

	}

}
