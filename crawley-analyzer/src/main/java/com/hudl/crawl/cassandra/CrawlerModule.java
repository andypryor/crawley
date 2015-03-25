package com.hudl.crawl.cassandra;

import java.io.IOException;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * 
 * Guice module for crawler.
 * 
 * @author andrew.pryor
 */
public class CrawlerModule extends AbstractModule
{

	@Override
	protected void configure() 
	{
		bind(CassandraClient.class).toProvider(CassandraClientProvider.class);
		
		Properties crawlerProperties = new Properties();
		try 
		{
			crawlerProperties.load(CrawlerModule.class.getClassLoader().getResourceAsStream("crawler.properties"));
			Names.bindProperties(binder(), crawlerProperties);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
