package hello.jdbc.connection;

import static hello.jdbc.connection.ConnectionConst.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionTest {

	@Test
	void driverManager() throws SQLException {
		Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		log.info("con1={}", con1);
		log.info("con2={}", con2);
	}

	@Test
	void dataSourceDriverManager() throws SQLException {
		DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		Connection con1 = dataSource.getConnection();
		Connection con2 = dataSource.getConnection();
		log.info("con1={}", con1);
		log.info("con2={}", con2);
	}

	@Test
	void dataSourceConnectionPool() throws SQLException, InterruptedException {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWORD);
		dataSource.setMaximumPoolSize(10);
		dataSource.setPoolName("MyPool");
		Connection connection = dataSource.getConnection();
		Thread.sleep(1000);
	}
}