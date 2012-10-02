package org.grejpfrut.lakon.summarizer.methods;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.SummarizerBase;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings.LocationMode;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * Basic implementation of {@link Summarizer} interface. Works in two modes:
 * first n sentences, first sentences from paragraphs
 * 
 * @author Adam Dudczak
 */
public class LocationBasedSummarizer extends SummarizerBase {

	private final static LocationMode DEFAULT_LOCATION_MODE = LocationMode.FIRST_IN_PARAGRAPHS;

	private LocationMode mode = DEFAULT_LOCATION_MODE;

	public LocationBasedSummarizer(Settings settings) {
		super(settings);

		String location = (String) settings
				.get(LocationSettings.P_LOCATION_MODE);
		if (location != null)
			mode = LocationMode.valueOf(location);

	}

	@Override
	protected List<Sentence> prepareSummary(Article article) {
		List<Sentence> sentences = article.getSentences();

		if (LocationMode.FIRST == mode)
			return sentences.subList(0, super.length);
		else
			return getFirstInParagraph(article, super.length);
	}

	/**
	 * Gets first sentences in paragraphs from given {@link Article}.
	 * 
	 * @param article -
	 *            article to summarize.
	 * @param length -
	 *            length of summary.
	 * @return summary as a List<Sentence>.
	 */
	private List<Sentence> getFirstInParagraph(Article article, int length) {

		List<Sentence> result = new ArrayList<Sentence>();
		List<Paragraph> paras = article.getParagraphs();

		int toTake = length;

		while (toTake > 0) {

			Iterator<Paragraph> iter = paras.iterator();
			while (iter.hasNext()) {
				Paragraph para = iter.next();
				Sentence sent = getSentence(para.getSentences());
				if (sent != null) {
					result.add(sent);
					if ((--toTake) == 0)
						break;
				}
				if (para.getSentences().size() == 0)
					iter.remove();
			}
		}
		return result;
	}

	/**
	 * Gets first not null sentence, and puts null on its place.
	 * 
	 * @param sentences
	 * @return
	 */
	private Sentence getSentence(List<Sentence> sentences) {

		Iterator<Sentence> iter = sentences.iterator();
		while (iter.hasNext()) {
			Sentence sent = iter.next();
			iter.remove();
			return sent;
		}
		return null;
	}

	/**
	 * Gets unique indentifier for both versions of this method.
	 */
	public String name() {
		return super.name() + "-"
				+ this.settings.get(LocationSettings.P_LOCATION_MODE);
	}

}
