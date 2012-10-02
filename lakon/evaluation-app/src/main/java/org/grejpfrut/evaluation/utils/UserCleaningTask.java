package org.grejpfrut.evaluation.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimerTask;

import org.grejpfrut.evaluation.dao.UsersManager;
import org.grejpfrut.evaluation.domain.User;

/**
 *
 * @author Adam Dudczak
 */
public class UserCleaningTask extends TimerTask {
	
	private UsersManager usersManager;

	@Override
	public void run() {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-2);
		Date date = calendar.getTime();
		
		List<User> users = usersManager.getUsers(User.INACTIVE_USER);
		for ( User user : users) {
			if ( user.getCreationDate().before(date) )
				this.usersManager.deleteUser(user.getId());
		}
		
	}

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

}
