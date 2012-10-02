package org.grejpfrut.evaluation.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.grejpfrut.evaluation.domain.User;

/**
 * 
 * @author Adam Dudczak
 */
public class AuthenticationFilter implements Filter {

	public static final String R_INSUFFICIENT_PRIVILEGES = "insufficient.privileges";

	/**
	 * Initial parameter name with names of pages available for admin users.
	 */
	private static final String P_ADMIN = "admin";

	/**
	 * Initial parameter name with names of pages available for authenticated
	 * users.
	 */
	private static final String P_NORMAL = "normal";

	/**
	 * Initial parameter name with names of pages available without
	 * authentication.
	 */
	private static final String P_ALL = "all";


	private Map<String, Integer> acl = new HashMap<String, Integer>();

	public void init(FilterConfig config) throws ServletException {

		final String adminPages = config.getInitParameter(P_ADMIN);
		setAccessLevel(adminPages, acl, User.ADMIN_USER);

		final String userPages = config.getInitParameter(P_NORMAL);
		setAccessLevel(userPages, acl, User.NORMAL_USER);

		final String allAccessPages = config.getInitParameter(P_ALL);
		setAccessLevel(allAccessPages, acl, User.INACTIVE_USER);

	}

	private void setAccessLevel(String pages, Map<String, Integer> acl,
			int userType) {
		for (String page : pages.split(",")) {
			Integer level = acl.get(page);
			if (level == null) {
				acl.put(page, Integer.valueOf(userType));
			} else {
				if (level < userType)
					acl.put(page, Integer.valueOf(userType));
			}
		}
	}

	public void doFilter(ServletRequest sreq, ServletResponse sres,
			FilterChain chain) throws IOException, ServletException {

		if (sreq instanceof HttpServletRequest
				&& sres instanceof HttpServletResponse) {

			final HttpServletRequest req = (HttpServletRequest) sreq;
			final HttpServletResponse res = (HttpServletResponse) sres;
			String page = resolvePage(req);
			Integer pageAccessLevel = this.acl.get(page);
			if (pageAccessLevel == null)
				pageAccessLevel = User.INACTIVE_USER;
			int userType = getUserType(req); 
			if (userType < pageAccessLevel.intValue()) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
		}
		chain.doFilter(sreq, sres);

	}

	private int getUserType(HttpServletRequest req) {

		HttpSession session = req.getSession(false);
		if (session == null)
			return User.INACTIVE_USER;
		User user = (User) session.getAttribute(UserSessionManager.USER_SESSION_ATTRIBUTE);
		if (user == null)
			return User.INACTIVE_USER;
		return user.getType();
	}

	private String resolvePage(HttpServletRequest req) {
		String page = req.getServletPath().substring(1);
		if (page.equals(""))
			page = "index.html";
		return page;
	}

	public void destroy() {

	}

}
