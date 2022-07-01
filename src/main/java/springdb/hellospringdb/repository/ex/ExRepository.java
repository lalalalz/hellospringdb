package springdb.hellospringdb.repository.ex;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import springdb.hellospringdb.domain.Member;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ExRepository {

    private final DataSource dataSource;

    public Member saveMember(Member member) {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection conn = DataSourceUtils.getConnection(dataSource);
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        }
        catch (SQLException e) {
            // h2 db 키 중복 오류
            if (e.getErrorCode() == 23505) {
                log.info("키 중복 오류 발생");
                throw new MyDuplicateKeyException(e);
            }

            throw new MyDbException(e);
        }
        finally {
            JdbcUtils.closeStatement(pstmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }
}
