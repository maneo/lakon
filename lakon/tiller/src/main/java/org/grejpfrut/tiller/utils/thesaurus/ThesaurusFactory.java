package org.grejpfrut.tiller.utils.thesaurus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

/**
 * Class which allow for serialization of {@link Thesaurus}.
 * 
 * @author Adam Dudczak
 */
public class ThesaurusFactory {

	public static final String THESAURI_FILE_NAME = "thesauri.dat";

	private static final Map<String, Thesaurus> thesauriCache = new HashMap<String, Thesaurus>();

	/**
	 * @param path -
	 *            path to directory where {@link Thesaurus} file is.
	 * @return instnace of {@link Thesaurus}.
	 */
	public static Thesaurus getThesaurus(String path) {

		if (thesauriCache.get(path) != null)
			return thesauriCache.get(path);

		try {
			Thesaurus thes = (Thesaurus) SerializationUtils
					.deserialize(new FileInputStream(new File(path
							+ File.separator + THESAURI_FILE_NAME)));
			thesauriCache.put(path, thes);
			return thes;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets {@link Thesaurus} for list of {@link SynSet}.
	 * 
	 * @param synSets -
	 *            list of {@link SynSet};
	 * @return {@link Thesaurus} build from given {@link SynSet}s.
	 */
	public static Thesaurus getThesaurus(List<SynSet> synSets,
			Map<String, List<Integer>> index) {
		return new Thesaurus(synSets, index);
	}

	/**
	 * @param thes -
	 *            {@link Thesaurus} instance for serialization.
	 * @param path -
	 *            path to directory in which thesauri file will be stored.
	 */
	public static void saveThesaurus(Thesaurus thes, String path) {

		try {
			FileOutputStream fos = new FileOutputStream(new File(path
					+ File.separator + THESAURI_FILE_NAME));
			SerializationUtils.serialize(thes, fos);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
