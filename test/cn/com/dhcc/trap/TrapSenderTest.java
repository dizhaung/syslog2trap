package cn.com.dhcc.trap;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;

public class TrapSenderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendPDU() throws IOException {
        // ´´½¨ PDU  
        PDUv1 pdu = new PDUv1();
        pdu.setAgentAddress(new IpAddress(InetAddress.getLocalHost()));
        pdu.setEnterprise(new OID("1.3.6.1.4.1.6876.888.1.1"));
        pdu.setTimestamp(0);
        pdu.setType(PDU.V1TRAP);
        pdu.setGenericTrap(PDUv1.ENTERPRISE_SPECIFIC);
        pdu.setSpecificTrap(203);
        
        TrapSender.sendPDU(pdu);
	}

}
