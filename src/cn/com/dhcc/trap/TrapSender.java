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
 * ���������������̷���Trap��Ϣ 
 *  
 * @author HP 
 * 
 */  
public class TrapSender {  
  
	private final static String IP = "127.0.0.1";    //�Զ�IP
	public final static long SYS_UP_TIME = new Date().getTime();
	private final static String PORT = "162";
    private static  Snmp snmp = null;  
    
    private static  Address targetAddress = null;  
  
   static {  
  
        // ���ù�����̵�IP�Ͷ˿�  
        targetAddress = GenericAddress.parse("udp:"+IP+"/"+PORT);  
        try {
			snmp = new Snmp(new DefaultUdpTransportMapping());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }  
  
    /** 
     * �������̷���Trap���� 
     *  
     * @throws IOException 
     */  
    public static void sendPDU(PDUv1 pdu) throws IOException {  
  
        // ���� target  
        CommunityTarget target = new CommunityTarget();  
        target.setAddress(targetAddress);  
        target.setCommunity(new OctetString("public"));
        // ͨ�Ų��ɹ�ʱ�����Դ���  
        target.setRetries(2);  
        // ��ʱʱ��  
        target.setTimeout(1500);  
        // snmp�汾  
        target.setVersion(SnmpConstants.version1);  
       
        
        // ��Agent����PDU��������Response  
        ResponseEvent respEvnt = snmp.send(pdu, target);  
  
        // ����Response  
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