package com.hudl.crawl.cassandra;

import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;

import org.jukito.JukitoRunner;
import org.jukito.TestModule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/***
 * 
 * @author andrew.pryor
 */
@RunWith(JukitoRunner.class)
public class QueryCassandra 
{
	@Test
	public void queryCassandra(CassandraClient client)
	{
		Session session = client.getCluster().connect();
		session.execute("use webpage;");
		ResultSet resultSet = session.execute("select * from f limit 50");
		List<Row> rows = resultSet.all();
		int i = 0;
		for(Row row : rows)
		{
			ByteBuffer buffer = row.getBytes("key");
			System.out.println(getStringFromBytes(buffer));
		}
	}
	
	 public static String unreverseUrl(String reversedUrl) {
		     StringBuilder buf = new StringBuilder(reversedUrl.length() + 2);
		 
		     int pathBegin = reversedUrl.indexOf('/');
		     if (pathBegin == -1)
		       pathBegin = reversedUrl.length();
		     String sub = reversedUrl.substring(0, pathBegin);
		 
		     String[] splits = sub.split(":"); // {<reversed host>, <port>, <protocol>}
		 
		     buf.append(splits[1]); // add protocol
		     buf.append("://");
		     reverseAppendSplits(splits[0].split("\\."), buf); // splits[0] is reversed
		     // host
		     if (splits.length == 3) { // has a port
		       buf.append(':');
		       buf.append(splits[2]);
		     }
		     buf.append(reversedUrl.substring(pathBegin));
		     return buf.toString();
		}
	 
		 private static void reverseAppendSplits(String[] splits, StringBuilder buf) 
		 {
			     for (int i = splits.length - 1; i > 0; i--) {
			       buf.append(splits[i]);
			       buf.append('.');
			     }
			     buf.append(splits[0]);
		 }
	
	private static String getStringFromBytes(ByteBuffer buffer )
	{
		int length = buffer.limit() - buffer.position();
		byte[] cellValue = new byte[length];
		buffer.get(cellValue, 0, length);
		return new String(cellValue, Charset.forName("UTF-8"));
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
