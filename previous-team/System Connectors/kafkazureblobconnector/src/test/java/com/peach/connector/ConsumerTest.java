package com.peach.connector;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
public class ConsumerTest {

	Consumer consumer=null;
	@Before
	public void setUp() throws Exception {
		consumer= new Consumer();           
	}

	@Test
	public void testInitialize() {
		
       try {
		consumer.initialize();
		Properties prob=consumer.getProperties();
		assertEquals("localhost:9093",prob.getProperty("bootstrap.servers"));
		assertEquals("consumer-blob",prob.getProperty("group.id"));
		assertEquals("org.apache.kafka.common.serialization.StringDeserializer",prob.getProperty("key.deserializer"));
		assertEquals("org.apache.kafka.common.serialization.StringDeserializer",prob.getProperty("value.deserializer"));
		assertEquals("false",prob.getProperty("enable.auto.commit"));
		assertEquals("30000", prob.getProperty("session.timeout.ms"));
	} catch (IOException e) {
		e.printStackTrace();
	} 
	}

}
