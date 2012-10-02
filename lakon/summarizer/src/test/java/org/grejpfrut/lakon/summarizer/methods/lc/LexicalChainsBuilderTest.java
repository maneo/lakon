package org.grejpfrut.lakon.summarizer.methods.lc;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.impl.TokenImpl;
import org.grejpfrut.tiller.utils.thesaurus.SynSet;

/**
 * Test {@link LexicalChainsBuilder}.
 * 
 * @author Adam Dudczak
 */
public class LexicalChainsBuilderTest extends AbstractThesauriTestCase {

	List<SynSet> synsCastle;

	Token tokenCastle;

	List<SynSet> synsFortress;

	Token tokenFortress;

	List<SynSet> synsSettlement;

	Token tokenSettlement;

	List<SynSet> synsAbnegat;

	Token tokenAbnegat;

	List<SynSet> synsAbderyta;

	Token tokenAbderyta;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		synsCastle = super.thesauri.getSynSets("zamek");
		tokenCastle = new TokenImpl("zamek");

		synsFortress = super.thesauri.getSynSets("twierdza");
		tokenFortress = new TokenImpl("twierdza");

		synsSettlement = super.thesauri.getSynSets("grodzisko");
		tokenSettlement = new TokenImpl("grodzisko");

		synsAbnegat = super.thesauri.getSynSets("abnegat");
		tokenAbnegat = new TokenImpl("abnegat");

		synsAbderyta = super.thesauri.getSynSets("abderyta");
		tokenAbderyta = new TokenImpl("abderyta");

	}

	public void testGet1Chain() {

		Interpretation inter = new Interpretation();

		inter.update(tokenCastle, synsCastle.get(0), 1);
		inter.update(tokenFortress, synsFortress.get(0), 2);
		inter.update(tokenAbderyta, synsAbderyta.get(0), 0);
		inter.update(tokenAbnegat, synsAbnegat.get(0), 1);
		inter.update(tokenSettlement, synsSettlement.get(0), 1);

		assertEquals(5, inter.countConnections());

		Collection<LexicalChain> chains = LexicalChainsBuilder.getChains(inter);
		assertEquals(3, chains.size());

	}

	public void testGet2Chains() {

		Interpretation inter2 = new Interpretation();

		inter2.update(tokenCastle, synsCastle.get(1), 1);
		inter2.update(tokenFortress, synsFortress.get(0), 2);
		inter2.update(tokenAbderyta, synsAbderyta.get(1), 1);
		inter2.update(tokenAbnegat, synsAbnegat.get(0), 2);
		inter2.update(tokenSettlement, synsSettlement.get(0), 2);

		assertEquals(5, inter2.countConnections());

		Collection<LexicalChain> chains = LexicalChainsBuilder
				.getChains(inter2);
		assertEquals(3, chains.size());

		Iterator<LexicalChain> chiter = chains.iterator(); 
		LexicalChain chain = chiter.next();
		assertEquals(1, chain.getElements().size());
		
		chain = chiter.next();
		assertEquals(2, chain.getElements().size());
		
		chain = chiter.next();
		assertEquals(2, chain.getElements().size());

	}

	public void testGet3Chains() {

		Interpretation inter = new Interpretation();

		inter.update(tokenCastle, synsCastle.get(0), 1);
		inter.update(tokenFortress, synsFortress.get(0), 2);
		inter.update(tokenAbderyta, synsAbderyta.get(1), 10);
		inter.update(tokenAbnegat, synsAbnegat.get(0), 8);
		inter.update(tokenSettlement, synsSettlement.get(0), 13);

		assertEquals(5, inter.countConnections());

		Collection<LexicalChain> chains = LexicalChainsBuilder.getChains(inter);
		assertEquals(2, chains.size());

	}

	public void testConnectChains() {

		Interpretation inter = new Interpretation();

		inter.update(tokenCastle, synsCastle.get(0), 1);
		inter.update(tokenFortress, synsFortress.get(0), 6);
		inter.update(tokenAbderyta, synsAbderyta.get(1), 8);
		inter.update(tokenAbnegat, synsAbnegat.get(0), 10);

		Interpretation inter2 = new Interpretation();
		inter2.update(tokenCastle, synsCastle.get(0), 11);
		inter2.update(tokenSettlement, synsSettlement.get(0), 15);

		Collection<LexicalChain> chains1 = LexicalChainsBuilder
				.getChains(inter);
		assertEquals(2, chains1.size());
		Collection<LexicalChain> chains2 = LexicalChainsBuilder
				.getChains(inter2);
		assertEquals(1, chains2.size());

		LexicalChain lc = chains1.iterator().next();
		LexicalChain lc2 = chains2.iterator().next();

		LexicalChain res = LexicalChainsBuilder.connectChains(lc, lc2);
		assertNotNull(res);
		assertEquals(3, res.getElements().size());
		assertEquals(4.0, res.getScore());

	}

}
