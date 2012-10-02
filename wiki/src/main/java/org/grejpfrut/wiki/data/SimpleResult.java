package org.grejpfrut.wiki.data;

import java.util.ArrayList;
import java.util.List;

import org.grejpfrut.wiki.utils.TermScore;
/**
 * Class represents simple results.
 * @author Adam Dudczak
 *
 */
public class SimpleResult {
	
	
	private final List<TermScore> keywords ;
	
	private final List<String> titles ; 
	
	public SimpleResult(List<TermScore> keywords, List<String> titles) {
		this.keywords = new ArrayList<TermScore>(keywords);
		this.titles = new ArrayList<String>(titles);
	}
	
	/**
	 * Simple termscores getter.
	 * @return array of of TermScores.
	 */
	public TermScore[] getKeywords(){
		return this.keywords.toArray(new TermScore[1]);
	}
	
	/**
	 * Simple titles getter.
	 * @return Array of String.
	 */
	public String[] getTitles(){
		return this.titles.toArray(new String[1]);
	}
	
	

}
