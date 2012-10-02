package org.grejpfrut.tiller.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.grejpfrut.tiller.utils.thesaurus.SynSet;
import org.grejpfrut.tiller.utils.thesaurus.Thesaurus;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusFactory;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusParser;

/**
 * This class creates {@link Thesaurus} from OpenOffice tesaurus. Created class
 * {@link Thesaurus} will be serialized to given path to file thesauri.dat.
 * {@link Thesaurus} instance can be produced using {@link ThesaurusFactory}
 * class.
 * 
 * @author Adam Dudczak
 */
public class ThesaurusIndexBuilder {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.out.println("usage: [path to openoffice thesarus] [output]");
			return;
		}
		String pathToThesaurus = args[0];
		String outputPath = args[1];

		new ThesaurusIndexBuilder().prepareThesaurus(pathToThesaurus,
				outputPath);

	}

	/**
	 * Prepares thesaurus from given input file and saves it in given output
	 * path.
	 * 
	 * @param pathToThesaurus
	 * @param outputPath
	 * @throws IOException
	 */
	public void prepareThesaurus(String pathToThesaurus, String outputPath)
			throws IOException {
		File outputPathDir = new File(outputPath);
		if (!outputPathDir.exists())
			outputPathDir.mkdirs();

		ThesaurusParser tparser = new ThesaurusParser(pathToThesaurus);
		List<SynSet> synsets = tparser.parse();
		Thesaurus thes = ThesaurusFactory.getThesaurus(synsets, tparser
				.getIndex());
		ThesaurusFactory.saveThesaurus(thes, outputPath);
	}

	/**
	 * Checks how thes works.
	 * 
	 * @param args
	 */
	private static void testThes(String[] args) {
		Thesaurus thes = ThesaurusFactory.getThesaurus(args[1]);
		List<SynSet> syns = thes.getSynSets("zamek");
		System.out.println("zamek:");
		for (SynSet syn : syns) {
			System.out.println(syn.getId() + ":" + syn.getAll());
		}

		syns = thes.getSynSets("twierdza");
		System.out.println("twierdza:");
		for (SynSet syn : syns) {
			System.out.println(syn.getId() + ":" + syn.getAll());
		}

		syns = thes.getSynSets("zasuwa");
		System.out.println("zasuwa:");
		for (SynSet syn : syns) {
			System.out.println(syn.getId() + ":" + syn.getAll());
		}
	}

}
