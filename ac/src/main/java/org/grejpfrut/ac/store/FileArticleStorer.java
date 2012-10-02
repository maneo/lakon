package org.grejpfrut.ac.store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.grejpfrut.ac.data.Item;

public class FileArticleStorer implements ArticleStorer {

	private final static Logger logger = Logger
			.getLogger(FileArticleStorer.class);

	private final String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	private final String path;
	
	private final static String NLINE = System.getProperty("line.separator");

	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy.MM.dd");

	public String getPath() {
		return path;
	}

	public FileArticleStorer(String path) {

		this.path = path;
		handleWorkingDir();

	}

	private void handleWorkingDir() {
		try {
			File workingDir = new File(path);
			if (!workingDir.exists() || !workingDir.isDirectory())
				logger
						.info("creating working directory: "
								+ workingDir.mkdir());
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception while creating new working directory", e);
		}

	}

	public void store(Item item) {

		StringBuffer xml = new StringBuffer();
		
		
		xml.append(xmlHeader);
		xml.append("<article>"+NLINE);
		xml.append("<title>" + item.getTitle() + "</title>"+NLINE);
		xml.append("<category>" + item.getCategory() + "</category>"+NLINE);
		xml.append("<link>" + item.getLink() + "</link>"+NLINE);
		xml.append("<summary>" + item.getSummary() + "</summary>"+NLINE);
		xml.append("<fulltext><![CDATA[" + item.getArticle() + "]]></fulltext>"+NLINE);
		final String date = DATE_FORMAT.format(item.getDate());
		xml.append("<pubDate>" + date + "</pubDate>"+NLINE);
		xml.append("</article>");
		checkWorkingDir(path);
		File file = new File(path + File.separator + item.getIdentifier()
				+ ".xml");
		try {
			if (!file.exists())
				file.createNewFile();

			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "UTF8"));
			out.write(xml.toString());
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Error while saving article");
		}
	}

	private void checkWorkingDir(final String path) {

		File workingDir = new File(path);
		if (!workingDir.exists())
			workingDir.mkdir();

	}
}
