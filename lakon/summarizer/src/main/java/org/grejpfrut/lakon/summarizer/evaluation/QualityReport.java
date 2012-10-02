package org.grejpfrut.lakon.summarizer.evaluation;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.grejpfrut.lakon.summarizer.evaluation.ReportEntry.Mesaure;

/**
 * This class is used as a data holder for quality report.
 * @author Adam Dudczak
 */
public class QualityReport {

	public enum ReportType {
		GENERAL, SUMMARIES, SCORES
	}

	/**
	 * List of {@link ReportEntry}.
	 */
	private final List<ReportEntry> report;

	public QualityReport(List<ReportEntry> report) {
		this.report = report;
		Collections.sort(this.report);
	}

	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append(printMeasure(Mesaure.COVERAGE));
		sb.append(printMeasure(Mesaure.RECALL));
		sb.append(printMeasure(Mesaure.PRECISION));
		return "\n"+sb.toString();

	}
	
	private String printMeasure(Mesaure mesaure){
		
		StringBuffer sb = new StringBuffer();
		sb.append("mesaure :"+mesaure+"\n");
		boolean header = false;
		for (ReportEntry entry : report) {
			if (!header) {
				//sb.append("id\t");
				for (String name : entry.getMethodNames()) {
					sb.append(name + "\t");
				}
				header = true;
			}
			sb.append("\n");
			sb.append(entry.getId() + "\t");
			for (String name : entry.getMethodNames()) {
				sb.append(MessageFormat.format("{0,number,#.##} \t",
						new Object[] { new Double(entry.getScore(mesaure,name)) }));
			}
		}
		return  sb.toString()+"\n";
		
	}

}
