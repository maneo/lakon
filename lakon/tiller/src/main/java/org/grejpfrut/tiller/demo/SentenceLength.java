package org.grejpfrut.tiller.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * Class used to calculate average number of sentences in 20% of input text.  
 * @author Adam Dudczak
 */
public class SentenceLength {
	
	private TillerConfiguration confi;
	
	private String LINE_SEPARATOR = System.getProperty("line.separator");
	

	public SentenceLength(TillerConfiguration conf) {
		this.confi = conf;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Properties conf;
		try {
			conf = new Properties();
			if (args.length >= 2) {
				conf.load(new FileInputStream(new File(args[1])));
			} else
				conf.load(SentenceLength.class
						.getResourceAsStream("/tiller-conf.properties"));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		SentenceLength sl = new SentenceLength(new TillerConfiguration(conf));
		System.out.println(sl.getSentenceLengthInfo(getText(args[0]),false));

	}
	
	public String getSentenceLengthInfo(String text, boolean asHtml){
		
		if (asHtml ) return getHtml(text);
		
		ArticleBuilder artBuilder = new ArticleBuilder(this.confi);
		Article art = artBuilder.getArcticle(text);
		StringBuffer sb = new StringBuffer();

		long length = 0;
		long noSentence = 0;
		length += art.getTokens().size();
		noSentence += art.getSentences().size();
		
		for ( Sentence sent : art.getSentences() ) {
			sb.append(sent.getText()+LINE_SEPARATOR);
		}
		

		sb.append("Number of words: " + length+LINE_SEPARATOR);
		sb.append("Number of sentences: " + noSentence+LINE_SEPARATOR);
		double ave = length / noSentence;
		sb.append("With average length: " + ave+LINE_SEPARATOR);
		sb.append("Abstract length :" + (0.2 * length / ave)+LINE_SEPARATOR);
		
		return sb.toString();
		
	}
	
	private String getHtml(String text){
		
		ArticleBuilder artBuilder = new ArticleBuilder(this.confi);
		Article art = artBuilder.getArcticle(text);
		StringBuffer sb = new StringBuffer();

		long length = 0;
		long noSentence = 0;
		length += art.getTokens().size();
		noSentence += art.getSentences().size();
		
		sb.append("<p> Number of words: " + length+"</p>");
		sb.append("<p>Number of sentences: " + noSentence+"</p>");
		double ave = length / noSentence;
		sb.append("<p>With average length: " + ave+"</p>");
		sb.append("<p>Abstract length :" + (0.2 * length / ave)+"</p>");
		
		return sb.toString();
		
		
	}
	

	private static String getText(String file) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(file)), "UTF8"));
			String str = null;
			while ((str = in.readLine()) != null) {
				sb.append(str + " ");
			}
			in.close();
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
