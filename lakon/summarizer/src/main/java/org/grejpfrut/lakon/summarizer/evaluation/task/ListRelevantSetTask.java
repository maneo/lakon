package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;

import org.grejpfrut.lakon.summarizer.evaluation.Evaluation;
import org.grejpfrut.lakon.summarizer.evaluation.SummaryEvaluation;
import org.grejpfrut.lakon.summarizer.evaluation.SummaryEvaluation.Relevance;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * Prints a few things:
 * <ul>
 *  <li>number of sentences chosen by participants in compare to number of sentences in text,</li>
 *  <li>number of sentence chosen by participants more than average number of times,</li>
 *  <li>text of chosen sentences</li>
 *  <li>position of chosen sentences in paragraph</li>
 * </ul>
 * 
 * @author Adam Dudczak
 */
public class ListRelevantSetTask extends EvaluationTask {

	private enum ReportType {
		TOP_RELEVANTS_WITH_TEXT, TOP_RELEVANTS_ONLY
	};

	public ListRelevantSetTask(Map values) {
		super(values);
	}

	@Override
	public String execute(int artId) {

		Article art = this.articles.get(artId);
		List<Evaluation> evalsForText = parser.getEvaluationsForText(artId);

		StringBuffer logger = new StringBuffer();
		SummaryEvaluation se = new SummaryEvaluation(art, evalsForText);
		logger.append("\n\nid:\t" + artId + "\n");
//		logger.append("Relevant sentences set size :\t"
//			+ se.getRelevantSentences(Relevance.ALL).size() + "\n");

		int relevantSetSize = se.getRelevantSentences(Relevance.ALL).size();
		int summaryLength = summariesLength.get(artId);

//		logger.append("(Filtered by average score):\t" + relevantSetSize + "\n");
//		logger.append("Number of sentence in article:\t"
//				+ art.getSentences().size() + "\n");
//		logger.append("Summary length:\t"+summaryLength+ "\n\n");
		
		return prepareResults(art, logger, se, relevantSetSize, ReportType.TOP_RELEVANTS_ONLY);
	}

	/**
	 * This one prints top sentences and their positions in paragraphs Only
	 * sentences which have been chosen more then average number of times are
	 * listed.
	 * 
	 * @param art
	 * @param logger
	 * @param se
	 * @param howMany
	 * @return
	 */
	public String prepareResults(Article art, StringBuffer logger,
			SummaryEvaluation se, int howMany, ReportType type) {
		List<Sentence> senteces = art.getSentences();
		SortedMap<Integer, Map<Integer, String>> ranking = se
				.getSentenceRanking();
		for (Entry<Integer, Map<Integer, String>> entry : ranking.entrySet()) {

			for (Entry<Integer, String> sentNo : entry.getValue().entrySet()) {
				if (type == ReportType.TOP_RELEVANTS_ONLY) {
					String[] splitted = sentNo.getValue().split("_");
//					logger.append(entry.getKey() + "\t" + splitted[1] + "\n");
					logger.append(sentNo.getKey()+"\t"+ entry.getKey() + "\n");
				} else {
					 logger.append("(" + entry.getKey() + ") [" +
					 sentNo.getValue() +"] "
					 + senteces.get(sentNo.getKey()) + "\n");
				}
				if (--howMany == 0)
					break;
			}
			if (howMany == 0)
				break;
		}
		return logger.toString();
	}

}
