package cn.com.dhcc.traps.services;

import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.dhcc.commons.HibernateUtil;
import cn.com.dhcc.traps.dao.AlarmDao;
import cn.com.dhcc.traps.dao.SyslogRealTimeLogDao;
import cn.com.dhcc.traps.models.Alarm;
import cn.com.dhcc.traps.models.SyslogRealTimeLog;

public class AlarmService {
	
	private AlarmDao dao;
	
	/**
	 * @param dao
	 */
	public AlarmService() {
		super();
		this.dao = new AlarmDao();
	}

	public List< Alarm> quaryAllNonSended(){
		Transaction transaction = HibernateUtil.getCurrentSession().beginTransaction();
		try{
			List<Alarm> list = dao.quaryAllNonSended();
			transaction.commit();
			return list;
		}catch(HibernateException e){
			transaction.rollback();
			e.printStackTrace();
		}
		return Collections.EMPTY_LIST;
	}
	

	public void sended( Alarm log){
		Transaction transaction = HibernateUtil.getCurrentSession().beginTransaction();
		try{
			dao.update(log);
			transaction.commit();
		}catch(HibernateException e){
			transaction.rollback();
			e.printStackTrace();
		}
	}
	public void sended(List< Alarm> logs){
		Transaction transaction = HibernateUtil.getCurrentSession().beginTransaction();
		try{
			for( Alarm log:logs){
				log.setFlag((byte)1);
				dao.update(log);
			}
			transaction.commit();
		}catch(HibernateException e){
			transaction.rollback();
			e.printStackTrace();
		}
	}
}
