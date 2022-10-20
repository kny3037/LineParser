package com.line.dao;

import com.line.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
/*
- 확장을 선언적으로 등록해 주는 역할을 한다.
- Extension(확장자) 뒤에 인자로 확장할 Extension을 추가하여 사용 할 수 있다.
- Spring을 사용할 경우 @ExtendWith(SpringExtension.class)와 같이 사용한다.
- junit4 사용시에는 springRunner.class를 명시적으로 선언하고 사용해야 했으나
- junit5들어와서는 @SpringBootTest 어노테이션안에 @ExtendsWith 명시가 되어있다.
- 즉, junit5를 사용할 경우  @SpringBootTest 어노테이션을 사용하지 않았을 경우에만
- @ExtendsWith를 직접적으로 명시를 해주면 되는 것이다.
 */
@ContextConfiguration(classes = UserDaoFactory.class)
// Junit5 Test코드를 실행 할 때 ApplicationContext에 들어갈 설정 정보(관계 설정)를 불러오게 해주는 기능
//
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    //@Autowired
    //의존성 주입을 할 때 사용하는 어노테이션으로 의존 객체의 타입에 해당하는 bean을 찾아 주입하는 역할


    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        User user1 = new User("2","kate","1111");

        UserDao userDao = context.getBean("awsUserDao",UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());

        User selectedUser = userDao.select(user1.getId());

        Assertions.assertEquals(user1.getName(), selectedUser.getName());
        Assertions.assertEquals(user1.getPassword(), selectedUser.getPassword());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
        User user1 = new User("2","kate","1111");
        User user2 = new User("3","kyeonghwan","2222");
        User user3 = new User("4","sujin","3333");

        UserDao userDao = context.getBean("awsUserDao",UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());

        userDao.add(user2);
        assertEquals(2, userDao.getCount());

        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }
}