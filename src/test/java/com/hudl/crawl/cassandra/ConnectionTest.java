package com.hudl.crawl.cassandra;

import static org.junit.Assert.assertTrue;

import org.jukito.JukitoRunner;
import org.jukito.TestModule;
import org.junit.Test;
import org.junit.runner.RunWith;

/***
 * 
 * @author andrew.pryor
 */
@RunWith(JukitoRunner.class)
public class ConnectionTest 
{
	@Test
	public void testConnect(CassandraClient client)
	{
		assertTrue(client != null);
	}
	
	public static class Module extends TestModule
	{
		@Override
		protected void configureTest() 
		{
			install(new CrawlerModule());
		}
	}
}
