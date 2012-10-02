package org.grejpfrut.lakon.summarizer;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * This class gets average length of document in given index. For big indices
 * rather too slow.
 * 
 * @author Adam Dudczak
 */
public class AveDocumentLength {

	private static final String DEFAULT_LUCENE_FIELD = "text";
	
	

	/**
	 * Calcalates average legnth of document in index 
	 * stored at given path.
	 * 
	 * @param path
	 * @return
	 */
	public double getAveLength(File file) {

		try {
			Directory wikiDir = FSDirectory.getDirectory(file, false);
			IndexReader reader = IndexReader.open(wikiDir);
			int numberOfDocuments = reader.numDocs();
			long numberOfTerms = 0;
			for (int i = 0; i < reader.maxDoc(); i++) {
				TermFreqVector tfv = reader.getTermFreqVector(i,
						DEFAULT_LUCENE_FIELD);
				if (tfv != null) {
					for (int freq : tfv.getTermFrequencies()) {
						numberOfTerms += freq;
					}
				}
			}
			reader.close();
			return numberOfTerms / numberOfDocuments;
		} catch (IOException e) {
			throw new RuntimeException(
					"Exception while calculating ave document length", e);
		}
	}

	/**
	 * Reads one argument from command line: path to lucene index.
	 * 
	 * @param args -
	 *            command line arguments.
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("path to lucene index required");
			return;
		}
		long start = System.currentTimeMillis();
		System.out.println(new AveDocumentLength().getAveLength(new File(args[0])));
		System.out.println("Time : "
				+ ((System.currentTimeMillis() - start) / 1000) + "s");

	}

}
