package springdb.hellospringdb.exception.translator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springdb.hellospringdb.repository.ex.ExRepository;
import springdb.hellospringdb.repository.ex.ExService;

@SpringBootTest
public class ExTranslatorV1Test {

    @Autowired
    private ExRepository ExRepository;
    @Autowired
    private ExService service;

    @Test
    void duplicatedKeySave() {
        service.create("myId");
        service.create("myId");
    }
}
