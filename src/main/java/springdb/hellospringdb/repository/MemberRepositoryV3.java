package springdb.hellospringdb.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import springdb.hellospringdb.domain.Member;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepositoryV3 {

    private final DataSource dataSource;


    public Member save(Member member) throws SQLException {

        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        }
        catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {
            close(conn, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {

        String sql = "select * from member where member_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();

            // 데이터가 있으면 (조회가 성공했으면)
            if (rs.next()) {
                Member findMember = new Member();
                findMember.setMemberId(rs.getString("member_id"));
                findMember.setMoney(rs.getInt("money"));
                return findMember;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        }

        catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }

        finally {
            close(conn, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {

        String sql = "update member set money=? where member_id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {
            close(conn, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {

        String sql = "delete from member where member_id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {
            close(conn, pstmt, null);
        }
    }

    private Connection getConnection() throws SQLException {
        // 동기화 매니저에서 커넥션(트랜잭션)을 획득
        // 서비스 계층에서 트랜잭션 매니저를 통해 트랜잭션 동기화 매니저에 트랜잭션을 보관하면
        // 이를 DataSourceUtils.getConnection()으로 획득할 수 있다.
        Connection connection = DataSourceUtils.getConnection(dataSource);
        log.info("get connection = {}, class = {}", connection, connection.getClass());
        return connection;
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {

        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(pstmt);

        // 획득한 커넥션(트랜잭션)을 트랜잭션 동기화 매니저로 다시 반납한다.
        // 이때는 커넥션을 닫는 것이 아니기 때문에 트랜잭션이 유지된다.
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
