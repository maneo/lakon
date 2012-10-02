package org.grejpfrut.lakon.summarizer.methods.lc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.impl.TokenImpl;
import org.grejpfrut.tiller.utils.thesaurus.SynSet;

/**
 * Tests all important {@link Interpretation} methods.
 * 
 * @author Adam Dudczak
 */
public class InterpretationTest extends AbstractThesauriTestCase {

	public void testContainsToken() {

		Interpretation inter = new Interpretation();

		Token token = new TokenImpl("zamek");
		SynSet synset = this.thesauri.getSynSets("zamek").get(0);

		assertFalse(inter.containsMeaningOfToken(token, synset));

		Token token2 = new TokenImpl("maszyneria");
		SynSet synset2 = this.thesauri.getSynSets("maszyneria").get(0);

		assertFalse(inter.containsMeaningOfToken(token2, synset2));

		inter.update(token, synset, 0);

		assertFalse(inter.containsMeaningOfToken(token2, synset2));
		assertTrue(inter.containsMeaningOfToken(token, synset));

		inter.update(token2, synset2, 0);

		assertTrue(inter.containsMeaningOfToken(token2, synset2));
		assertTrue(inter.containsMeaningOfToken(token, synset));

	}

	public void testUpdate() {

		Interpretation it = prepareInterpretation();

		Token token = new TokenImpl("abderyta");
		SynSet synset = this.thesauri.getSynSets("abderyta").get(0);

		it.update(token, synset, 0);
		assertEquals(3, it.getChainElements().size());

		it.update(token, synset, 0);
		assertEquals(3, it.getChainElements().size());

		// you can add same position two times
		List<ChainElement> elements = it.getChainElements();
		int index = elements.indexOf(new ChainElement(token, synset));
		ChainElement ce = elements.get(index);
		assertNotNull(ce);
		assertEquals(2, ce.getPositions().size());

		it.update(token, synset, 1);

		assertEquals(3, ce.getPositions().size());

	}

	public void testNoConnections() {

		final Interpretation inter = prepareInterpretation();
		final Map<Integer, Set<ChainElement>> connection = inter
				.getConnections();
		int res = connection.size();
		assertEquals(2, res);
		assertEquals(2, inter.countConnections());

	}

	public void test1Connections() {

		Interpretation inter = getConnectionsInterpretation();
		
		final Map<Integer, Set<ChainElement>> connection = inter
				.getConnections();
		int res = connection.size();
		assertEquals(1, res);
		assertEquals(2, inter.countConnections());

	}
	
	public void test2Connections() {
		
		Interpretation inter = getConnectionsInterpretation();
		
		Token token = new TokenImpl("abnegat");
		SynSet synset = this.thesauri.getSynSets("abnegat").get(0);

		Token token1 = new TokenImpl("abderyta");
		SynSet synset1 = this.thesauri.getSynSets("abderyta").get(1);
		
		inter.update(token, synset, 0);
		inter.update(token1, synset1, 3);
		
		final Map<Integer, Set<ChainElement>> connection = inter
				.getConnections();
		int res = connection.size();
		assertEquals(2, res);
		assertEquals(4, inter.countConnections());
		
		
	}

	private Interpretation getConnectionsInterpretation() {
		Interpretation inter = new Interpretation();

		Token token = new TokenImpl("zamek");
		SynSet synset = this.thesauri.getSynSets("zamek").get(0);

		Token token2 = new TokenImpl("twierdza");
		SynSet synset2 = this.thesauri.getSynSets("twierdza").get(0);

		inter.update(token, synset, 0);
		inter.update(token2, synset2, 1);
		return inter;
	}
	
	

	private Interpretation prepareInterpretation() {

		Interpretation inter = new Interpretation();

		Token token = new TokenImpl("zamek");
		SynSet synset = this.thesauri.getSynSets("zamek").get(0);

		Token token2 = new TokenImpl("maszyneria");
		SynSet synset2 = this.thesauri.getSynSets("maszyneria").get(0);

		inter.update(token, synset, 2);
		inter.update(token2, synset2, 2);

		return inter;

	}

}
