package cn.com.dhcc.commons;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = (new Configuration()).configure().buildSessionFactory();

	/**
	 * @return
	 * @throws HibernateException
	 * @see org.hibernate.SessionFactory#getCurrentSession()
	 */
	public static Session getCurrentSession() throws HibernateException {
		return sessionFactory.getCurrentSession();
	}

}
