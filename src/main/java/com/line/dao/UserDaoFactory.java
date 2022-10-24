package com.line.dao;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configurable
public class UserDaoFactory {
    //객체 생성을 대신 해주는 공장
    //조립을 해주는 역할
    @Bean
    public UserDao awsUserDao(){  //날개 5개 선풍기
        return new UserDao(dataSource());
    }

    @Bean
    DataSource dataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        Map<String, String> env = System.getenv();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);

        dataSource.setUrl(env.get("DB_HOST"));
        dataSource.setUsername(env.get("DB_USER"));
        dataSource.setPassword(env.get("DB_PASSWORD"));
        return dataSource;
    }
}

// 스프링 빈이란?
// 스프링의 특징에는 제어의 역전(IoC)이 있다.
// 제어의 역전이란, 간단히 말해서 객체의 생성 및 제어권을 사용자가 아닌 스프링에게 맡기는 것이다.
// 지금까지는 사용자가 new연산을 통해 객체를 생성하고 메소드를 호출했다.
// IoC가 적용된 경우에는 이러한 객체의 생성과 사용자의 제어권을 스프링에게 넘긴다.
// 사용자는 직접 new를 이용해 생성한 객체를 사용하지 않고, 스프링에 의하여 관리당하는 자바 객체를 사용한다.
// 이 객체를 '빈(bean)'이라 한다.