package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.List;
import java.util.Map;

import org.grejpfrut.lakon.summarizer.evaluation.Evaluation;
import org.grejpfrut.lakon.summarizer.utils.PositionConverter;
import org.grejpfrut.tiller.entities.Article;

/**
 * Prints numbers of sentences chosen by users for given article.
 * 
 * @author Adam Dudczak
 */
public class EvaluationsForArticleTask extends EvaluationTask{
	


	public EvaluationsForArticleTask(Map values) {
		super(values);
	}

	@Override
	public String execute(int artId) {
		
		StringBuffer sb = new StringBuffer();
		PositionConverter convert = new PositionConverter(articles.get(artId));
		sb.append("\n\n artId : " + artId+"\n");
		List<String> users = parser.getUsers();
		for (String user : users) {
			Evaluation eval = parser.getEvaluation(artId, user);
			if ( eval == null ) continue;
			List<Integer> positions = convert.convert(eval.getChosen());
			StringBuffer sub = new StringBuffer();
			for (Integer pos : positions)
				sub.append(pos + " ");
			sb.append(sub.toString()+"\n");
		}
		return sb.toString();

	}

}
