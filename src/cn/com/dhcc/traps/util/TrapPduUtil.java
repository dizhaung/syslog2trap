package cn.com.dhcc.traps.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.com.dhcc.traps.models.ActiveAlarm;
import cn.com.dhcc.traps.models.SyslogRealTimeLog;

public class TrapPduUtil {

	private final static Map<String, String> dynamicLevelCauseOidMap = new HashMap();
	private final static Map<String ,String > topLevelCauseOidMap = new HashMap();
	private final static Map<String,String> storageOidFragmentMap = new HashMap();
	static {
		storageOidFragmentMap.put("1509","12");//dell
		storageOidFragmentMap.put("1501","13");//emc
		storageOidFragmentMap.put("1508","14");//huawei
		storageOidFragmentMap.put("1502","15");//ibm
		storageOidFragmentMap.put("1503","16");//hp
		storageOidFragmentMap.put("1504","17");//hds
		storageOidFragmentMap.put("1505","18");//Network Appliance
		storageOidFragmentMap.put("1506","19");//同友
		storageOidFragmentMap.put("1507","20");		//日立
		
		dynamicLevelCauseOidMap.put("CPU利用率阀值越界", "1.3.6.1.4.1.6876.STORAGE.2.");
		dynamicLevelCauseOidMap.put("丢包率阀值越界", "1.3.6.1.4.1.6876.STORAGE.4.");
		dynamicLevelCauseOidMap.put("内存利用率阀值越界", "1.3.6.1.4.1.6876.STORAGE.5.");
		dynamicLevelCauseOidMap.put("错包率阀值越界", "1.3.6.1.4.1.6876.STORAGE.9.");
		dynamicLevelCauseOidMap.put("文件系统使用率阀值越界", "1.3.6.1.4.1.6876.STORAGE.13.");
		dynamicLevelCauseOidMap.put("物理卷使用率阀值越界", "1.3.6.1.4.1.6876.STORAGE.14.");
		dynamicLevelCauseOidMap.put("磁盘分区使用率阀值越界", "1.3.6.1.4.1.6876.STORAGE.15.");
		dynamicLevelCauseOidMap.put("表空间使用率阀值越界", "1.3.6.1.4.1.6876.STORAGE.17.");
		dynamicLevelCauseOidMap.put("响应时间阀值越界", "1.3.6.1.4.1.6876.STORAGE.18.");
		dynamicLevelCauseOidMap.put("电源传感器配置变更", "1.3.6.1.4.1.6876.STORAGE.20.");

		topLevelCauseOidMap.put("设备不能访问", "1.3.6.1.4.1.6876.STORAGE.1.4");
		topLevelCauseOidMap.put("ENT坏", "1.3.6.1.4.1.6876.STORAGE.3.4");
		topLevelCauseOidMap.put("电源坏", "1.3.6.1.4.1.6876.STORAGE.7.4");
		topLevelCauseOidMap.put("风扇坏", "1.3.6.1.4.1.6876.STORAGE.10.4");
		topLevelCauseOidMap.put("设备不可达", "1.3.6.1.4.1.6876.STORAGE.11.4");
		topLevelCauseOidMap.put("接口down", "1.3.6.1.4.1.6876.STORAGE.12.4");
		topLevelCauseOidMap.put("电源配置变更", "1.3.6.1.4.1.6876.STORAGE.21.4");
		topLevelCauseOidMap.put("电源传感器坏", "1.3.6.1.4.1.6876.STORAGE.19.4");
		topLevelCauseOidMap.put("链路:down", "1.3.6.1.4.1.6876.STORAGE.16.4");
		topLevelCauseOidMap.put("系统单元坏", "1.3.6.1.4.1.6876.STORAGE.8.4");
		topLevelCauseOidMap.put("接口状态变更", "1.3.6.1.4.1.6876.STORAGE.6.4");

	}

	public static List<PDUv1> toPdu(List<SyslogRealTimeLog> list) {
		List<PDUv1> pduList = new ArrayList();

		for (SyslogRealTimeLog log : list) {
			PDUv1 pdu = new PDUv1();
			pdu.setErrorIndex(0);
			pdu.setErrorStatus(0);
			pdu.setRequestID(new Integer32(0));
			// 加入设备启动时间
			pdu.setTimestamp(new Date().getTime() - TrapSender.SYS_UP_TIME);

			// 设定第二个变量Enterprise
			pdu.setEnterprise(new OID("1.3.6.1.4.1.6876.12.1.4"));
			try {
				pdu.setAgentAddress(new IpAddress(InetAddress.getLocalHost()));
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pdu.setGenericTrap(PDUv1.ENTERPRISE_SPECIFIC);
			try {
				String title = URLEncoder.encode("统一平台告警", "utf-8")
						.replaceAll("%", " 0x").replaceFirst(" ", "");
				pdu.add(new VariableBinding(new OID(
						"1.3.6.1.4.1.6876.4.3.301.0"), new OctetString(title)));

				pdu.add(new VariableBinding(new OID(
						"1.3.6.1.4.1.6876.4.3.302.0"), new OctetString(log
						.getIp())));

				String content = URLEncoder.encode(log.getMsg(), "utf-8")
						.replaceAll("%", " 0x").replaceFirst(" ", "");
				pdu.add(new VariableBinding(new OID(
						"1.3.6.1.4.1.6876.4.3.303.0"), new OctetString(content)));

				String bussinessSys = URLEncoder.encode("业务云平台网管系统", "utf-8")
						.replaceAll("%", " 0x").replaceFirst(" ", "");
				pdu.add(new VariableBinding(new OID(
						"1.3.6.1.4.1.6876.4.3.304.0"), new OctetString(
						bussinessSys)));

				pdu.add(new VariableBinding(new OID(
						"1.3.6.1.4.1.6876.4.3.308.0"), new Integer32(99)));
				pdu.add(new VariableBinding(new OID(
						"1.3.6.1.4.1.6876.4.3.309.0"), new OctetString("10")));

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			pdu.add(new VariableBinding(new OID(
					SnmpConstants.snmpTrapEnterprise.toDottedString()),
					new OID("1.3.6.1.4.1.6876.12")));

			pdu.setType(PDU.V1TRAP);

			pduList.add(pdu);
		}
		return pduList;
	}

	public static List<PDUv1> convertAlarmToPdu(List<ActiveAlarm> list) {
		List<PDUv1> pduList = new ArrayList();

		for (ActiveAlarm alarm : list) {
			
			String enterprise = generateEnterprise(alarm);
			if(!"".equals(enterprise)){
				PDUv1 pdu = new PDUv1();
				pdu.setErrorIndex(0);
				pdu.setErrorStatus(0);
				pdu.setRequestID(new Integer32(0));
				// 加入设备启动时间
				pdu.setTimestamp(new Date().getTime() - TrapSender.SYS_UP_TIME);

				// 设定第二个变量Enterprise
				pdu.setEnterprise(new OID(enterprise));
				try {
					pdu.setAgentAddress(new IpAddress(InetAddress.getLocalHost()));
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				pdu.setGenericTrap(PDUv1.ENTERPRISE_SPECIFIC);
				try {
					String title = URLEncoder.encode(alarm.getCauseWithSeverity(), "utf-8")
							.replaceAll("%", " 0x").replaceFirst("^ ", "");
					pdu.add(new VariableBinding(new OID(
							"1.3.6.1.4.1.6876.4.3.301.0"), new OctetString(title)));

					pdu.add(new VariableBinding(new OID(
							"1.3.6.1.4.1.6876.4.3.302.0"), new OctetString(alarm
							.getMoIp())));

					String content = URLEncoder.encode(alarm.getDetail(), "utf-8")
							.replaceAll("%", " 0x").replaceFirst("^ ", "");
					pdu.add(new VariableBinding(new OID(
							"1.3.6.1.4.1.6876.4.3.303.0"), new OctetString(content)));

					String bussinessSys = URLEncoder.encode("业务云平台网管系统", "utf-8")
							.replaceAll("%", " 0x").replaceFirst("^ ", "");
					pdu.add(new VariableBinding(new OID(
							"1.3.6.1.4.1.6876.4.3.304.0"), new OctetString(
							bussinessSys)));

					pdu.add(new VariableBinding(new OID(
							"1.3.6.1.4.1.6876.4.3.308.0"), new Integer32(99)));
					pdu.add(new VariableBinding(new OID(
							"1.3.6.1.4.1.6876.4.3.309.0"), new OctetString("10")));

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				pdu.add(new VariableBinding(new OID(
						SnmpConstants.snmpTrapEnterprise.toDottedString()),
						new OID("1.3.6.1.4.1.6876.12")));

				pdu.setType(PDU.V1TRAP);

				pduList.add(pdu);
			}
			
		}
		return pduList;
	}

	private static String generateEnterprise(ActiveAlarm alarm) {
		Set<String> dynamicLevelCauses = dynamicLevelCauseOidMap.keySet();
		String almarCause = alarm.getCause().trim();
		String moType = alarm.getCmoType().getMoType();
		
		for(String cause :dynamicLevelCauses){
			if(almarCause.contains(cause)){
				int severity = alarm.getSeverity()-1;
				String oid =  dynamicLevelCauseOidMap.get(cause)+severity;
				
				//告警原因带级别，比如：CPU利用率阀值越界1级
				alarm.setCauseWithSeverity(almarCause+severity+"级");
				return generateStorageOid(oid,moType);
			}
		}
		
		Set<String> topLevelCauses = topLevelCauseOidMap.keySet();
		
		for(String cause: topLevelCauses){
			if(almarCause.contains(cause)){
				String oid =   topLevelCauseOidMap.get(cause);
				return generateStorageOid(oid,moType);
			}
		}
		return "";
	}
	private static String generateStorageOid(String oid,String moType){
		String oidFragment = storageOidFragmentMap.get(moType);
		if(oidFragment == null) return "";
		return oid.replaceFirst("STORAGE", oidFragment);
	}
}
