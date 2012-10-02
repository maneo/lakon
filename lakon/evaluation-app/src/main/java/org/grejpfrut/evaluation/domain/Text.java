package org.grejpfrut.evaluation.domain;

/**
 * Text domain class.
 * 
 * @hibernate.class table="Texts"
 */
public class Text {

	/**
	 * Text id.
	 * 
	 * @hibernate.id generator-class="native" column="TXT_ID"
	 */
	private int id;
	
	/**
	 * Text's title.
	 * @hibernate.property column="TXT_TITLE" length="400" not-null="true"
	 */
	private String title;

	/**
	 * Text text.
	 * 
	 * @hibernate.property column="TXT_TEXT" length="100000" not-null="true"
	 */
	private String text;
	
	
	/**
	 * @hibernate.property column="TXT_EXT_LENGTH" length="3" not-null="true"
	 */
	private int extractLength;
	
	
	/**
	 * @hibernate.property column="TXT_EVAL_COUNT" length="3" not-null="true"
	 */
	private int evaluationCount = 0;

	
	public Text(){
		
	}
	
	public Text(String title, String text){
		this.text = text;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getExtractLength() {
		return extractLength;
	}

	public void setExtractLength(int extractLength) {
		this.extractLength = extractLength;
	}

	public int getEvaluationCounr() {
		return evaluationCount;
	}

	public void setEvaluationCounr(int evaluationCounr) {
		this.evaluationCount = evaluationCounr;
	}


}
