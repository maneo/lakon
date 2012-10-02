package org.grejpfrut.ac.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.TestCase;

import org.grejpfrut.ac.data.Item;

public class FileStorerTest extends TestCase {

	public final String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><article><title>test-title</title><category>test-category</category><link>http://wiadomosci.gazeta.pl/wiadomosci/1,test,3481099.html?skad=rss</link><summary>test-summup</summary><fulltext><![CDATA[test-article]]></fulltext><pubDate>2006.07.13</pubDate></article>"; 

	
	public void testStoring() {

		Item item = new Item();
		item.appendArticle("test-article");
		item.appendTitle("test-title");
		item.appendCategory("test-category");
		item
				.appendLink("http://wiadomosci.gazeta.pl/wiadomosci/1,test,3481099.html?skad=rss");
		item.appendDate("Thu, 13 Jul 2006 22:04:31 +0200");
		item.appendSummary("test-summup");
		FileArticleStorer fas = new FileArticleStorer("work");
		fas.store(item);
		File art = new File(fas.getPath() + File.separator
				+ item.getIdentifier() + ".xml");
		String file = getFromFile(art);
		assertTrue(file.equals(res));

		art.delete();
	}

	private String getFromFile(File name) {

		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		try {
			is = new FileInputStream(name);
		} catch (FileNotFoundException e1) {
			fail();
		}
		Reader r = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(r);
		String line = "";
		try {
			while ((line = br.readLine()) != null)
				sb.append(new String(line.getBytes(), "UTF-8"));
			br.close();
		} catch (IOException e) {
			System.out.println("Error during reading file");
		}
		return sb.toString();
	}

}
