package org.grejpfrut.evaluation.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User domain class.
 * @hibernate.class table="Users"
 */
public class User {
	
	public final static int ADMIN_USER = 10;
	
	public final static int NORMAL_USER = 1;
	
	public final static int INACTIVE_USER = 0;

	/**
	 * User id.
	 * 
	 * @hibernate.id generator-class="native" column="USER_ID"
	 */
	private int id;

	/**
	 * User login.
	 * 
	 * @hibernate.property column="USER_LOGIN" length="30" not-null="true" unique="true"
	 */
	private String login;

	/**
	 * User password.
	 * 
	 * @hibernate.property column="USER_PASS" length="25" not-null="true"
	 */
	private String pass;
	
	
	/**
	 * User type.
	 * 
	 * @hibernate.property column="USER_TYPE" length="1" not-null="false"
	 */
	private int type;
	
	/**
	 * Date when user was created.
	 * @hibernate.property column="USER_SINCE" not-null="false"
	 */
	private Date creationDate = new Date();
	
	/**
	 *  
	 * @hibernate.set inverse="true" cascade="all-delete-orphan
	 * @hibernate.key column="EV_USER_ID"
	 * @hibernate.one-to-many class="org.grejpfrut.evaluation.domain.Evaluation"
	 */
	private Set evaluations = new HashSet();
	

	public User() {

	}

	public User(String login, String pass) {
		this.login = login;
		this.pass = pass;
	}
	

	/**
	 * @return Gets login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login -
	 *            sets user login.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return Gets pass.
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass -
	 *            sets user password.
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return Gets user id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets user id.
	 * 
	 * @param id -
	 *            user id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Gets user type. 
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets user type.
	 * @param type 
	 */
	public void setType(int type) {
		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set evaluations) {
		this.evaluations = evaluations;
	}

}