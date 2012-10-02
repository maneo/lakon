package org.grejpfrut.tiller.builders;

import java.util.ArrayList;
import java.util.List;

import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.entities.impl.ParagraphImpl;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * This class produces {@link Paragraph}s, whole process is based on input
 * {@link String} with article.
 * 
 * @author Adam Dudczak
 * 
 */
public class ParagraphBuilder {

	/**
	 * Minimal length of paragraph in words by default it is 0.
	 */
	private int minParaLength = 0;

	/**
	 * {@link Paragraph}s are build up from
	 * 
	 * @{link Sentence}s.
	 */
	private final SentenceBuilder sentenceBuilder;

	/**
	 * This constructor gets information about
	 * {@link TillerConfiguration#MIN_PARA_LENGTH_PROPERTY} also it inits
	 * {@link ParagraphBuilder#sentenceBuilder} for instance of this class.
	 * 
	 * @param conf -
	 *            {@link TillerConfiguration#getMinimalParagraphLength()} value
	 *            is optional.
	 */
	public ParagraphBuilder(TillerConfiguration conf) {

		this.sentenceBuilder = new SentenceBuilder(conf);
		this.minParaLength = conf.getMinimalParagraphLength();

	}

	/**
	 * This method gets an array of {@link Paragraph} from raw text. It splits
	 * paragraph by \n+ regular expression. Very short paragraphs (defined by
	 * {@link ParagraphBuilder#minParaLength}) can be titles of next paragraph.
	 * 
	 * @param article -
	 *            raw text.
	 * @return An array of {@link Paragraph}.
	 */
	public List<Paragraph> getParagraphs(String article) {

		List<Paragraph> paras = new ArrayList<Paragraph>();
		String ps[] = article.trim().split("\n+");
		String title = null;
		for (int i = 0; i < ps.length; i++) {
			final String sentence = ps[i].trim();
			if (isParagraph(i, ps.length - 1, sentence, title)) {
				List<Sentence> sentences = this.sentenceBuilder.getSentences(sentence);
				if (title != null) {
					paras.add(new ParagraphImpl(sentences, title));
					title = null;
				} else
					paras.add(new ParagraphImpl(sentences));
			} else {
				title = sentence;
			}
		}
		return paras;
	}

	/**
	 * If this input text is too short it will be treated as a title of next
	 * paragraph. If this is the last paragraph of article it will be treated as
	 * a paragraph despite of its length. If input text is too short and
	 * previous paragraph was also short, this method will treat current input
	 * text as a paragraph (and previous one will be its title).
	 * 
	 * @param currentIndex -
	 *            current index in article.
	 * @param maxIndex -
	 *            max index for this article.
	 * @param input -
	 *            input text.
	 * @param previous -
	 *            if previous para was to short this souldn't be null.
	 * @return true - if input text is paragraph, false otherwise.
	 */
	private boolean isParagraph(int currentIndex, int maxIndex, String input,
			String previous) {
		if (currentIndex == maxIndex)
			return true;
		final int paraLength = input.split("\\s+").length;
		if (paraLength < this.minParaLength) {
			if (previous != null)
				return true;
			else
				return false;
		}
		return true;
	}

}
