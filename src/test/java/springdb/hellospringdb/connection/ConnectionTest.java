package springdb.hellospringdb.connection;


import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static springdb.hellospringdb.connection.ConnectionConst.*;

public class ConnectionTest {

    @Test
    void dataSourceDriverManagerTest() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        Connection connection1 = dataSource.getConnection();
        Connection connection2 = dataSource.getConnection();

        System.out.println("connection1 = " + connection1 + " connection1 class = " + connection1.getClass());
        System.out.println("connection2 = " + connection2 + " connection2 class = " + connection2.getClass());
    }


    @Test
    void HikariCPDataSource() throws SQLException, InterruptedException {

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("hello");

        Thread.sleep(1000);

        Connection conn1 = dataSource.getConnection();
        System.out.println("connection1 = " + conn1 + " connection1 class = " + conn1.getClass());
        conn1.close();

        Connection conn2 = dataSource.getConnection();
        System.out.println("connection2 = " + conn2 + " connection2 class = " + conn2.getClass());
        conn2.close();
    }
}
