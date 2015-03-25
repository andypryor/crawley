package com.hudl.crawl.cassandra;

import com.datastax.driver.core.Cluster;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * 
 * Client used to connect to Cassandra database.
 * 
 * @author andrew.pryor
 */
@Singleton
public class CassandraClient 
{
	private Cluster cassandraCluster;
	
	@Inject
	public CassandraClient(Cluster cassandraCluster)
	{
		this.cassandraCluster = cassandraCluster;
	}
	
	public Cluster getCluster()
	{
		return cassandraCluster;
	}
}
