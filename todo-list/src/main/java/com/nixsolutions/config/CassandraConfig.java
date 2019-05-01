package com.nixsolutions.config;

import static java.util.Collections.singletonList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import com.datastax.driver.core.PlainTextAuthProvider;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
	@Value("${cassandra.entity.package}")
	private String ENTITY_PACKAGE;
	@Value("${spring.data.cassandra.keyspace-name}")
	private String KEYSPACE;
	@Value("${spring.data.cassandra.username}")
	private String USERNAME;
	@Value("${spring.data.cassandra.password}")
	private String PASSWORD;

	@Override
	protected String getKeyspaceName() {
		return KEYSPACE;
	}

	@Bean
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setAuthProvider(new PlainTextAuthProvider(USERNAME, PASSWORD));
		cluster.setUsername(USERNAME);
		cluster.setPassword(PASSWORD);
		cluster.setKeyspaceCreations(singletonList(CreateKeyspaceSpecification.createKeyspace(KEYSPACE).ifNotExists()
				.with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication()));
		return cluster;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { ENTITY_PACKAGE };
	}
}
