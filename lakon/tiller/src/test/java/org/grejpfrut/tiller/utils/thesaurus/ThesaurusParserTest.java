package org.grejpfrut.tiller.utils.thesaurus;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.grejpfrut.tiller.TillerTestCase;

/**
 * This test check {@link ThesaurusParser}.
 * @author Adam Dudczak
 */
public class ThesaurusParserTest extends TillerTestCase {
	
	
	private static final String TEST_THESAURUS_CATEGORIES = "test.thesaurus.categories";
	private static final String TEST_THESAURUS_MULTIPLE = "test.thesaurus.multiple";
	private final static String TEST_THESAURUS_PROPERTY = "test.thesaurus";
	

	public void testParseString() {
	
		final String thesaurus = super.testData.getProperty(TEST_THESAURUS_PROPERTY);
		ThesaurusParser parser = new ThesaurusParser(null);
		try {
			
			List<SynSet> res = parser.parse(thesaurus);

			validateSynsets(res);
			
			
		} catch (IOException e) {
			fail("Exception while parsing thesaurus");
		}
	}
	
	public void testCattegoriesSynsets() {
		
		final String thesaurus = super.testData.getProperty(TEST_THESAURUS_CATEGORIES);
		ThesaurusParser parser = new ThesaurusParser(null);
		try {
		
			List<SynSet> res = parser.parse(thesaurus);
			assertEquals(3, res.size());
			
			Map<String, List<Integer>> index = parser.getIndex();
			assertEquals(13, index.size());
			assertEquals(2, index.get("zamek").size());
			

		} catch (IOException e) {
			fail("Exception while parsing thesaurus");
		}
	}
	
	public void testDuplicatedSynsets() {
		
		final String thesaurus = super.testData.getProperty(TEST_THESAURUS_MULTIPLE);
		ThesaurusParser parser = new ThesaurusParser(null);
		try {
			
			List<SynSet> res = parser.parse(thesaurus);

			validateSynsets(res);
			
			Map<String, List<Integer>> index = parser.getIndex();
			assertEquals(5, index.size());
			assertEquals(1, index.get("60 minut").size());
			assertEquals(0, index.get("60 minut").get(0).intValue());
			
		} catch (IOException e) {
			fail("Exception while parsing thesaurus");
		}
	}

	private void validateSynsets(List<SynSet> res) {
		assertEquals(3, res.size());

		SynSet synset = res.get(0);
		assertEquals(1, synset.getHypernyms().size());
		assertEquals(0, synset.getHyponyms().size());
		assertEquals(2, synset.getSyns().size());
		assertEquals(3, synset.getAll().size());

		synset = res.get(1);
		assertEquals(0, synset.getHypernyms().size());
		assertEquals(1, synset.getHyponyms().size());
		assertEquals(2, synset.getSyns().size());
		assertEquals(3, synset.getAll().size());

		synset = res.get(2);
		assertEquals(0, synset.getHypernyms().size());
		assertEquals(0, synset.getHyponyms().size());
		assertEquals(2, synset.getSyns().size());
		assertEquals(2, synset.getAll().size());
	}


}
