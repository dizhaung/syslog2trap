package cn.com.dhcc.traps.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.VariableBinding;

import cn.com.dhcc.traps.TrapSender;
import cn.com.dhcc.traps.models.SyslogRealTimeLog;

public class Syslog2TrapPdu {

	public static List<PDUv1> toPdu(List< SyslogRealTimeLog> list){
		List<PDUv1> pduList = new ArrayList();
		
		for(SyslogRealTimeLog log:list){
			PDUv1 pdu = new PDUv1();
	       pdu.setErrorIndex(0);
	        pdu.setErrorStatus(0);
	        pdu.setRequestID(new Integer32(0));
	        //加入设备启动时间
	        pdu.setTimestamp(new Date().getTime()- TrapSender.SYS_UP_TIME);
	      
			//设定第二个变量Enterprise
	        pdu.setEnterprise(new OID("1.3.6.1.4.1.6876.12.1.4"));
	        try {
				pdu.setAgentAddress(new IpAddress(InetAddress.getLocalHost()));
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        pdu.setGenericTrap(PDUv1.ENTERPRISE_SPECIFIC);
			try {
				String title = URLEncoder.encode("统一平台告警", "utf-8").replaceAll("%", " 0x").replaceFirst(" ", "");
				pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.301.0"),  
		                new OctetString(title)));  

				 pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.302.0"),  
			                new OctetString(log.getIp())));  
			       
				 String content = URLEncoder.encode(log.getMsg(), "utf-8").replaceAll("%", " 0x").replaceFirst(" ", "");
				 pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.303.0"),  
			                new OctetString(content)));  
			     
				String bussinessSys = URLEncoder.encode("存储告警", "utf-8").replaceAll("%", " 0x").replaceFirst(" ", "");
		        pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.304.0"),  
		                new OctetString(bussinessSys)));  
		        
		        pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.308.0"),  
		                    new Integer32(99)));  
		        pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.309.0"),  
		                new OctetString("10")));  
		        
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		  
	    	pdu.add(new VariableBinding(new OID( SnmpConstants.snmpTrapEnterprise.toDottedString()),  
	                        new OID("1.3.6.1.4.1.6876.12")));  
			
	    	pdu.setType(PDU.V1TRAP);  
	        
	        pduList.add(pdu);
		}
		return pduList;
	}
}
