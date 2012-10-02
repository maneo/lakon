package org.grejpfrut.lakon.summarizer.methods.lc;

import java.io.IOException;
import java.util.List;

import org.grejpfrut.lakon.summarizer.AbstractDataTestCase;
import org.grejpfrut.tiller.utils.thesaurus.SynSet;
import org.grejpfrut.tiller.utils.thesaurus.Thesaurus;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusFactory;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusParser;

/**
 * Abstract class which constructs {@link Thesaurus} from data given in default
 * test data file (See {@link AbstractDataTestCase}).
 * 
 * @author Adam Dudczak
 */
public abstract class AbstractThesauriTestCase extends AbstractDataTestCase {

	protected final static String INTERPRETATION_TESAURUS_KEY = "test.interpretation.thesaurus";

	protected Thesaurus thesauri;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		final String thes = super.testData
				.getProperty(INTERPRETATION_TESAURUS_KEY);
		this.thesauri = AbstractThesauriTestCase.getTestThesauri(thes);

	}

	public static Thesaurus getTestThesauri(String thes) throws IOException {

		ThesaurusParser tparser = new ThesaurusParser(null);
		List<SynSet> syns = tparser.parse(thes);
		return ThesaurusFactory.getThesaurus(syns, tparser.getIndex());

	}

}
