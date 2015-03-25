package com.hudl.crawl.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * 
 * Instantiates a CassandraClient
 * 
 * @author andrew.pryor
 */
public class CassandraClientProvider implements Provider<CassandraClient>
{
	@Inject
	@Named("cassandraNodeIp")
	private String node;
	
	@Override
	public CassandraClient get() 
	{
		Cluster cluster = Cluster.builder()
						.addContactPoint(node)
						.build();
		
		System.out.println("Cassandra Connected to Cluster " + cluster.getMetadata().getClusterName());
		
		return new CassandraClient(cluster);
	}

}
