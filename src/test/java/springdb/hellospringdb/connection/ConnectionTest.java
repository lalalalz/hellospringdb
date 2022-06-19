package springdb.hellospringdb.connection;


import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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

}
