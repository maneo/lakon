package org.grejpfrut.ac;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * I put wrong tag to indicate end of paragraph. This class examines xmls to
 * find incorrect tag and replace them with correct one.
 * 
 * @author ad
 * 
 */
public class XmlCorrector {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{

		if (args.length != 1) {
			System.out.println("i need path to folder with articles");
			return;
		}
		
		correctXmls(args[0]);

	}

	private static void correctXmls(String workingDir) throws Exception {

		File[] files = new File(workingDir).listFiles();
		long filecounter = 0;
		for (File file : files) {

			String line;
			StringBuffer sb = new StringBuffer();
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis,"UTF-8"));
			while ((line = reader.readLine()) != null) {
				line = line.replaceAll("<br/>", "\n");
				line = line.replaceAll("<fulltext>", "<fulltext><![CDATA[");
				line = line.replaceAll("</fulltext>", "]]></fulltext>");
				sb.append(line + "\n");
			}
			reader.close();
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
			out.write(sb.toString());
			out.close();
			filecounter++;
		}
		System.out.println("value replaced in : "+filecounter+" file");
	}

}
