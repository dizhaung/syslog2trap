package cn.com.dhcc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.mp.SnmpConstants;

import cn.com.dhcc.traps.TrapSender;
import cn.com.dhcc.traps.models.ActiveAlarm;
import cn.com.dhcc.traps.models.SyslogRealTimeLog;
import cn.com.dhcc.traps.services.ActiveAlarmService;
import cn.com.dhcc.traps.services.SyslogRealTimeLogService;
import cn.com.dhcc.traps.util.TrapPduUtil;

public class MainTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMain() {
		ActiveAlarmService service = new ActiveAlarmService();
		TrapSender.SYS_UP_TIME = new Date().getTime();
		List< ActiveAlarm> logs = service.quaryAllNonSended();
		List<PDUv1> pdus = TrapPduUtil.convertAlarmToPdu(logs);
		
		TrapSender.sendPDU(pdus);
	    service.sended(logs);
	}
	
	@Test
	public void testURLEncode(){
		try {
			String encodedStr = URLEncoder.encode("127.0.0.1还可以", "utf-8").replaceAll("%", " 0x").replaceFirst("$ ", "");
		
			System.out.println(URLDecoder.decode((" "+encodedStr).replace(" 0x", "%"),"utf-8")); ;
			
				
		

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
