package cn.com.dhcc.traps;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * @描述：模拟VMware发送trap
 * @作者：SZ
 * @时间：2015-10-26 上午11:28:01
 */
public class TestSendTrap {
	final static String IP = "192.168.0.197";// 对端IP

	final static String PORT = "162";
	private Snmp snmp = null;

	private Address targetAddress = null;

	private void test() {
		// 设置 target
		CommunityTarget target = new CommunityTarget();
		target.setAddress(targetAddress);

		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		// snmp版本
		target.setVersion(SnmpConstants.version1);

		// 创建 PDU
		PDUv1 pdu = new PDUv1();
		pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.6876.4.3.308.0"),
				new OctetString("3")));

		pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.304.0"),
				new OctetString("0xE7 0xBB 0xBF 0xE8 0x89 0xB2")));

		pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.307.0"),
				new OctetString("xiaoxuntong-DB-10.210.94.97")));

		pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.306.0"),
				new OctetString("0x43 0x50 0x55 0xE4 0xBD 0xBF 0xE7 0x94 0xA8 0xE7 0x8E 0x87 0xE9 0xAB 0x98 0x20 0x2D 0x20 0xE8 0xA1 0xA1 0xE9 0x87 0x8F 0xE6 0x8C 0x87 0xE6 0xA0 0x87 0x20 0xE4 0xBD 0xBF 0xE7 0x94 0xA8 0xE6 0x83 0x85 0xE5 0x86 0xB5 0x20 0x3D 0x20 0x38 0x34 0x25")));

		pdu.add(new VariableBinding(new OID("1.3.6.1.4.1.6876.4.3.305.0"),
				new OctetString("0xE9 0xBB 0x84 0xE8 0x89 0xB2")));

		pdu.setEnterprise(new OID("1.3.6.1.4.1.6876.4.3"));// 设置厂商Oid

		pdu.setType(PDU.V1TRAP);
		System.out.println(pdu);
		System.out.println("发送成功！");
		// 向Agent发送PDU，并接收Response
		try {
			ResponseEvent respEvnt = snmp.send(pdu, target);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initComm() throws IOException {
		// 设置管理进程的IP和端口
		targetAddress = GenericAddress.parse("udp:" + IP + "/" + PORT);
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		transport.listen();
	}

	public static void main(String[] args) {
		try {
			TestSendTrap util = new TestSendTrap();
			util.initComm();
			util.test();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
