package org.grejpfrut.evaluation.controllers.cmds;

/**
 * 
 * @author Adam Dudczak
 */
public class Register {

	private String login;

	private String pass;

	private String repass;
	
	public Register()
	{
		
	}

	public Register(String login, String pass, String repass) {
		this.login = login;
		this.pass = pass;
		this.repass = repass;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRepass() {
		return repass;
	}

	public void setRepass(String repass) {
		this.repass = repass;
	}

}
