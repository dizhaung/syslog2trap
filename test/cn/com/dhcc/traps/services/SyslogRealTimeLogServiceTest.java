package cn.com.dhcc.traps.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SyslogRealTimeLogServiceTest {

	private SyslogRealTimeLogService service;
	@Before
	public void setUp() throws Exception {
		service = new SyslogRealTimeLogService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQuaryAllNonSended() {
		System.out.println(service.quaryAllNonSended());
	}

}
