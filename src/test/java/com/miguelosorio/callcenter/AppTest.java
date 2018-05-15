package com.miguelosorio.callcenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	@InjectMocks
	private Dispatcher dispatcher;
	
	@Mock
	private CallGenerator generator;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSingleDispatcherForTenCalls() throws InterruptedException {
		new CallGenerator().start(10);
		Thread.currentThread().sleep(500);
		
		new Dispatcher(1).dispatchCall();
	}
}
