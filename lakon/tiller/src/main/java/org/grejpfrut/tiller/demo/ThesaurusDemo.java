package org.grejpfrut.tiller.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.grejpfrut.tiller.utils.thesaurus.SynSet;
import org.grejpfrut.tiller.utils.thesaurus.Thesaurus;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusFactory;

/**
 * Allows to get synset from thesuari throught console.
 * @author Adam Dudczak
 */
public class ThesaurusDemo {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.out.println("usage: [path to thesarusi.dat]");
			return;
		}

		Thesaurus thes = ThesaurusFactory.getThesaurus(args[0]);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		try {
			while (!(line = br.readLine()).equals(":q")) {
				if (!"".equals(line)) {
					List<SynSet> syns = thes.getSynSets(line.trim());
					for (SynSet s : syns) {
						System.out.println(s.getId() + " " + s.getSyns()
								+ " hyper: " + s.getHypernyms() + ""
								+ " hypo: " + s.getHyponyms());
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Exception while reading from input", e);
		} finally {
			br.close();
		}

	}

}
