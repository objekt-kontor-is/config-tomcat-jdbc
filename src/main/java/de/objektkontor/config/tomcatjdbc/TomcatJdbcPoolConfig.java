package de.objektkontor.config.tomcatjdbc;

import java.sql.Connection;
import java.time.Duration;

import org.apache.tomcat.jdbc.pool.DataSource;

import de.objektkontor.config.ObservableConfig;
import de.objektkontor.config.annotation.ConfigParameter;
import de.objektkontor.config.common.DBConfig;

public class TomcatJdbcPoolConfig extends ObservableConfig {

	public enum TransactionIsolation {

		NONE(Connection.TRANSACTION_NONE),
		READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
		REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
		READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
		SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

		private final int constantValue;

		private TransactionIsolation(int constantValue) {
			this.constantValue = constantValue;
		}

		public int getConstantValue() {
			return constantValue;
		}
	}

	// ------------------------------------------------------------------------------------
	// dimensions
	// ------------------------------------------------------------------------------------
	@ConfigParameter(description = ""
			+ "The initial number of connections that are created when the pool is started") private int initialSize = 10;

	@ConfigParameter(description = ""
			+ "The maximum number of active connections that can be allocated from this pool at the same time") private int maxActive = 100;

	@ConfigParameter(description = ""
			+ "The minimum number of established connections that should be kept in the pool at all times") private int minIdle = 10;

	@ConfigParameter(description = ""
			+ "The maximum number of connections that should be kept in the pool at all times") private int maxIdle = 100;

	// ------------------------------------------------------------------------------------
	// service functions
	// ------------------------------------------------------------------------------------
	@ConfigParameter(description = ""
			+ "The maximum number of milliseconds that the pool will wait (when there are no available connections) "
			+ "for a connection to be returned before throwing an exception") private Duration maxWait = Duration.ofSeconds(30);

	@ConfigParameter(description = ""
			+ "Sets the maximum time in seconds that this data source will waie while attempting to connect to a database "
			+ "A value of zero specifies that the timeout is the default system timeout "
			+ "if there is one; otherwise, it specifies that there is no timeout") private Duration loginTimeout = Duration.ofSeconds(0);

	@ConfigParameter(description = ""
			+ "The indication of whether objects will be validated before being borrowed from the pool. "
			+ "If the object fails to validate, it will be dropped from the pool, and we will attempt to borrow another") private boolean testOnBorrow = false;

	@ConfigParameter(description = ""
			+ "The indication of whether objects will be validated when a connection is first created. "
			+ "If an object fails to validate, it will be throw SQLException") private boolean testOnConnect = false;

	@ConfigParameter(description = ""
			+ "The indication of whether objects will be validated before being returned to the pool") private boolean testOnReturn = false;

	@ConfigParameter(description = ""
			+ "The indication of whether objects will be validated by the idle object evictor (if any). "
			+ "If an object fails to validate, it will be dropped from the pool") private boolean testWhileIdle;

	@ConfigParameter(description = ""
			+ "Register the pool with JMX or not") private boolean jmxEnabled = true;

	@ConfigParameter(description = ""
			+ "Set to true if you wish that calls to getConnection should be treated fairly in a true FIFO fashion. "
			+ "This uses the org.apache.tomcat.jdbc.pool.FairBlockingQueue implementation for the list of the idle connections. "
			+ "This flag is required when you want to use asynchronous connection retrieval. "
			+ "Setting this flag ensures that threads receive connections in the order they arrive") private boolean fairQueue = true;

	// ------------------------------------------------------------------------------------
	// connections functions
	// ------------------------------------------------------------------------------------
	@ConfigParameter(description = ""
			+ "The connection properties that will be sent to our JDBC driver when establishing new connections. "
			+ "Format of the string must be [propertyName=property;]") private String connectionProperties; // not set

	@ConfigParameter(description = ""
			+ "A custom query to be run when a connection is first created") private String initSQL; // not set

	@ConfigParameter(description = ""
			+ "The default auto-commit state of connections created by this pool. "
			+ "If not set, default is JDBC driver default (the setAutoCommit method will not be called)") private Boolean defaultAutoCommit; // not set

	@ConfigParameter(description = ""
			+ "The default read-only state of connections created by this pool. If not set then the setReadOnly method will not be called") private Boolean defaultReadOnly; // not set

	@ConfigParameter(description = ""
			+ "The default TransactionIsolation state of connections created by this pool. "
			+ "If not set, the method will not be called and it defaults to the JDBC driver") private TransactionIsolation defaultTransactionIsolation; // not set

	@ConfigParameter(description = ""
			+ "The default catalog of connections created by this pool") private String defaultCatalog; // not set

	@ConfigParameter(description = ""
			+ "If autoCommit==false then the pool can terminate the transaction by calling rollback "
			+ "on the connection as it is returned to the pool") private boolean rollbackOnReturn;

	@ConfigParameter(description = ""
			+ "If autoCommit==false then the pool can complete the transaction by "
			+ "calling commit on the connection as it is returned to the pool. "
			+ "If rollbackOnReturn==true then this attribute is ignored") private boolean commitOnReturn;

	// ------------------------------------------------------------------------------------
	// connections validation
	// ------------------------------------------------------------------------------------
	@ConfigParameter(description = ""
			+ "The SQL query that will be used to validate connections from this pool before returning them to the caller") private String validationQuery; // not set

	@ConfigParameter(description = ""
			+ "Avoid excess validation, only run validation at most at this frequency. "
			+ "If a connection is due for validation, but has been validated previously within this interval, it will not be validated again") private Duration validationInterval = Duration
					.ofSeconds(30);

	@ConfigParameter(description = ""
			+ "The timeout before a connection validation queries fail") private Duration validationQueryTimeout = Duration.ofSeconds(-1); // disabled

	@ConfigParameter(description = ""
			+ "Set this to true to log errors during the validation phase to the log file") private boolean logValidationErrors = false;

	// ------------------------------------------------------------------------------------
	// connections termination
	// ------------------------------------------------------------------------------------
	@ConfigParameter(description = ""
			+ "Time to keep the connection. "
			+ "When a connection is returned to the pool, the pool will check to see if the now - time-when-connected > maxAge has been reached, "
			+ "and if so, it closes the connection rather than returning it to the pool. "
			+ "0 means that connections will be left open and no age check will be done upon returning the connection to the pool") private Duration maxAge = Duration.ofSeconds(0); // disabled

	@ConfigParameter(description = ""
			+ "Time to sleep between runs of the idle connection validation/cleaner thread. "
			+ "This value should not be set under 1 second. It dictates how often we check for idle, "
			+ "abandoned connections, and how often we validate idle connections") private Duration timeBetweenEvictionRuns = Duration.ofSeconds(5);

	@ConfigParameter(description = ""
			+ "The minimum amount of time an object may sit idle in the pool before it is eligible for eviction") private Duration minEvictableIdleTime = Duration.ofSeconds(60);

	@ConfigParameter(description = ""
			+ "Flag to log stack traces for application code which abandoned a Connection. "
			+ "Logging of abandoned Connections adds overhead for every Connection borrow because a stack trace has to be generated") private boolean logAbandoned = false;

	@ConfigParameter(description = ""
			+ "Flag to remove abandoned connections if they exceed the removeAbandonedTimeout. "
			+ "If set to true a connection is considered abandoned and eligible for removal if it has been "
			+ "in use longer than the removeAbandonedTimeout. "
			+ "Setting this to true can recover db connections from applications that fail to close a connection") private boolean removeAbandoned = false;

	@ConfigParameter(description = ""
			+ "Timeout before an abandoned(in use) connection can be removed. "
			+ "The value should be set to the longest running query your applications might have") private Duration removeAbandonedTimeout = Duration.ofSeconds(60);

	@ConfigParameter(description = ""
			+ "Connections that have been abandoned (timed out) wont get closed and reported up unless the number of "
			+ "connections in use are above the percentage defined by abandonWhenPercentageFull. "
			+ "The value should be between 0-100. 0 means that connections are eligible for closure as soon as removeAbandonedTimeout has been reached") private int abandonWhenPercentageFull = 0;

	@ConfigParameter(description = ""
			+ "Timeout Similar to to the removeAbandonedTimeout value but instead of treating the connection as abandoned, "
			+ "this simply logs the warning if logAbandoned is set to true. "
			+ "If this value is equal or less than 0, no suspect checking will be performed. "
			+ "Suspect checking only takes place if the timeout value is larger than 0 and the connection was not abandoned or if abandon check is disabled. "
			+ "If a connection is suspect a WARN message gets logged and a JMX notification gets sent once") private Duration suspectTimeout = Duration.ofSeconds(0); // disabled

	/**
	 * Configures specified data source instance
	 *
	 * @param dataSource
	 */
	public void applyTo(DataSource dataSource) {
		// dimensions
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxActive(maxActive);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxIdle(maxIdle);
		// service functions
		dataSource.setMaxWait((int) maxWait.toMillis());
		dataSource.setLoginTimeout((int) loginTimeout.getSeconds());
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnConnect(testOnConnect);
		dataSource.setTestOnReturn(testOnReturn);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setJmxEnabled(jmxEnabled);
		dataSource.setFairQueue(fairQueue);
		// connection functions
		if (connectionProperties != null) {
			dataSource.setConnectionProperties(connectionProperties);
		}
		if (initSQL != null) {
			dataSource.setInitSQL(initSQL);
		}
		if (defaultAutoCommit != null) {
			dataSource.setDefaultAutoCommit(defaultAutoCommit);
		}
		if (defaultReadOnly != null) {
			dataSource.setDefaultReadOnly(defaultReadOnly);
		}
		if (defaultTransactionIsolation != null) {
			dataSource.setDefaultTransactionIsolation(defaultTransactionIsolation.getConstantValue());
		}
		if (defaultCatalog != null) {
			dataSource.setDefaultCatalog(defaultCatalog);
		}
		dataSource.setRollbackOnReturn(rollbackOnReturn);
		dataSource.setCommitOnReturn(commitOnReturn);
		// connections validation
		if (validationQuery != null) {
			dataSource.setValidationQuery(validationQuery);
		}
		dataSource.setValidationInterval(validationInterval.toMillis());
		dataSource.setValidationQueryTimeout((int) validationQueryTimeout.getSeconds());
		dataSource.setLogValidationErrors(logValidationErrors);
		// connections termination
		dataSource.setMaxAge(maxAge.toMillis());
		dataSource.setTimeBetweenEvictionRunsMillis((int) timeBetweenEvictionRuns.toMillis());
		dataSource.setMinEvictableIdleTimeMillis((int) minEvictableIdleTime.toMillis());
		dataSource.setLogAbandoned(logAbandoned);
		dataSource.setRemoveAbandoned(removeAbandoned);
		dataSource.setRemoveAbandonedTimeout((int) removeAbandonedTimeout.getSeconds());
		dataSource.setAbandonWhenPercentageFull(abandonWhenPercentageFull);
		dataSource.setSuspectTimeout((int) suspectTimeout.getSeconds());
	}

	/**
	 * Untility function to apply external db config to data source
	 *
	 * @param dbConfig
	 * @param dataSource
	 */
	public static void applyJdbcConfig(DBConfig dbConfig, DataSource dataSource) {
		dataSource.setDriverClassName(dbConfig.getDriver());
		dataSource.setUrl(dbConfig.getUrl());
		dataSource.setUsername(dbConfig.getUser());
		dataSource.setPassword(dbConfig.getPassword());
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Duration getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Duration maxWait) {
		this.maxWait = maxWait;
	}

	public Duration getLoginTimeout() {
		return loginTimeout;
	}

	public void setLoginTimeout(Duration loginTimeout) {
		this.loginTimeout = loginTimeout;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnConnect() {
		return testOnConnect;
	}

	public void setTestOnConnect(boolean testOnConnect) {
		this.testOnConnect = testOnConnect;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isJmxEnabled() {
		return jmxEnabled;
	}

	public void setJmxEnabled(boolean jmxEnabled) {
		this.jmxEnabled = jmxEnabled;
	}

	public boolean isFairQueue() {
		return fairQueue;
	}

	public void setFairQueue(boolean fairQueue) {
		this.fairQueue = fairQueue;
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public String getInitSQL() {
		return initSQL;
	}

	public void setInitSQL(String initSQL) {
		this.initSQL = initSQL;
	}

	public Boolean getDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}

	public Boolean getDefaultReadOnly() {
		return defaultReadOnly;
	}

	public void setDefaultReadOnly(Boolean defaultReadOnly) {
		this.defaultReadOnly = defaultReadOnly;
	}

	public TransactionIsolation getDefaultTransactionIsolation() {
		return defaultTransactionIsolation;
	}

	public void setDefaultTransactionIsolation(TransactionIsolation defaultTransactionIsolation) {
		this.defaultTransactionIsolation = defaultTransactionIsolation;
	}

	public String getDefaultCatalog() {
		return defaultCatalog;
	}

	public void setDefaultCatalog(String defaultCatalog) {
		this.defaultCatalog = defaultCatalog;
	}

	public boolean isRollbackOnReturn() {
		return rollbackOnReturn;
	}

	public void setRollbackOnReturn(boolean rollbackOnReturn) {
		this.rollbackOnReturn = rollbackOnReturn;
	}

	public boolean isCommitOnReturn() {
		return commitOnReturn;
	}

	public void setCommitOnReturn(boolean commitOnReturn) {
		this.commitOnReturn = commitOnReturn;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public Duration getValidationInterval() {
		return validationInterval;
	}

	public void setValidationInterval(Duration validationInterval) {
		this.validationInterval = validationInterval;
	}

	public Duration getValidationQueryTimeout() {
		return validationQueryTimeout;
	}

	public void setValidationQueryTimeout(Duration validationQueryTimeout) {
		this.validationQueryTimeout = validationQueryTimeout;
	}

	public boolean isLogValidationErrors() {
		return logValidationErrors;
	}

	public void setLogValidationErrors(boolean logValidationErrors) {
		this.logValidationErrors = logValidationErrors;
	}

	public Duration getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Duration maxAge) {
		this.maxAge = maxAge;
	}

	public Duration getTimeBetweenEvictionRuns() {
		return timeBetweenEvictionRuns;
	}

	public void setTimeBetweenEvictionRuns(Duration timeBetweenEvictionRuns) {
		this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
	}

	public Duration getMinEvictableIdleTime() {
		return minEvictableIdleTime;
	}

	public void setMinEvictableIdleTime(Duration minEvictableIdleTime) {
		this.minEvictableIdleTime = minEvictableIdleTime;
	}

	public boolean isLogAbandoned() {
		return logAbandoned;
	}

	public void setLogAbandoned(boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}

	public boolean isRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public Duration getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(Duration removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public int getAbandonWhenPercentageFull() {
		return abandonWhenPercentageFull;
	}

	public void setAbandonWhenPercentageFull(int abandonWhenPercentageFull) {
		this.abandonWhenPercentageFull = abandonWhenPercentageFull;
	}

	public Duration getSuspectTimeout() {
		return suspectTimeout;
	}

	public void setSuspectTimeout(Duration suspectTimeout) {
		this.suspectTimeout = suspectTimeout;
	}
}
