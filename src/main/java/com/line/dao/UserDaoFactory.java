package com.line.dao;

import org.springframework.context.annotation.Bean;

public class UserDaoFactory {
    //조립을 해주는 역할
    @Bean
    public UserDao awsUserDao(){  //날개 5개 선풍기
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;
    }
}
