package org.grejpfrut.evaluation.controllers.cmds;

import java.util.Arrays;
import java.util.List;

/**
 * Command class representing evaluation.
 * 
 * @author Adam Dudczak
 */
public class EvaluationCommand {
	
	String artId;
	
	String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * Gets the list of selected sentences ids, each id
	 * is a expresion of this kind : 1_2
	 * @return
	 */
	public List<String> getSentences() {
		String[] sids = ids.split(",");
		return Arrays.asList(sids);
	}

	public String getArtId() {
		return artId;
	}

	public void setArtId(String artId) {
		this.artId = artId;
	}
	
	/**
	 * @return Gets {@link EvaluationCommand#artId} as @{link {@link Integer}. 
	 */
	public Integer getIntArtId() {
		if ( artId != null ) return Integer.valueOf(artId);
		return null;
	}

}
