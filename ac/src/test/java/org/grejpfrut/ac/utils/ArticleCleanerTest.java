package org.grejpfrut.ac.utils;

import junit.framework.TestCase;

import org.grejpfrut.ac.cleaners.ArticleCleaner;

public class ArticleCleanerTest extends TestCase {
	
	private final static String text = "<br> <br> <br>  <br>a";
	private final static String result = "<br>a";
	
	private final static String basic = "<br><br>a";
	
	public void testCleaner(){
		
		String res = ArticleCleaner.clean(text);
		assertTrue(result.equals(res));
	}
	
	public void testCleaner2(){
		
		String res = ArticleCleaner.clean(basic);
		assertTrue(result.equals(res));
	}

}
