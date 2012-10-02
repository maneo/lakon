package org.grejpfrut.tiller.utils.thesaurus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Synonimy.ux.pl gives information about hypernyms (hyponyms) only in
 * OpenOffice thesaurus format. This class parses file and creates lucene index
 * out of this thesaurus.
 * 
 * @author Adam Dudczak
 */
public class ThesaurusParser {

	private final static Logger logger = Logger
			.getLogger(ThesaurusParser.class);

	private static final String HYPERNYM_INDICATOR = "nadrz\u0119dne";

	private static final String HYPONYM_INDICATOR = "podrz\u0119dne";

	private final String pathToTezaurus;

	private enum STATE {
		START, LIST, SYN_GROUP
	};

	private static final String THESAURUS_ENCODING = "ISO-8859-2";

	private List<SynSet> synsets = null;

	private Map<String, List<Integer>> index = null;

	/**
	 * @param pathToTez -
	 *            path to file with thesaurus in OpenOffice format.
	 */
	public ThesaurusParser(String pathToTez) {
		this.pathToTezaurus = pathToTez;
	}

	/**
	 * @return Thesaurus index (word: list of synsets in which this word
	 *         appears) or null - when parse, was not executed before.
	 */
	public Map<String, List<Integer>> getIndex() {
		return index;
	}

	/**
	 * Gets thesaurus from file (path given in constructor).
	 * 
	 * @throws IOException
	 */
	public List<SynSet> parse() throws IOException {

		FileInputStream fis = new FileInputStream(this.pathToTezaurus);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis,
				THESAURUS_ENCODING));
		try {
			parse(reader);
		} catch (IOException e) {
			throw new RuntimeException("Exception while parsing thesaurus", e);
		} finally {
			reader.close();
		}
		return this.synsets;
	}

	/**
	 * For test only.
	 * 
	 * @param content -
	 *            synonimy.ux.pl thesaurus as a String
	 * @return List of all {@link SynSet}.
	 * @throws IOException
	 */
	public List<SynSet> parse(String content) throws IOException {

		StringReader sreader = new StringReader(content);
		BufferedReader reader = new BufferedReader(sreader);
		try {
			parse(reader);
		} catch (IOException e) {
			throw new RuntimeException("Exception while parsing thesaurus", e);
		} finally {
			reader.close();
		}
		return synsets;
	}

	/**
	 * Method which parses synonimy.ux.pl thesaurus in OpenOffice format.
	 * 
	 * @param reader -
	 *            reader.
	 * @throws IOException
	 */
	private void parse(BufferedReader reader) throws IOException {

		this.synsets = new ArrayList<SynSet>();
		this.index = new HashMap<String, List<Integer>>();

		String line = null;
		SynSet synset = null;
		String entry = null;
		int numberOfSynSets = 0;
		STATE state = STATE.START;

		while ((line = reader.readLine()) != null) {

			if (state == STATE.START) {
				state = STATE.LIST; // ignore the first line
			} else if (state == STATE.LIST) {

				String[] tokenized = line.split("\\|");
				numberOfSynSets = Integer.parseInt(tokenized[1]);
				entry = tokenized[0];
				state = STATE.SYN_GROUP;

			} else if (state == STATE.SYN_GROUP) {
				assert numberOfSynSets != 0;
				synset = new SynSetImpl(entry, synsets.size());
				String[] tokenized = line.substring(2).split("\\|");
				for (String token : tokenized) {
					processSynSetEntry(synset, token.trim());
				}
				List<Integer> isNewBuffer = new ArrayList<Integer>();
				boolean isNew = false;
				for (String token : synset.getSyns()) {
					List<Integer> occurOfThisToken = this.index.get(token);
					if (occurOfThisToken == null) {
						isNew = true;
						break;
					}
					if (isNewBuffer.size() == 0)
						isNewBuffer.addAll(occurOfThisToken);
					else {
						isNewBuffer.retainAll(occurOfThisToken);
						isNew = isNewBuffer.size() == 0;
					}
				}
				if (isNew) {
					synsets.add(synset);
					addToIndex(synset);
				}
				if (--numberOfSynSets == 0)
					state = STATE.LIST;
			}
		}
	}

	private void addToIndex(SynSet synset) {

		for (String item : synset.getSyns()) {
			List<Integer> occur = this.index.get(item);
			if (occur == null) {
				occur = new ArrayList<Integer>();
				this.index.put(item, occur);
			}
			occur.add(synset.getId());
		}

	}

	/**
	 * Processes single thesaurus entry, and deciced which synonyms are hyponyms
	 * (hypernyms). Adds them to given {@link SynSet}.
	 * 
	 * @param synset -
	 *            current synset.
	 * @param token -
	 *            current entry.
	 */
	private void processSynSetEntry(SynSet synset, String token) {

		if (token.endsWith(")")) {

			String[] splitted = token.split("\\s\\(");
			boolean matched = false;
			for (int i = 1; i < splitted.length; i++) {
				if (splitted[i].contains(HYPERNYM_INDICATOR)) {
					synset.addHypernym(splitted[0].trim());
					matched = true;
					break;
				}else if (splitted[i].contains(HYPONYM_INDICATOR)){
					synset.addHyponym(splitted[0].trim());
					matched = true;
					break;
				}
			}
			if ( !matched )
				synset.addSyn(splitted[0].trim());

		} else {
			synset.addSyn(token.trim());
		}

	}
}
