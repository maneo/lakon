package org.grejpfrut.evaluation.dao;

import java.util.List;

import org.grejpfrut.evaluation.domain.User;

/**
 * Data access objet to CRUD {@link User}s.
 * 
 * @author ad
 *
 */
public interface UsersManager {
	
	public User getUser(String login);
	public User getUser(int id);
	public void saveUser(User user);
	public void deleteUser(int id);	
	public List<User> getUsers();
	public List<User> getUsers(int userType);
	
}
