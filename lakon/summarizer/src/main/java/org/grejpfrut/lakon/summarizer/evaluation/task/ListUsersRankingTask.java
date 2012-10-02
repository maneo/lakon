package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.Map;
import java.util.Map.Entry;

/**
 * List users ranking.
 * @author Adam Dudczak
 */
public class ListUsersRankingTask extends EvaluationTask {

	public ListUsersRankingTask(Map values) {
		super(values);
	}

	@Override
	public String execute(int articleId) {
		Map<String, Integer> ranking = 	parser.getUsersRanking();
		int between1_3 = 0;
		int between4_6 = 0;
		int between7_9 = 0;
		int _10 = 0;
		
		StringBuffer sb = new StringBuffer();
		for ( Entry<String, Integer> entry : ranking.entrySet() ){
			int value = entry.getValue(); 
			if ( ( value >= 1) && (value <= 3) ) between1_3++;
			else if ( ( value >= 4) && (value <= 6) ) between4_6++;
			else if ( ( value >= 7) && (value <= 9) ) between7_9++;
			else _10++;
			sb.append(entry.getKey()+"\t"+entry.getValue()+"\n");
		}
		sb.append("\n 1 -- 3&"+between1_3+"\\ \n 4 -- 6&"+between4_6+"\\ \n 7 -- 9& "+between7_9+" \\ \n 10&"+_10+"\\");
		
		return sb.toString();
	}

}
