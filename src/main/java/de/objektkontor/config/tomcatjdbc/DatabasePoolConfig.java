package de.objektkontor.config.tomcatjdbc;

import org.apache.tomcat.jdbc.pool.DataSource;

import de.objektkontor.config.annotation.ConfigParameter;
import de.objektkontor.config.common.DBConfig;

public class DatabasePoolConfig extends DBConfig {

	@ConfigParameter private TomcatJdbcPoolConfig pool;

	public TomcatJdbcPoolConfig getPool() {
		return pool;
	}

	public void setPool(TomcatJdbcPoolConfig pool) {
		this.pool = pool;
	}

	public void applyTo(DataSource dataSource) {
		TomcatJdbcPoolConfig.applyJdbcConfig(this, dataSource);
		pool.applyTo(dataSource);
	}
}
