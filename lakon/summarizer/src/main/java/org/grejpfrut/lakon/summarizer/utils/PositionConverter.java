package org.grejpfrut.lakon.summarizer.utils;

import java.util.ArrayList;
import java.util.List;

import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * This class converts sentence position from (paragraph, sentence) to
 * (sentence) coordinates in {@link Article}.
 * 
 * @author Adam Dudczak
 */
public class PositionConverter {

	private final Article art;

	private List<Integer> offsets; 

	private List<Paragraph> paragraphs;
	
	public PositionConverter(Article art) {

		this.art = art;
		this.paragraphs = art.getParagraphs();
		this.offsets  = getParagraphOffsets();

	}

	public int convert(int paragraph, int sentence){
		
		return this.offsets.get(paragraph) + sentence;
		
	}
	
	public List<Integer> convert(String[] coords) {

		List<Integer> global = new ArrayList<Integer>();
		for (String coord : coords) {
			global.add(convert(coord));
		}
		return global;
	}

	public Integer convert(String coord){
		String[] splitted = coord.split("_");
		int para = Integer.parseInt(splitted[0])-1;
		int sentence = Integer.parseInt(splitted[1])-1;
		return offsets.get(para) + sentence;
	}
	
	public List<Integer> getParagraphOffsets() {

		List<Integer> parasLength = new ArrayList<Integer>();
		int prevParaStart = 0;
		for (int i = 0; i < paragraphs.size(); i++) {
			parasLength.add(prevParaStart);
			prevParaStart += paragraphs.get(i).getSentences().size();
		}
		return parasLength;

	}
}
