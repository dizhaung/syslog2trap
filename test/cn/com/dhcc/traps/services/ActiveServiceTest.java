package cn.com.dhcc.traps.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActiveServiceTest {

	private ActiveAlarmService service;
	@Before
	public void setUp() throws Exception {
		this.service = new ActiveAlarmService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQuaryAllNonSended() {
		System.out.println(service.quaryAllNonSended());
}

	@Test
	public void testSendedActive() {
		fail("Not yet implemented"); // TODO
	}

}
