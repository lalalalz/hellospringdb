package springdb.hellospringdb.repository.ex;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springdb.hellospringdb.domain.Member;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExService {

    private final ExRepository repository;

    @Transactional
    public void create(String memberId) {
        try {
            repository.saveMember(new Member(memberId, 0));
            log.info("saveId={}", memberId);
        }
        catch (MyDuplicateKeyException e) {
            log.info("키 중복 오류에 따른 복구 시도");
            String retryId = generateNewId(memberId);
            log.info("retryId={}", retryId);
            repository.saveMember(new Member(retryId, 0));
        }
        catch (MyDbException e) {
            log.info("데이터 접근 계층 처리 불가 예외", e);
            throw e;
        }
    }

    private String generateNewId(String memberId) {
        return memberId + new Random().nextInt(10000);
    }
}
