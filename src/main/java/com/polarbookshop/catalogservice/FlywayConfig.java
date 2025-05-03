package com.polarbookshop.catalogservice;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableConfigurationProperties({ DataSourceProperties.class, FlywayProperties.class })
public class FlywayConfig {

	@Bean
	@Primary
	public DataSource dataSource(DataSourceProperties properties) {
		String url= properties.getUrl();
		url= url.replaceAll("polardb_catalog", "postgres");
		DataSource initialDataSource = DataSourceBuilder.create().url(url)
				.username(properties.getUsername()).password(properties.getPassword()).build();

		JdbcTemplate jdbc = new JdbcTemplate(initialDataSource);
		if (!databaseExists(jdbc, "polardb_catalog")) {
			jdbc.execute("CREATE DATABASE polardb_catalog");
		}

		return DataSourceBuilder.create().url(properties.getUrl()).username(properties.getUsername())
				.password(properties.getPassword()).build();
	}

	@Bean
	public Flyway flyway(DataSource dataSource, FlywayProperties properties) {
		Flyway flyway = Flyway.configure().dataSource(dataSource)
				.schemas(properties.getSchemas().toArray(new String[0]))
				.locations(properties.getLocations().toArray(new String[0]))
				.baselineOnMigrate(properties.isBaselineOnMigrate()).load();

		flyway.migrate();
		return flyway;
	}

	private boolean databaseExists(JdbcTemplate jdbc, String dbName) {
		return Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM pg_database WHERE datname = ?)",
				Boolean.class, dbName));
	}
}