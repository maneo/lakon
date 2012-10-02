package org.grejpfrut.lakon.summarizer.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.grejpfrut.tiller.entities.Sentence;

/**
 * This class holds data about results of quality report. 
 * Calculates COVERAGE,RECALL, PRECISION.
 * 
 * @author Adam Dudczak
 */
public class ReportEntry implements Comparable<ReportEntry> {
	
	/**
	 * This defines three measures used in summaries evaluation,
	 * @author Adam Dudczak
	 */
	public enum Mesaure { COVERAGE, RECALL, PRECISION }

	/**
	 * Article id for which this evaluation was performed.
	 */
	private final int articleId;

	/**
	 * Each method creates summary (as a list of sentences). This is map of 
	 * method names to their summaries.
	 */
	private Map<String, List<Sentence>> resultSummaries = new HashMap<String, List<Sentence>>();

	/**
	 * This holds scores of measures for all investigated methods.
	 */
	private Map<Mesaure, Map<String,Double>> scores = new HashMap<Mesaure, Map<String, Double>>();
	
	/**
	 * Length of summary.
	 */
	private int length;

	/**
	 * @param articleId - id of investigated article.
	 * @param length - summary length fot this article.
	 */
	public ReportEntry(int articleId, int length) {
		
		this.articleId = articleId;
		this.length = length;
		
		scores.put(Mesaure.COVERAGE, new HashMap<String, Double>());
		scores.put(Mesaure.RECALL, new HashMap<String, Double>());
		scores.put(Mesaure.PRECISION, new HashMap<String, Double>());
		
	}

	/**
	 * @return Gets summary lenght for this report (and article).
	 */
	public int getSummaryLength() {
		return this.length;
	}

	/**
	 * @return Gets article id.
	 */
	public int getId() {
		return this.articleId;
	}

	/**
	 * Puts summary for given method into this report.
	 * @param methodName - name of investigated method.
	 * @param summary - results for this method.
	 */
	public void put(String methodName, List<Sentence> summary) {
		this.resultSummaries.put(methodName, summary);
	}

	/**
	 * @param mesaure - desired {@link Mesaure} type. 
	 * @param methodName - desired method  name.
	 * @return Gets score for given method name, and 
	 * particular measure type.
	 */
	public double getScore(Mesaure mesaure, String methodName) {
		return this.scores.get(mesaure).get(methodName);
	}

	/**
	 * Calculates coverage for given method name, using given 
	 * summary (list of sentences).
	 * @param methodName
	 * @param topSentences
	 * @return coverage for given method and summary.
	 */
	public double calculateCoverage(String methodName,
			List<Sentence> topSentences) {

		List<Sentence> summary = this.resultSummaries.get(methodName);
		if (summary == null)
			throw new RuntimeException("No summary for " + methodName
					+ " in report entry");

		List<Sentence> coverage = new ArrayList<Sentence>(topSentences);
		coverage.retainAll(summary);
		double summaryLength = summary.size();
		double score = coverage.size() / summaryLength;
		scores.get(Mesaure.COVERAGE).put(methodName, score);
		return score;
	}

	/**
	 * Calculates recall for given method name, using given 
	 * summary (list of sentences). 
	 * @param methodName
	 * @param relevantSentences
	 * @return recall for given method and summary
	 */
	public double calculateRecall(String methodName, List<Sentence> relevantSentences){
		
		List<Sentence> summary = this.resultSummaries.get(methodName);
		if (summary == null)
			throw new RuntimeException("No summary for " + methodName
					+ " in report entry");
		
		List<Sentence> relevantInSummary  = new ArrayList<Sentence>(relevantSentences);
		relevantInSummary.retainAll(summary);

		double relevantSize = relevantSentences.size();
		double recall = relevantInSummary.size() / relevantSize;
		scores.get(Mesaure.RECALL).put(methodName, recall);
		return recall;
		
	}
	
	/**
	 * Calculates precision for given method name, using given 
	 * summary (list of sentences). 
	 * @param methodName
	 * @param relevantSentences
	 * @return precision for given method and summary
	 */
	public double calculatePrecision(String methodName, List<Sentence> relevantSentences){
		
		List<Sentence> summary = this.resultSummaries.get(methodName);
		if (summary == null)
			throw new RuntimeException("No summary for " + methodName
					+ " in report entry");
		
		List<Sentence> relevantInSummary  = new ArrayList<Sentence>(relevantSentences);
		relevantInSummary.retainAll(summary);

		double summaryLength = summary.size();
		double precision = relevantInSummary.size() / summaryLength;
		scores.get(Mesaure.PRECISION).put(methodName, precision);
		return precision;
		
	}

	/**
	 * @return gets summaries for all methods.
	 */
	public Map<String, List<Sentence>> getSummaries() {
		return this.resultSummaries;
	}

	/**
	 * @return gets names investigated of methods.
	 */
	public List<String> getMethodNames() {
		return new ArrayList<String>(this.resultSummaries.keySet());
	}

	/**
	 * Sets value of given {@link Mesaure} for given method name.
	 * @param methodName
	 * @param mes
	 * @param value
	 */
	public void setValue(String methodName, Mesaure mes, double value){
		scores.get(mes).put(methodName, value);
	}

	/**
	 * Allows for sorting report entries according to article id.
	 */
	public int compareTo(ReportEntry o) {
		if (o.getId() > this.getId())
			return -1;
		else if (o.getId() == this.getId())
			return 0;
		return 1;
	}

}
