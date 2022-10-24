package com.line.dao;

import com.line.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private final DataSource dataSource;
    private final JdbcContext jdbcContext;

    public UserDao(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
    }


    public void add(final User user) throws SQLException, ClassNotFoundException {
        jdbcContext.workWithStatmentStrategy(new StatmentStrategy() {
            @Override
            public PreparedStatement makePreparedStatment(Connection connection) throws SQLException {
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
                pstmt.setString(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPassword());

                return pstmt;
            }
        });
    }

    public User select(String id) throws SQLException, ClassNotFoundException {

        Connection connection = dataSource.getConnection();

        //Query 문 작성
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users where id = ?");
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();
        User user = null;
        //ResultSet : 조회결과를 참조할 수 있는 클래스와 그 메소드를 사용한다.
        if(rs.next()){
            user = new User(rs.getString("id"),
                    rs.getString("name"),rs.getString("password"));
        }
        // 형태는 rs.next가 DB의 첫 row부터 내려가도록 해주는 역할.

        //사용 종료 close.
        rs.close();
        pstmt.close();
        connection.close();

        //없으면 exception.
        // 구체적인 에러를 알 수 있다.
        if (user == null) throw new EmptyResultDataAccessException(1);
        // 소스에서 해당아이디로 조회를 했을 때 유저 정보가 null이면 예외처리
        // EmptyResultDataAccessException ?
        // : 데이터에 접근 할 때 결과가 적어도 1줄 이상 있어야 하지만 실제로 아무런 줄도 리턴되지 않을 때 발생하는 예외

        return user;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        this.jdbcContext.excuteSql("delete from users");
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;  //select 쿼리를 실행할 때 사용

        try {
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM users");
            rs = pstmt.executeQuery(); // select 구문을 수행 할 때 사용되는 함수
            rs.next();  // 커서를 다음 행으로 이동시키는 역할.
            // 다음 행으로 넘어갈 수 있으면 true를 리턴하고 커서가 넘어가고,
            // 마지막 행에 도달하면 false를 리턴.
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {//error가 나도 실행되는 블럭.
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
