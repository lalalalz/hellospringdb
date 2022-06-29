package springdb.hellospringdb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import springdb.hellospringdb.domain.Member;
import springdb.hellospringdb.repository.MemberRepositoryV3;

import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceV3_2 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) {

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);

        txTemplate.executeWithoutResult((status) -> {
            try {
                bizLogic(fromId, toId, money);
            }
            catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}