package com.nexon.maple.connectionTest;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DatabaseConnectionTest {

    @Autowired
    private SqlSessionFactory sqlFactory;

    //SqlSessionFactory 객체가 제대로 만들어졌는지 Test
    @Test
    public void testFactory() {
        assertNotNull(sqlFactory);
    }

    //MyBatis와 Mysql 서버가 제대로 연결되었는지 Test
    @Test
    public void testSession() {
        assertNotNull(sqlFactory.openSession());
        assertNotNull(sqlFactory.openSession().getConnection());
    }
}
