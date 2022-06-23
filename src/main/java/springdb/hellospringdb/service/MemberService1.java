package springdb.hellospringdb.service;

import lombok.RequiredArgsConstructor;
import springdb.hellospringdb.domain.Member;
import springdb.hellospringdb.repository.MemberRepositoryV1;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberService1 {

    private final MemberRepositoryV1  memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromMember.getMemberId(), fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toMember.getMemberId(), toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
