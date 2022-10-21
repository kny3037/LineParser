package com.line.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatmentStrategy{
    @Override
    public PreparedStatement makePreparedStatment(Connection connection) throws SQLException {
        return connection.prepareStatement("delete from users");
    }
}
