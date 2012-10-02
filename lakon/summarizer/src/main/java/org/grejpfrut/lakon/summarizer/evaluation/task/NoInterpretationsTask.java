package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.methods.LexicalChainsSummarizer;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.tiller.entities.Article;

/**
 * 
 * @author Adam Dudczak
 */
public class NoInterpretationsTask extends EvaluateSummariesTask {

	protected int noOfInterpretations = 0;

	public NoInterpretationsTask(Map values) {
		super(values);
	}

	@Override
	public String execute(int articleId) {
		
		this.noOfInterpretations = 0;
		StringBuffer sb = new StringBuffer();
		while (noOfInterpretations < 1000) {
			sb.append("number of interpretations :" + noOfInterpretations +"\n");
			sb.append(super.execute(articleId));
			noOfInterpretations += 50;
		}
		return sb.toString();
	}

	@Override
	protected List<Summarizer> getSummarizers(Article art) {

		List<Summarizer> summs = new ArrayList<Summarizer>();

		Settings lc = new LexicalChainsSettings(settings.getMap());
		lc.put(LexicalChainsSettings.P_REPRESENTATIVE_MODE,
				LexicalChainsSettings.Representatives.OFF.toString());
		lc.put(LexicalChainsSettings.P_MAX_NUMBER_OF_INTERPRETATIONS,
				new Integer(noOfInterpretations));
		lc.put(LexicalChainsSettings.P_ONLY_NOUNS, Boolean.TRUE.toString());
		Summarizer chainer = new LexicalChainsSummarizer(lc);
		summs.add(chainer);

		return summs;
	}

}
