package springdb.hellospringdb.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import springdb.hellospringdb.domain.Member;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {

        // 등록
        Member member = new Member("memberV0", 20000);
//        repository.save(member);

        // 조회
        Member findMember = repository.findById(member.getMemberId());
        System.out.println("findMember = " + findMember);
        assertThat(findMember).isEqualTo(member);

        // 수정
        repository.update(member.getMemberId(), 20000);
        Member findMember2 = repository.findById(member.getMemberId());
        assertThat(findMember2.getMoney()).isEqualTo(20000);

        // 삭제
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}