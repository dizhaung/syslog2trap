package cn.com.dhcc.trap;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/** 
 * 本类用于向管理进程发送Trap信息 
 *  
 * @author HP 
 * 
 */  
public class TrapSender {  
  
	private final static String IP = "127.0.0.1";    //对端IP
	public final static long SYS_UP_TIME = new Date().getTime();
	private final static String PORT = "162";
    private static  Snmp snmp = null;  
    
    private static  Address targetAddress = null;  
  
   static {  
  
        // 设置管理进程的IP和端口  
        targetAddress = GenericAddress.parse("udp:"+IP+"/"+PORT);  
        try {
			snmp = new Snmp(new DefaultUdpTransportMapping());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }  
  
    /** 
     * 向管理进程发送Trap报文 
     *  
     * @throws IOException 
     */  
    public static void sendPDU(PDUv1 pdu) throws IOException {  
  
        // 设置 target  
        CommunityTarget target = new CommunityTarget();  
        target.setAddress(targetAddress);  
        target.setCommunity(new OctetString("public"));
        // 通信不成功时的重试次数  
        target.setRetries(2);  
        // 超时时间  
        target.setTimeout(1500);  
        // snmp版本  
        target.setVersion(SnmpConstants.version1);  
       
        
        // 向Agent发送PDU，并接收Response  
        ResponseEvent respEvnt = snmp.send(pdu, target);  
  
        // 解析Response  
        if (respEvnt != null && respEvnt.getResponse() != null) {  
            Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse()  
            .getVariableBindings();  
            for (int i = 0; i < recVBs.size(); i++) {  
                VariableBinding recVB = recVBs.elementAt(i);  
                System.out.println(recVB.getOid() + " : " + recVB.getVariable());  
            }  
        }  
    }  
  
  
}  