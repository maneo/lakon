package org.grejpfrut.lakon.summarizer.methods.lc;

import java.util.List;

import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.impl.TokenImpl;
import org.grejpfrut.tiller.utils.thesaurus.SynSet;

/**
 * Test {@link LexicalChain#getScore()} method.
 * 
 * @author Adam Dudczak
 */
public class LexicalChainTest extends AbstractThesauriTestCase {
	
	public void testEmptryChain() {
		
		List<SynSet> syns = super.thesauri.getSynSets("zamek");
		Token token = new TokenImpl("zamek");
		ChainElement ce = new ChainElement(token, syns.get(0));
		
		LexicalChain lc = new LexicalChain(ce);
		//	position was not set for chainElement
		assertEquals(0.0, lc.getScore()); 
		
		ce = new ChainElement(token, syns.get(0), 6);
		lc.addElement(ce);
		assertEquals(1.0, lc.getScore());
		
	}

	public void testChainScoring() {
		
		List<SynSet> syns = super.thesauri.getSynSets("zamek");
		Token token = new TokenImpl("zamek");
		ChainElement ce = new ChainElement(token, syns.get(0), 0);
		ce.addPosition(0);
		
		LexicalChain lc = new LexicalChain(ce);

		syns = super.thesauri.getSynSets("twierdza");
		token = new TokenImpl("twierdza");
		ChainElement ce2 = new ChainElement(token, syns.get(0), 0);
		ce2.addPosition(0);

		lc.addElement(ce2);
		double score= lc.getScore();
		assertEquals(4.0, score); 

	}

}
