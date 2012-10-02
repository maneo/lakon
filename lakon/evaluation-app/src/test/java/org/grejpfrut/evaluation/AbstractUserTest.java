package org.grejpfrut.evaluation;

import junit.framework.TestCase;

import org.grejpfrut.evaluation.dao.UsersManager;
import org.grejpfrut.evaluation.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Abstract base test, which puts test user into {@link UsersManager}.
 * 
 * @author Adam Dudczak
 */
public class AbstractUserTest extends TestCase {

	public static final String TEST_EXISTING_USER_PASS = "test";

	public static final String USERS_MANAGER_BEAN_NAME = "usersManager";

	protected static final String TEST_EXISTING_USER_EMAIL = "test@test.com";

	protected ApplicationContext ctx;

	protected UsersManager usersManager;

	protected void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("evaluation-app-servlet.xml");

		this.usersManager = (UsersManager) ctx.getBean(USERS_MANAGER_BEAN_NAME);
		User user = new User(TEST_EXISTING_USER_EMAIL,TEST_EXISTING_USER_PASS);
		user.setType(1);
		this.usersManager.saveUser(user);

	}

	@Override
	protected void tearDown() throws Exception {
		User user = this.usersManager.getUser(TEST_EXISTING_USER_EMAIL);
		this.usersManager.deleteUser(user.getId());
	}

}
