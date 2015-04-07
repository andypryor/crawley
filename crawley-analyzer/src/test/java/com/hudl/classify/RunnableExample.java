package com.hudl.classify;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jukito.JukitoRunner;
import org.jukito.TestModule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.hudl.crawl.cassandra.CassandraClient;
import com.hudl.crawl.cassandra.CrawlerModule;

@RunWith(JukitoRunner.class)
public class RunnableExample 
{
	private static final String TRAIN_POSITIVE_DIR = "C:\\code\\java\\lead-finder\\coach-crawler\\training-urls-text";
	
	private static  final String TRAIN_NEGATIVE_DIR = "C:\\code\\java\\lead-finder\\coach-crawler\\junk-training-urls-text";

	@Test
    public void runClassify(CassandraClient client) throws Exception
    {
        final Classifier<String, String> bayes = new BayesClassifier<String, String>();
        
       	bayes.learn("contactPage", readDirectory(new File(TRAIN_POSITIVE_DIR)));
       	bayes.learn("notContactPage", readDirectory(new File(TRAIN_NEGATIVE_DIR)));
        

        /*
         * Now that the classifier has "learned" two classifications, it will
         * be able to classify similar sentences. The classify method returns
         * a Classification Object, that contains the given featureset,
         * classification probability and resulting category.
         */
        final String[] unknownText1 = "today is a sunny day".split("\\s");
        final String[] unknownText2 = "there will be rain".split("\\s");

        System.out.println( bayes.classify(Arrays.asList(unknownText1)).getCategory());
        System.out.println( bayes.classify(Arrays.asList(unknownText2)).getCategory());

		Session session = client.getCluster().connect();
		session.execute("use webpage;");
		ResultSet resultSet = session.execute("select * from p");
		List<Row> rows = resultSet.all();
		for(Row row : rows)
		{
			ByteBuffer buffer = row.getBytes("value");
			String value = getStringFromBytes(buffer);
			System.out.println(bayes.classify(Arrays.asList(value)).getCategory());
		}

        /*
         * Please note, that this particular classifier implementation will
         * "forget" learned classifications after a few learning sessions. The
         * number of learning sessions it will record can be set as follows:
         */
        bayes.setMemoryCapacity(500); // remember the last 500 learned classifications
    }
    
	public static List<String> readDirectory(File file) throws Exception
	{
		ArrayList<String> files = new ArrayList<String>();
		for(File f : file.listFiles())
		{
			files.add(readFile(f));
		}
		return files;
	}
    
	public static String readFile(File file) throws Exception
	{
		 byte[] encoded = Files.readAllBytes(Paths.get(file.toURI()));
		 return new String(encoded, "UTF-8");
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
