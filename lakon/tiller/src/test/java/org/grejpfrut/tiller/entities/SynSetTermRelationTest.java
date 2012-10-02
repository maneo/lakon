package org.grejpfrut.tiller.entities;

import junit.framework.TestCase;

import org.grejpfrut.tiller.utils.thesaurus.SynSet;
import org.grejpfrut.tiller.utils.thesaurus.SynSetImpl;
import org.grejpfrut.tiller.utils.thesaurus.SynSet.TermRelation;

/**
 * This tests {@link SynSetImpl#getRelation(String)} and
 * {@link SynSetImpl#getRelation(SynSet))} methods of {@link SynSetImpl} class.
 * 
 * @author Adam Dudczak
 */
public class SynSetTermRelationTest extends TestCase {

	public void testSynonymsRelations() {

		SynSet castleSynset = new SynSetImpl("zamek", 0);
		castleSynset.addSyn("twierdza");
		castleSynset.addSyn("cytadela");

		SynSet stormSynset = new SynSetImpl("burza", 1);
		stormSynset.addHypernym("zjawisko atmosferyczne");
		stormSynset.addHyponym("sztorm");

		assertEquals(TermRelation.UNKNOWN, castleSynset
				.getRelation(stormSynset));
		assertEquals(TermRelation.UNKNOWN, stormSynset
				.getRelation(castleSynset));

		assertEquals(TermRelation.SYNONYMS, castleSynset
				.getRelation(castleSynset));
		assertEquals(TermRelation.SYNONYMS, stormSynset
				.getRelation(stormSynset));

		assertEquals(TermRelation.SYNONYMS, castleSynset
				.getRelation("cytadela"));

		assertEquals(TermRelation.OTHER, stormSynset
				.getRelation("sztorm"));
		
		assertEquals(TermRelation.UNKNOWN, stormSynset
				.getRelation("zamek"));

	}

}
