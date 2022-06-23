package springdb.hellospringdb.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import springdb.hellospringdb.domain.Member;
import springdb.hellospringdb.repository.MemberRepositoryV2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberService2 {

    private final DataSource datasource;
    private final MemberRepositoryV2 memberRespository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        Connection conn = datasource.getConnection();

        try {
            // 트랜잭션 시작
            conn.setAutoCommit(false);
            bizLogic(fromId, toId, money, conn);
            conn.commit();
        }
        catch (Exception e) {
            conn.rollback();
            throw new IllegalStateException(e);
        }
        finally {
            release(conn);
        }
    }

    private void bizLogic(String fromId, String toId, int money, Connection conn) throws SQLException {
        Member fromMember = memberRespository.findById(fromId);
        Member toMember = memberRespository.findById(toId);

        memberRespository.update(conn, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRespository.update(conn, toId, toMember.getMoney() + money);
    }

    private void release(Connection conn) {
        JdbcUtils.closeConnection(conn);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
