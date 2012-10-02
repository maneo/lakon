package org.grejpfrut.ac;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.grejpfrut.ac.data.Item;
import org.grejpfrut.ac.utils.ArticleParser;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * Counts basic statistics according to collected articles. 
 * ave. number of words in article.
 * ave. number of articles per day.
 *  
 * @author ad
 *
 */
public class Stats {

	private long sumNoOfWordsInArts = 0;

	private long noOfArts = 0;
	
	private long howManyHaveDates = 0;

	private Map<String, Integer> artPerDay = new HashMap<String, Integer>();
	
	private final static Logger logger = Logger.getLogger(Stats.class);
	
	public static void main(String[] args) throws Exception {
		
		if ( args.length != 1) {
			System.out.println("i need path to folder with articles");
			return;
		}
		Stats stats = new Stats();
		stats.stats(args[0]);
		
	}

	public void stats(String workingDir) throws Exception {

		File[] files = new File(workingDir).listFiles();
		for (File file : files) {
			
			logger.info("parsing: "+file);
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			final XMLReader reader = parser.getXMLReader();
			final ArticleParser p = new ArticleParser();
			reader.setContentHandler(p);
			reader.parse(new InputSource(new FileInputStream(file)));
			Item item = p.getItem();
			this.noOfArts++;
			this.sumNoOfWordsInArts += countWords(item.getArticle() + " "
					+ item.getSummary());
			final String dateString = item.getDateString();
			if (dateString != null) {
				this.howManyHaveDates++;
				if (this.artPerDay.containsKey(dateString)) {
					int counter = this.artPerDay.get(dateString).intValue();
					this.artPerDay.put(dateString, new Integer(++counter));
				} else {
					this.artPerDay.put(item.getDateString(), new Integer(1));
				}
			}
		}
		showResults();
		
	}

	private void showResults() {
		
		double ave = sumNoOfWordsInArts / noOfArts;
		System.out.println("Average number of words in article is : "+ave+" in "+noOfArts+" articles");
		
		long noOfDates = this.artPerDay.keySet().size();
		long sumOfArts = 0;
		for (Entry<String,Integer> entry : this.artPerDay.entrySet()){
			int howMany = entry.getValue().intValue();
			sumOfArts += howMany;
		}
	
		double avePerDay = 0;
		if ( noOfDates != 0) avePerDay = sumOfArts/noOfDates;
		System.out.println("I founded :"+this.howManyHaveDates+" articles with dates");
		System.out.println("Average number of arts per day is : "+avePerDay+" calculated with "+noOfDates+" diffrent dates");
			
	}

	private long countWords(String string) {
		StringTokenizer st = new StringTokenizer(string," ");
		long wordcount = 0;
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			if (!word.equals("<br/>"))
				wordcount++;
		}
		return wordcount;
	}

}
