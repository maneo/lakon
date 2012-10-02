package org.grejpfrut.evaluation.dao;

import java.util.List;

import org.grejpfrut.evaluation.domain.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Basic implementation of DAO, used for accessing {@link User}s
 * 
 * @author Adam Dudczak
 * 
 */
public class UsersManagerImpl extends HibernateDaoSupport implements
		UsersManager {
	
	public void saveUser(User user) {
		this.getHibernateTemplate().saveOrUpdate(user);
		this.getHibernateTemplate().flush();
	}

	public void deleteUser(int id) {
		this.getHibernateTemplate().flush();
		User user = this.getUser(id);
		this.getHibernateTemplate().delete(user);
		this.getHibernateTemplate().flush();
	}

	public User getUser(String login) {

		List res = this.getHibernateTemplate().find("from User where login=?",login);
		
		if (res.size() == 0)
			return null;
		return (User) res.get(0);
	}

	public User getUser(int id) {
		List res = this.getHibernateTemplate().find("from User where id=?", id);
		if (res.size() == 0)
			return null;
		return (User) res.get(0);
	}

	public List<User> getUsers() {
		return (List<User>) getHibernateTemplate().find("from User");
	}

	public List<User> getUsers(int userType) {
		return (List<User>) getHibernateTemplate().find("from User where type=?",userType );
	}

}
