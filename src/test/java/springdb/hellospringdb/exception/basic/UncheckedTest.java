package springdb.hellospringdb.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class UncheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.catchException();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        assertThatThrownBy(() -> service.throwException())
                .isInstanceOf(MyUncheckedException.class);
    }


static class Service {

    Repository repository = new Repository();

    public void catchException() {
        try {
            repository.call();
        }
        catch(MyUncheckedException e) {
            SQLException ex = (SQLException) e.getCause();
            log.info("예외 처리, message={}", e.getMessage(), e);
            log.info("SQLException 에러 코드 확인하기 = {}", ex.getErrorCode());
        }
    }

    public void throwException() {
        repository.call();
    }
}


static class Repository {

    public void throwCheckedException() throws SQLException {
        throw new SQLException("테스트 입니다.", "hello", 1234);
    }

    public void call() {

        try {
            throwCheckedException();
        }

        catch (SQLException e) {
            throw new MyUncheckedException(e);
        }
    }
}
}
