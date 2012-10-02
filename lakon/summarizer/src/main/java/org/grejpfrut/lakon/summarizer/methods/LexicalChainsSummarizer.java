package org.grejpfrut.lakon.summarizer.methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.grejpfrut.lakon.summarizer.SummarizerBase;
import org.grejpfrut.lakon.summarizer.methods.lc.ChainElement;
import org.grejpfrut.lakon.summarizer.methods.lc.Interpretation;
import org.grejpfrut.lakon.summarizer.methods.lc.LexicalChain;
import org.grejpfrut.lakon.summarizer.methods.lc.LexicalChainsBuilder;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings.Representatives;
import org.grejpfrut.lakon.summarizer.utils.PositionConverter;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.Token.PartOfSpeech;
import org.grejpfrut.tiller.utils.thesaurus.SynSet;
import org.grejpfrut.tiller.utils.thesaurus.Thesaurus;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusFactory;

/**
 * Implementation of summarization using lexical chains, based on: "Regina
 * Barzilay and Michael Elhadad. Using lexical chains for text summarization
 * Inteligent Scalable Text Summarization Workshop (ISTS’97), pages 10–17,
 * 1997."
 * 
 * @author Adam Dudczak
 */
public class LexicalChainsSummarizer extends SummarizerBase {

	private final static Logger logger = Logger
			.getLogger(LexicalChainsSummarizer.class);

	private Thesaurus thesauri;

	private final static int DEFAULT_NUMBER_OF_INTERPRETATIONS = 20;

	private int maxNumberOfInterpretations = DEFAULT_NUMBER_OF_INTERPRETATIONS;

	private Representatives useRepresentatives = Representatives.OFF;

	private boolean onlyNouns = true;

	private PositionConverter converter;

	public LexicalChainsSummarizer(Settings settings) {
		super(settings);

		final String pathToThesauri = (String) settings
				.get(LexicalChainsSettings.P_PATH_TO_THESAURI);
		thesauri = ThesaurusFactory.getThesaurus(pathToThesauri);

		Integer noOfInter = (Integer) settings
				.get(LexicalChainsSettings.P_MAX_NUMBER_OF_INTERPRETATIONS);
		if (noOfInter != null)
			this.maxNumberOfInterpretations = noOfInter;

		String repString = (String) settings
				.get(LexicalChainsSettings.P_REPRESENTATIVE_MODE);
		if (repString != null)
			this.useRepresentatives = Representatives.valueOf(repString);

		String onlyNounsString = (String) settings
				.get(LexicalChainsSettings.P_ONLY_NOUNS);
		if (onlyNounsString != null)
			this.onlyNouns = Boolean.valueOf(onlyNounsString);

	}

	LexicalChainsSummarizer(Settings settings, Thesaurus thesauri) {
		super(settings);

		this.thesauri = thesauri;
		Integer noOfInter = (Integer) settings
				.get(LexicalChainsSettings.P_MAX_NUMBER_OF_INTERPRETATIONS);
		if (noOfInter != null)
			this.maxNumberOfInterpretations = noOfInter;

	}

	@Override
	protected List<Sentence> prepareSummary(Article article) {

		this.converter = new PositionConverter(article);

		List<Sentence> sentences = article.getSentences();
		List<Paragraph> paragraphs = article.getParagraphs();
		List<LexicalChain> chains = constructChains(paragraphs);

		if (Level.DEBUG.equals(logger.getLevel())) {
			logger.debug("Chains for text: ");
			for (LexicalChain chain : chains) {
				logger.debug("score: " + chain.getScore() + ") " + chain);
			}
		}

		Map<Integer, Double> sentenceScoring = new HashMap<Integer, Double>();

		// extract term weights on top of lexical chains
		for (LexicalChain chain : chains) {

			double lcScore = chain.getScore();
			List<ChainElement> celems = this.useRepresentatives == Representatives.ON ? chain
					.getRepresentativeElements()
					: chain.getElements();

			for (ChainElement ce : celems) {
				for (Integer sentence : ce.getPositions()) {
					Double score = sentenceScoring.get(sentence);
					if (score == null)
						score = lcScore;
					else
						score = score.doubleValue() + lcScore;
					sentenceScoring.put(sentence, score);
				}
			}
		}

		// get high rated
		SortedMap<Double, SortedSet<Integer>> topScored = new TreeMap<Double, SortedSet<Integer>>(
				SummarizerBase.DESC_COMPARATOR);
		for (Entry<Integer, Double> entry : sentenceScoring.entrySet()) {
			double value = entry.getValue();
			SortedSet<Integer> sents = topScored.get(value);
			if (sents == null) {
				sents = new TreeSet<Integer>();
				topScored.put(entry.getValue(), sents);
			}
			sents.add(entry.getKey());
		}

		List<Sentence> result = new ArrayList<Sentence>();

		// get n sentences numbers, and sort them in original order.
		TreeSet<Integer> sortedSentences = new TreeSet<Integer>();
		for (Entry<Double, SortedSet<Integer>> entry : topScored.entrySet()) {
			for (Integer value : entry.getValue()) {
				sortedSentences.add(value);
				if ((--length) == 0)
					break;
			}
			if (length == 0)
				break;
		}

		if (length > 0) {
			// when number of sentences from chain elements is lower than
			// given length of summary, add sentences with weight zero.
			sortedSentences.addAll(getRest(sentenceScoring.keySet(),
					sentences.size()).subList(0, length));
		}

		// sentences are now sorted, put them to result
		for (Integer i : sortedSentences) {
			result.add(sentences.get(i));
			logger.debug("( score: " + sentenceScoring.get(i) + " )"
					+ sentences.get(i));
		}
		logger.debug(result);
		return result;
	}

	private List<Integer> getRest(Set<Integer> fromChains, int length) {
		List<Integer> rest = new ArrayList<Integer>();
		for (int i = 0; i < length; i++) {
			if (!fromChains.contains(i))
				rest.add(i);
		}
		Collections.sort(rest);
		return rest;
	}
	
	public List<LexicalChain> getChains(Article article){
		this.art = article;
		this.converter = new PositionConverter(article);
		return constructChains(art.getParagraphs());
	}

	private List<LexicalChain> constructChains(List<Paragraph> paragraphs) {

		List<LexicalChain> chains = new ArrayList<LexicalChain>();
		for (int p = 0; p < paragraphs.size(); p++) {
			List<Sentence> sentences = paragraphs.get(p).getSentences();
			List<Interpretation> interps = new ArrayList<Interpretation>();
			for (int s = 0; s < sentences.size(); s++) {
				final List<Token> tokens = sentences.get(s).getTokens();
				for (int t = 0; t < tokens.size(); t++) {
					final Token token = tokens.get(t);

					if ((onlyNouns && token.getFirstInfo() != PartOfSpeech.NOUN)
							|| token.isStopWord())
						continue;

					List<SynSet> synsets = this.thesauri.getSynSets(token
							.getBaseForm());
					if (synsets.size() == 0)
						continue;
					logger.debug("token: " + token.getBaseForms()
							+ " number of synsets: " + synsets.size());
					List<Interpretation> newInters = new ArrayList<Interpretation>();
					for (SynSet synset : synsets) {
						newInters.addAll(createInterpretation(interps, token,
								synset, s, p));
					}
					interps = removeWeakInterpretation(newInters);
				}
				logger.debug("sentence :" + s + " no of interpretation:"
						+ interps.size());
				//System.gc();
			}
			Set<LexicalChain> rubberChains = new HashSet<LexicalChain>();
			for (Interpretation inter : interps) {
				rubberChains.addAll(LexicalChainsBuilder.getChains(inter));
			}
			logger.debug("!!paragraph " + p + " no of rubber chains:"
					+ rubberChains.size());
			rubberChains = merge(chains, rubberChains);
			chains = LexicalChainsBuilder.getStrongChains(rubberChains);
			logger.debug(" no of strong chains:" + rubberChains.size());
			logger.debug(" no of merged chains:" + chains.size() + " :"
					+ chains);
		}
		Collections.sort(chains);
		logger.debug(chains);
		return chains;
	}

	/**
	 * This method merges two lists of lexical chains.
	 * 
	 * @param chainsW
	 * @param rubberChains
	 * @return
	 */
	private Set<LexicalChain> merge(List<LexicalChain> chains,
			Set<LexicalChain> rubberChains) {

		Set<LexicalChain> result = new HashSet<LexicalChain>();
		List<LexicalChain> rubberList = new ArrayList<LexicalChain>(
				rubberChains);
		Collections.sort(rubberList);
		Iterator<LexicalChain> chainsIter = chains.iterator();
		while (chainsIter.hasNext()) {
			LexicalChain chain = chainsIter.next();
			Iterator<LexicalChain> rubberIter = rubberList.iterator();
			while (rubberIter.hasNext()) {
				LexicalChain rChain = rubberIter.next();
				LexicalChain merged = LexicalChainsBuilder.connectChains(chain,
						rChain);
				if (merged != null) {
					result.add(merged);
					chainsIter.remove();
					rubberIter.remove();
					break;
				}
			}
		}
		result.addAll(chains);
		result.addAll(rubberList);
		return result;
	}

	private List<Interpretation> removeWeakInterpretation(
			List<Interpretation> interps) {
		if (interps.size() < this.maxNumberOfInterpretations)
			return interps;
		Collections.sort(interps);
		return new ArrayList<Interpretation>(interps.subList(0,
				maxNumberOfInterpretations));
	}

	/**
	 * Creates new {@link Interpretation} for given {@link SynSet} and
	 * {@link Token}.
	 * 
	 * @param interps -
	 *            list of all existing {@link Interpretation}s.
	 * @param token -
	 *            {@link Token}
	 * @param synset -
	 *            {@link SynSet}
	 * @param sentence - 
	 *            position of given {@link Token}.
	 * @param paragraph
	 * @return List of newly created {@link Interpretation}.
	 */
	private List<Interpretation> createInterpretation(
			List<Interpretation> interps, Token token, SynSet synset,
			int sentence, int paragraph) {

		List<Interpretation> newInterpretations = new ArrayList<Interpretation>();
		int globalSentence = this.converter.convert(paragraph, sentence);
		for (Interpretation inter : interps) {
			Interpretation newInter = inter.clone();
			if (newInter.update(token, synset, globalSentence))
				newInterpretations.add(newInter);
		}
		if (interps.size() == 0) {
			newInterpretations.add(new Interpretation(token, synset,
					globalSentence));
		}
		return newInterpretations;
	}

	/**
	 * Gets unique indetifier of this method in both versions, with and without
	 * representatives.
	 */
	public String name() {
		return super.name() + "-" + this.useRepresentatives;
	}

}
