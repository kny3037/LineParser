package com.line.dao;

import com.line.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.Map;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(){
        this.connectionMaker = new AWSConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {

        this.connectionMaker = connectionMaker;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        // try ~ catch는 catch문을 실행하고 *사용자가 프로그램을 종료할 수도 있고 계속 구문을 실행할 수도* 있다.
        // throws는 예외를 해당 구문에 던져주어 책임을 전가하고 예외상황을 처리가 수행된 후 *프로그램이 종료* 된다.
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = connectionMaker.getConnection();
            pstmt = new DeleteAllStrategy().makePreparedStatment(connection);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {  //error가 나도 실행되는 블럭.
            if(pstmt != null){
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

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;  //select 쿼리를 실행할 때 사용

        try {
            connection = connectionMaker.getConnection();
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

    public void add(User user) throws SQLException, ClassNotFoundException {
        Connection conn = connectionMaker.getConnection();

        //Query 문 작성
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users(id, name, password) value (?,?,?)"
        );
        ps.setString(1,user.getId());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());

        ps.executeUpdate();  // select 구문을 제외한 다른 구문을 수행할 때 사용되는 함수
        ps.close();
        conn.close();
    }

    public User select(String id) throws SQLException, ClassNotFoundException {

        Connection connection = connectionMaker.getConnection();

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
        // EmptyResultDataAccessException : 데이터에 접근 할 때 결과가 적어도 1줄 이상
        // 있어야 하지만 실제로 아무런 줄도 리턴되지 않을 때 발생하는 예외

        return user;
    }



    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add(new User("0", "kate", "123"));

       // System.out.println(user.getName());
    }
}
