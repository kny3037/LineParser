package com.line.dao;

import com.line.domain.User;

import java.sql.*;
import java.util.Map;

public abstract class UserDaoAbstract {

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;

    public void add(User user) throws SQLException, ClassNotFoundException {
        Connection conn = getConnection();

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
      //  UserDaoAbstract userDao = new UserDaoAbstract();
     //   userDao.add(new User("7", "Ruru", "123"));

       // System.out.println(user.getName());
    }
}
