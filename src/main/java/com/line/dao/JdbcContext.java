package com.line.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void excuteSql(String sql) throws SQLException, ClassNotFoundException {
        this.workWithStatmentStrategy(new StatmentStrategy() {
            @Override
            public PreparedStatement makePreparedStatment(Connection connection) throws SQLException {
                return connection.prepareStatement(sql);
            }
        });
    }


    public void workWithStatmentStrategy(StatmentStrategy stmt) throws ClassNotFoundException {
        // try ~ catch는 catch문을 실행하고 *사용자가 프로그램을 종료할 수도 있고 계속 구문을 실행할 수도* 있다.
        // throws는 예외를 해당 구문에 던져주어 책임을 전가하고 예외상황을 처리가 수행된 후 *프로그램이 종료* 된다.
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            pstmt = new DeleteAllStrategy().makePreparedStatment(conn);
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
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

}
