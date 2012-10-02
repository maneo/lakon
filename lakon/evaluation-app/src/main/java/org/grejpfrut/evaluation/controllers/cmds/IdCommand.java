package org.grejpfrut.evaluation.controllers.cmds;

/**
 * Command used for dealing with simple deletion/modification of elements.
 * 
 * @author Adam Dudczak
 *
 */
public class IdCommand {
	
	public final static String DELETE_ACTION = "delete";
	
	public final static String MODIFY_ACTION = "modify";
	
	
	private String id;
	
	private String action;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getId() {
		return id;
	}
	
	public int getIdAsInt(){
		return  Integer.parseInt(id);
	}
	
	public void setId(String id) {
		this.id = id;
	}
	

}
