package org.grejpfrut.lakon.summarizer.evaluation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.lucene.store.Lock.With;
import org.grejpfrut.lakon.summarizer.utils.PositionConverter;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * This class represents summary evaluation. Allows to get sentence ranking
 * according to how many times each sentences was chosen by users. This class
 * have access to text of article, and all data necessary to evalute summary ({@link Evaluation}
 * have only strings representing chosen sentences).
 * 
 * @author Adam Dudczak
 */
public class SummaryEvaluation {

	/**
	 * {@link RankingType#WITH_PARAGRAPHS} ranking will contain information
	 * about sentence position in paragraph.
	 * {@link RankingType#JUST_SENT_NUMBER} ranking will contain global sentence
	 * number only.
	 * 
	 * @author Adam Dudczak
	 */
	public enum RankingType {
		WITH_PARAGRAPHS, JUST_SENT_NUMBER
	};

	/**
	 * Evaluated article.
	 */
	private final Article article;

	/**
	 * Position converter.
	 */
	private final PositionConverter converter;

	/**
	 * List of sentences chosen by users.
	 */
	private final List<Evaluation> evaluations;

	/**
	 * Sentence number and number of times it was chosen by users.
	 */
	private Map<Integer, Integer> integerScores = null;

	/**
	 * Sentence coordinate (eg. 1_1) and number of times it was chosen by users.
	 */
	private Map<String, Integer> stringScores = null;


	/**
	 * For comparing two integers in descending order.
	 */
	private final static Comparator<Integer> DESCENDING = new Comparator<Integer>() {

		public int compare(Integer o1, Integer o2) {
			return -o1.compareTo(o2);
		}

	};

	/**
	 * If you use {@link Relevance#ALL} all sentences chosen by users will be
	 * used as relevant set. When using {@link Relevance#AVERAGE} only sentences
	 * which was chosen more than average number of times will create relevant
	 * set.
	 * 
	 * @author Adam Dudczak
	 * 
	 */
	public enum Relevance {
		ALL, AVERAGE
	};

	/**
	 * @param article -
	 *            article used in this evaluation.
	 * @param evaluations -
	 *            list of users' evaluations.
	 */
	public SummaryEvaluation(Article article, List<Evaluation> evaluations) {

		this.article = article;
		this.converter = new PositionConverter(article);
		this.evaluations = evaluations;

	}

	/**
	 * @return Gets paragraphs' offsets for this text.
	 */
	public List<Integer> getParagraphOffsets() {
		return this.converter.getParagraphOffsets();
	}

	/**
	 * @param howMany
	 * @return Gets top "howMany" from this article.
	 */
	public List<Sentence> getTopSentences(int howMany) {

		List<Sentence> sentences = this.article.getSentences();
		List<Sentence> result = new ArrayList<Sentence>();

		Map<Integer, List<Integer>> integerRanking = getSimpleSentenceRanking();

		while (howMany > 0) {

			for (List<Integer> entry : integerRanking.values()) {
				for (int item : entry) {
					result.add(sentences.get(item));
					if (--howMany <= 0)
						return result;
				}
			}
		}
		return result;
	}

	/**
	 * @param howMany
	 * @return Gets top howMany sentences positions.
	 */
	public List<Integer> getTopSentencePositions(int howMany) {

		List<Integer> result = new ArrayList<Integer>();

		Map<Integer, List<Integer>> integerRanking = getSimpleSentenceRanking();

		while (howMany > 0) {

			for (List<Integer> entry : integerRanking.values()) {
				for (int item : entry) {
					result.add(item);
					if (--howMany <= 0)
						return result;
				}
			}

		}
		return result;

	}

	/**
	 * Gets sentences ranking based on given {@link Evaluation}s. Ranking holds
	 * information about number of occurences for sentences. Number of times -> [
	 * position -> string_position ]
	 * 
	 * @return Gets sentences ranking based on given {@link Evaluation}s.
	 */
	public SortedMap<Integer, Map<Integer, String>> getSentenceRanking() {

		if (stringScores == null) {
			setScores();
		}

		SortedMap<Integer, Map<Integer, String>> stringRanking 
		   = new TreeMap<Integer, Map<Integer, String>>(DESCENDING);
		for (Entry<String, Integer> entry : this.stringScores.entrySet()) {

			Map<Integer, String> occurences = stringRanking.get(entry
					.getValue());
			if (occurences == null) {
				occurences = new TreeMap<Integer, String>();
				stringRanking.put(entry.getValue(), occurences);
			}
			int position = this.converter.convert(entry.getKey());
			occurences.put(position, entry.getKey());

		}
		return stringRanking;

	}

	/**
	 * Gets sentences ranking based on given {@link Evaluation}s. Ranking holds
	 * information about number of occurences for sentences.
	 * 
	 * @return Gets sentences ranking based on given {@link Evaluation}s.
	 */
	public Map<Integer, List<Integer>> getSimpleSentenceRanking() {

		if (integerScores == null || stringScores == null) {
			setScores();
		}

		Map<Integer, List<Integer>> sentenceRanking = new TreeMap<Integer, List<Integer>>(
				DESCENDING);
		for (Entry<Integer, Integer> entry : this.integerScores.entrySet()) {

			List<Integer> occurences = sentenceRanking.get(entry.getValue());
			if (occurences == null) {
				occurences = new ArrayList<Integer>();
				sentenceRanking.put(entry.getValue(), occurences);
			}
			occurences.add(entry.getKey());

		}
		return sentenceRanking;
	}

	private void setScores() {

		integerScores = new HashMap<Integer, Integer>();
		stringScores = new HashMap<String, Integer>();

		for (Evaluation eval : this.evaluations) {
			String[] chosen = eval.getChosen();
			for (String chosenOne : chosen) {
				Integer counter = stringScores.get(chosenOne);
				int position = this.converter.convert(chosenOne);
				if (counter == null) {
					integerScores.put(position, new Integer(1));
					stringScores.put(chosenOne, new Integer(1));
				} else {
					integerScores.put(position, counter + 1);
					stringScores.put(chosenOne, counter + 1);
				}
			}
		}
	}

	/**
	 * Gets set of relevant sentences for {@link Article} from this
	 * {@link SummaryEvaluation}.
	 * 
	 * @return list of relevant sentennces.
	 */
	public List<Sentence> getRelevantSentences(Relevance setType) {

		List<Sentence> sentences = this.article.getSentences();
		List<Sentence> result = new ArrayList<Sentence>();
		Map<Integer, List<Integer>> sentenceRanking = getSimpleSentenceRanking();

		if (setType == Relevance.ALL) {

			for (List<Integer> entry : sentenceRanking.values()) {
				for (int item : entry) {
					result.add(sentences.get(item));
				}
			}
		} else {
			double average = getAverageOccurences();
			for (Entry<Integer, List<Integer>> entry : sentenceRanking
					.entrySet()) {
				if (entry.getKey() < average)
					continue;
				for (int sentence : entry.getValue()) {
					result.add(sentences.get(sentence));
				}
			}
		}
		return result;
	}

	/**
	 * Calculates average number of occurences fo sentence in users'
	 * evaluations.
	 * 
	 * @return
	 */
	private double getAverageOccurences() {

		double result = 0.0;
		int counter = 0;

		for (Entry<Integer, List<Integer>> entry : this
				.getSimpleSentenceRanking().entrySet()) {

			result += entry.getKey() * entry.getValue().size();
			counter += entry.getValue().size();

		}
		return result / counter;

	}

	/**
	 * @return Gets list of relevant sentences.
	 */
	public List<Integer> getRelevenatSentencesPositions() {
		return new ArrayList<Integer>(this.getSentenceRanking().keySet());
	}

}
