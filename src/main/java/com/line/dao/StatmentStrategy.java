package com.line.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatmentStrategy {
    PreparedStatement makePreparedStatment (Connection connection) throws SQLException;
}
