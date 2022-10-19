package com.line.dao;

import com.line.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
       // UserDao userDao = new UserDao(new AWSConnectionMaker());
        UserDao userDao = new UserDaoFactory().awsUserDao();
        User user = new User("3","kim","1123");
        userDao.add(user);

        User selectedUser = userDao.select("0");
        Assertions.assertEquals("kate", selectedUser.getName());
    }
}