package org.grejpfrut.lakon.summarizer.evaluation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class holds basic information about evaluation created by user
 * (holds data about summary created by user during the experiment). This 
 * class is used by {@link EvaluationsParser} later it's replaced by
 * {@link SummaryEvaluation}. 
 * 
 * 
 * @author Adam Dudczak
 */
public class Evaluation {

	/**
	 * User name.
	 */
	private String user;

	/**
	 * Summarized article.
	 */
	private int textId;

	/**
	 * Strings representing chosen sentences.
	 */
	private String[] chosen;

	/**
	 * @return Gets number of sentences in this summary.
	 */
	public int getNumberOfSentences() {
		return this.chosen.length;
	}

	/**
	 * Used for validating of summaries created by users.
	 * 
	 * @return Gets number of number of highest chosen paragraph.
	 */
	public int getLastParagraph() {
		int max = 0;
		for (String sentence : chosen) {
			String[] splitted = sentence.split("_");
			int paraNo = Integer.parseInt(splitted[0]) - 1;
			if (paraNo > max)
				max = paraNo;
		}
		return max;
	}

	/**
	 * @param paranumber -
	 *            paragraph number.
	 * @return List (of numbers) of sentence for given paragraph.
	 * 
	 */
	public List<Integer> getSentencesForPara(int paranumber) {

		List<Integer> sentences = new ArrayList<Integer>();
		for (String sentence : chosen) {
			String[] splitted = sentence.split("_");
			int paraNo = Integer.parseInt(splitted[0]) - 1;
			if (paraNo == paranumber) {
				int sen = Integer.parseInt(splitted[1]) - 1;
				sentences.add(sen);
			}
		}
		return sentences;
	}

	/**
	 * @return Gets list of all paragraphs whose sentences 
	 * were chosen by users.
	 */
	public Set<Integer> getParagraphs() {

		Set<Integer> paragraphs = new HashSet<Integer>();
		for (String sentence : chosen) {
			String[] splitted = sentence.split("_");
			int paraNo = Integer.parseInt(splitted[0]) - 1;
			paragraphs.add(paraNo);
		}
		return paragraphs;

	}

	/**
	 * @return Gets array of strings (eg. 1_2, second sentence from 
	 * first paragraph), each string is representing sentence chosen 
	 * by user. 
	 */
	public String[] getChosen() {
		return chosen;
	}

	/**
	 * @return Gets list of all chosen sentences (see {@link Evaluation#getChosen()}).
	 */
	public List<String> getChosenList() {
		List<String> list = new ArrayList<String>();
		for (String elem : chosen) {
			list.add(elem);
		}
		return list;
	}

	/**
	 * @param chosen - list of strings representing 
	 * coordinates of chosen sentences.
	 */
	public void setChosen(String[] chosen) {
		this.chosen = chosen;
	}

	/**
	 * @return Gets text id.
	 */
	public int getTextId() {
		return textId;
	}

	/**
	 * Sets textId.
	 * @param textId
	 */
	public void setTextId(int textId) {
		this.textId = textId;
	}

	/**
	 * @return Gets user name.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user - user name.
	 */
	public void setUser(String user) {
		this.user = user;
	}

}
