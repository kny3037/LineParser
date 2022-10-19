package com.line.dao;

public class UserDaoFactory {
    //조립을 해주는 역할
    public UserDao awsUserDao(){  //날개 5개 선풍기
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;
    }
}
