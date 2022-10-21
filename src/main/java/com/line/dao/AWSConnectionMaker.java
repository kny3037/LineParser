package com.line.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class AWSConnectionMaker implements ConnectionMaker{
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        //OS의 환경변수의 값(Environment variables)을 System.getenv( ) 라는 메서드를 통해서 불러오는 것.
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        // 데이터베이스와 연결하는 드라이버 클래스를 찾아 로드한다.
        // mysql, oracle 등 각 벤더사 마다 클래스 이름이 다르다.
        // mysql은 "com.mysql.jdbc.Driver"이며, 이는 외우는 것이 아니라 구글링하면 된다.

        // Connection은 네트워크상의 연결 자체를 의미한다.
        // 자바프로그램과 DB 사이의 길로 볼 수 있다.
        // 보통 Connection 하나당 트랜잭션 하나를 관리
        // 트랜잭션은 하나 이상의 쿼리에서 동일한 Connection 객체를 공유하는 것
        Connection conn = DriverManager.getConnection
                (dbHost, dbUser, dbPassword);
        // DriverManager.getConnection() 은 실제 자바프로그램과 데이터베이스를 네트워크상에서 연결을 해주는 메소드
        // 드라이버서버에 접속할수 있는 커넥션객체를 가져옴.
        // 연결을 관리하는 Connection 객체를 생성한다.

        return conn;
    }
}
