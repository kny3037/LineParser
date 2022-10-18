package com.line.dao;

import com.line.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(dbHost, dbUser, dbPassword);
        // DriverManager 클래스는 메모리에 로드된 드라이버 클래스를 관리.

        return conn;
    }

    public void add() throws SQLException, ClassNotFoundException {
        Connection conn = getConnection();

        //Query 문 작성
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users(id, name, password) value (?,?,?)"
        );
        ps.setString(1,"0");
        ps.setString(2,"nayeong");
        ps.setString(3,"1123");

        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public User select(String id) throws SQLException, ClassNotFoundException {

        Connection connection = getConnection();

        //Query 문 작성
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users where id = ?");
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"),rs.getString("password"));
        //사용 종료 close.
        rs.close();
        pstmt.close();
        connection.close();

        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
       // userDao.add();
        User user = userDao.select("0");
        System.out.println(user.getName());
    }
}
