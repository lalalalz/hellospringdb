package springdb.hellospringdb.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.catchException();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        assertThatThrownBy(() -> service.throwException())
                .isInstanceOf(MyCheckedException.class);
    }


    static class Service {

        Repository repository = new Repository();

        public void catchException() {
            try {
                repository.call();
            }
            catch(Exception e) {
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        public void throwException() throws Exception {
            repository.call();
        }
    }


    static class Repository {

        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }

}
