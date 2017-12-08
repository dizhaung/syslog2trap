package cn.com.dhcc.traps.dao;

import java.util.List;

import org.hibernate.Session;

import cn.com.dhcc.commons.HibernateUtil;
import cn.com.dhcc.traps.models.SyslogRealTimeLog;

public class SyslogRealTimeLogDao {
	public List< SyslogRealTimeLog> quaryAllNonSended(){
		Session session = HibernateUtil.getCurrentSession();
		
		return session.createQuery("from SyslogRealTimeLog s where s.flag=:flag", SyslogRealTimeLog.class).setParameter("flag", 0).list();
	}
	
	public void  update(SyslogRealTimeLog log){
		Session session = HibernateUtil.getCurrentSession();
		
		 session.update(log);
	}
}
