package com.line.dao;

import com.line.domain.User;

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
        Connection connection = connectionMaker.getConnection();

        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM users");
        pstmt.executeUpdate();

        pstmt.close();
        connection.close();

    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection connection = connectionMaker.getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) FROM users");

        ResultSet rs = pstmt.executeQuery();

        rs.next();
        int conut = rs.getInt(1);

        rs.close();
        pstmt.close();
        connection.close();

        return conut;
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

        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public User select(String id) throws SQLException, ClassNotFoundException {

        Connection connection = connectionMaker.getConnection();

        //Query 문 작성
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users where id = ?");
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();
        //ResultSet : 조회결과를 참조할 수 있는 클래스와 그 메소드를 사용한다.
        rs.next();
        // 형태는 rs.next가 DB의 첫 row부터 내려가도록 해주는 역할.
        User user = new User(rs.getString("id"), rs.getString("name"),rs.getString("password"));
        //사용 종료 close.
        rs.close();
        pstmt.close();
        connection.close();

        return user;
    }



    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add(new User("0", "kate", "123"));

       // System.out.println(user.getName());
    }
}
