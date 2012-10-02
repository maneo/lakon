package org.grejpfrut.evaluation.domain;


/**
 * Users' evaluation domain class.
 * 
 * @hibernate.class table="Evaluations"
 */
public class Evaluation {

	/**
	 * Evaluation id.
	 * 
	 * @hibernate.id generator-class="native" column="EV_ID"
	 */
	private int id;

	/**
	 * Article for this evaluation.
	 * 
	 * @hibernate.many-to-one column="EV_TXT_ID"  class="org.grejpfrut.evaluation.domain.Text" cascade="none" lazy="false" 
	 */
	private Text text;

	/**
	 * 
	 * @hibernate.many-to-one column="EV_USER_ID" class="org.grejpfrut.evaluation.domain.User"  cascade="none" lazy="false" 
	 */
	private User user;
	
	/**
	 * String with selected sentences ids eg. "1,4,5,6"
	 * @hibernate.property column="EV_SEL_IDS" length="4000" not-null="true"
	 */
	private String selectedIds;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSelectedIds() {
		return selectedIds;
	}
	
	/**
	 * @return Gets selected ids as array of int.
	 */
	public int[] getSelectedIdsArray() {
		String[] splitted = this.selectedIds.split(",");
		int[] iids = new int[splitted.length];
		for(int i = 0; i<iids.length; i++){
			iids[i] = Integer.parseInt(splitted[i]);
		}
		return iids;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

}
