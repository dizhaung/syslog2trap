package cn.com.dhcc.traps.dao;

import java.util.List;

import org.hibernate.Session;

import cn.com.dhcc.commons.HibernateUtil;
import cn.com.dhcc.traps.models.ActiveAlarm;
import cn.com.dhcc.traps.models.SyslogRealTimeLog;

public class ActiveAlarmDao {
	public List< ActiveAlarm> quaryAllNonSended(){
		Session session = HibernateUtil.getCurrentSession();
		
		return session.createQuery("from ActiveAlarm s where s.flag=:flag", ActiveAlarm.class).setParameter("flag", 0).list();
	}
	
	public void  update(ActiveAlarm alarm){
		Session session = HibernateUtil.getCurrentSession();
		
		 session.update(alarm);
	}
}
