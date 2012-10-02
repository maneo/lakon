package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.grejpfrut.lakon.summarizer.evaluation.Evaluation;
import org.grejpfrut.lakon.summarizer.evaluation.SummaryEvaluation;
import org.grejpfrut.tiller.entities.Article;

/**
 * Prints sentences and number of times it were chosen by users.
 * @author Adam Dudczak
 */
public class RelevanceSetDistributionTask extends EvaluationTask {

	
	public RelevanceSetDistributionTask(Map values) {
		super(values);
	}

	
	@Override
	public String execute(int artId) {
		
		Article art = this.articles.get(artId);
		List<Evaluation> evalsForText = parser.getEvaluationsForText(artId);
		StringBuffer logger = new StringBuffer();

		SummaryEvaluation se = new SummaryEvaluation(art, evalsForText);
		logger.append("\n\n id: " + artId+"\n");
		logger.append(" title: " + art.getTitle()+"\n");

		logger.append("position \t times\n");
		Map<Integer, Integer> distribution = new HashMap<Integer, Integer>();
		for (Entry<Integer, List<Integer>> entry : se.getSimpleSentenceRanking()
				.entrySet()) {
			for (Integer sentNo : entry.getValue()) {
				distribution.put(sentNo, entry.getKey());
			}
		}

		for (int i = 0; i < art.getSentences().size(); i++) {
			if (distribution.get(i) != null)
				logger.append(i + "\t" + distribution.get(i)+"\n");
			else
				logger.append(i + "\t0"+"\n");

		}

		logger.append("\n paragraphs offsets:\n");
		List<Integer> paras = se.getParagraphOffsets();
		for (int i = 0; i < paras.size(); i++) {
			logger.append(i + "\t" + paras.get(i)+"\n");
		}
		return logger.toString();
		
	}


}
