package org.grejpfrut.wiki.searcher.gui;

import javax.swing.table.AbstractTableModel;

import org.grejpfrut.wiki.utils.TermScore;

public class KeywordsTableModel extends AbstractTableModel {

	private TermScore[] termScores = null;

	private String[] colName = new String[] { "keywords", "scores" };

	public KeywordsTableModel() {

	}

	public KeywordsTableModel(TermScore[] score) {
		this.termScores = score;
	}

	public void setTermScores(TermScore[] score) {
		this.termScores = score;
		this.fireTableDataChanged();
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		if (this.termScores != null)
			return this.termScores.length;
		return 0;
	}

	@Override
	public String getColumnName(int column) {
		return this.colName[column];
	}

	public Object getValueAt(int row, int col) {
		if (this.termScores[row] != null) {
			if (col == 0)
				return this.termScores[row].getTerm();
			return this.termScores[row].getTfidf();
		}
		else return null;
	}

}
