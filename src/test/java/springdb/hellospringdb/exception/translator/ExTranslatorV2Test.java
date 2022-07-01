package springdb.hellospringdb.exception.translator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static springdb.hellospringdb.connection.ConnectionConst.*;

@Slf4j
public class ExTranslatorV2Test {

    private DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

    @Test
    void exceptionTranslator() {

        String sql = "select bad grammar";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.executeQuery();
        }
        catch (SQLException e) {
            assertThat(e.getErrorCode()).isEqualTo(42122);

            SQLErrorCodeSQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            DataAccessException translatedException = translator.translate("이것은 문법 오류 예외입니다.", sql, e);

            log.info("번역된 결과", translatedException);
            assertThat(translatedException.getClass()).isEqualTo(BadSqlGrammarException.class);
        }
    }
}
