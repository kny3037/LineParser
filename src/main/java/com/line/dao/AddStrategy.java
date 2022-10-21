package com.line.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStrategy implements StatmentStrategy{
    @Override
    public PreparedStatement makePreparedStatment(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("insert into users (id, name, password) value(?,?,?)");
        return null;
    }
}
