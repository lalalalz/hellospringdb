package springdb.hellospringdb.repository;

import springdb.hellospringdb.domain.Member;

public interface MemberRepository {

    Member save(Member member);

    Member findById(String MemberId);

    void update(String memberId, int money);

    void delete(String memberId);

}
