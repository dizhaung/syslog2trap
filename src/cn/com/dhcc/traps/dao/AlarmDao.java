package cn.com.dhcc.traps.dao;

import java.util.List;

import org.hibernate.Session;

import cn.com.dhcc.commons.HibernateUtil;
import cn.com.dhcc.traps.models.Alarm;
import cn.com.dhcc.traps.models.CmoType;
import cn.com.dhcc.traps.models.SyslogRealTimeLog;

public class AlarmDao {
	public List< Alarm> quaryAllNonSended(){
		Session session = HibernateUtil.getCurrentSession();
		List< Alarm> alarms = session.createQuery("from Alarm s where s.flag=:flag").setParameter("flag", (byte)0).list();
		for(Alarm alarm : alarms){
			
//			需要从motype取得设备厂商部分,然后从tcomtype表获得关联的cmotype厂商的详细信息
			CmoType cmoType = (CmoType)session.createQuery("from CmoType c where c.moType =:topMoType").setParameter("topMoType", alarm.getTopMoType()).uniqueResult();
			alarm.setCmoType(cmoType);
		}
		return alarms;
	}
	
	public void  update(Alarm alarm){
		Session session = HibernateUtil.getCurrentSession();
		
		 session.update(alarm);
	}
}
