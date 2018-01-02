package cn.com.dhcc.traps.dao;

import java.util.List;

import org.hibernate.Session;

import cn.com.dhcc.commons.HibernateUtil;
import cn.com.dhcc.traps.models.ActiveAlarm;
import cn.com.dhcc.traps.models.CmoType;
import cn.com.dhcc.traps.models.SyslogRealTimeLog;

public class ActiveAlarmDao {
	public List< ActiveAlarm> quaryAllNonSended(){
		Session session = HibernateUtil.getCurrentSession();
		List< ActiveAlarm> alarms = session.createQuery("from ActiveAlarm s where s.flag=:flag").setParameter("flag", 0).list();
		for(ActiveAlarm alarm : alarms){
			CmoType cmoType = (CmoType)session.createQuery("from CmoType c where c.moType =:topMoType").setParameter("topMoType", alarm.getTopMoType()).uniqueResult();
			alarm.setCmoType(cmoType);
		}
		return alarms;
	}
	
	public void  update(ActiveAlarm alarm){
		Session session = HibernateUtil.getCurrentSession();
		
		 session.update(alarm);
	}
}
