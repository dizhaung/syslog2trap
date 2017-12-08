package cn.com.dhcc.traps.services;

import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.dhcc.commons.HibernateUtil;
import cn.com.dhcc.traps.dao.SyslogRealTimeLogDao;
import cn.com.dhcc.traps.models.SyslogRealTimeLog;

public class SyslogRealTimeLogService {
	
	private SyslogRealTimeLogDao dao;
	
	/**
	 * @param dao
	 */
	public SyslogRealTimeLogService() {
		super();
		this.dao = new SyslogRealTimeLogDao();
	}

	public List< SyslogRealTimeLog> quaryAllNonSended(){
		Transaction transaction = HibernateUtil.getCurrentSession().beginTransaction();
		try{
			List<SyslogRealTimeLog> list = dao.quaryAllNonSended();
			transaction.commit();
			return list;
		}catch(HibernateException e){
			transaction.rollback();
			e.printStackTrace();
		}
		return Collections.EMPTY_LIST;
	}
	

	public void sended(SyslogRealTimeLog log){
		Transaction transaction = HibernateUtil.getCurrentSession().beginTransaction();
		try{
			dao.update(log);
			transaction.commit();
		}catch(HibernateException e){
			transaction.rollback();
			e.printStackTrace();
		}
	}
	public void sended(List<SyslogRealTimeLog> logs){
		Transaction transaction = HibernateUtil.getCurrentSession().beginTransaction();
		try{
			for(SyslogRealTimeLog log:logs){
				log.setFlag(1);
				dao.update(log);
			}
			transaction.commit();
		}catch(HibernateException e){
			transaction.rollback();
			e.printStackTrace();
		}
	}
}
