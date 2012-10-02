package org.grejpfrut.evaluation.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.grejpfrut.evaluation.domain.User;

/**
 * This class allows easy for manipulation of user's data stored in
 * {@link HttpSession}
 * 
 * @author Adam Dudczak
 */
public class UserSessionManager {

	
	/**
	 * Name of session attribute where {@link User} object will be stored.
	 */
	public final static String USER_SESSION_ATTRIBUTE = "user";
	
	/**
	 * Gets {@link User} from {@link HttpSession}.
	 * @param req - request
	 * @return an instance of {@link User} or null.
	 */
	public static User getUser(HttpServletRequest req) {
		HttpSession session = req.getSession();
		return (User) session.getAttribute(UserSessionManager.USER_SESSION_ATTRIBUTE);
	}

	/**
	 * Places given {@link User} at {@link UserSessionManager#USER_SESSION_ATTRIBUTE} 
	 * attribute in {@link HttpSession}  
	 * @param req - request.
	 * @param user - {@link User} or null.
	 */
	public static void setUser(HttpServletRequest req, User user) {
		HttpSession session = req.getSession();
		session.setAttribute(UserSessionManager.USER_SESSION_ATTRIBUTE, user);
	}

}
