package org.grejpfrut.ac.store;

import junit.framework.TestCase;

public class CacheTest extends TestCase {

	private static final int CACHE_SIZE = 50;

	public ArticleIdCache cache;

	@Override
	protected void setUp() throws Exception {

		cache = new ArticleIdCache(CACHE_SIZE,
				"org.grejpfrut.ac.store.IdsFileSystemLoader");

	}

	public void testAlreadyIn() {

		cache.addId("12");
		cache.addId("13");
		assertTrue(cache.isInCache("13"));
		assertTrue(cache.isInCache("12"));

	}

	public void testMaxCacheSize() {
		for (int i = 0; i < CACHE_SIZE + 1; i++) {
			cache.addId("" + i);
		}
		assertEquals(cache.cacheSize(), CACHE_SIZE);
	}

}
