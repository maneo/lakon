package org.grejpfrut.evaluation.dao;

import java.sql.SQLException;
import java.util.List;

import org.grejpfrut.evaluation.domain.Text;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Simple DAO for accessing {@link Text}s.
 * 
 * @author Adam Dudczak
 * 
 */
public class TextsManagerImpl extends HibernateDaoSupport implements
		TextsManager {

	public void saveText(String text, String title) {
		Text art = new Text(title, text);
		this.getHibernateTemplate().saveOrUpdate(art);
	}

	public void deleteText(int id) {
		Text art = this.getText(id);
		this.getHibernateTemplate().delete(art);
	}

	public Text getText(int id) {
		List res = this.getHibernateTemplate().find("from Text where id=?", id);
		if (res.size() == 0)
			return null;
		return (Text) res.get(0);
	}

	public void saveText(Text art) {
		this.getHibernateTemplate().saveOrUpdate(art);
	}

	public List<Text> getTexts() {
		return (List<Text>) this.getHibernateTemplate().find("from Text");
	}

	/**
	 * Gets all text except those whose ids are in given array.
	 */
	public List<Text> getTexts(Integer[] exceptThisIds) {
		if (exceptThisIds.length > 0) {
			// TODO : this one stinks
			StringBuffer sb = new StringBuffer();
			sb.append(exceptThisIds[0]);
			for (int i = 1; i < exceptThisIds.length; i++)
				sb.append("," + exceptThisIds[i]);
			return this.getHibernateTemplate().find(
					"from Text where id not in (" + sb.toString() + ") order by evaluationCount asc");
		}
		return this.getHibernateTemplate().find(
				"from Text order by evaluationCount asc");
	}

	/**
	 * Counts texts stored in db.
	 */
	public int countTexts() {
		Integer res = (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return ((Integer) session.createQuery(
								"select count(*) from Text").iterate().next())
								.intValue();
					}
				});
		return res;
	}


}
