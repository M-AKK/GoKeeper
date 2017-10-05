package com.gokeeper.repository;

import com.gokeeper.dataobject.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository repository;

    //先写存储
    @Test
    public void save(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("1232");
        //userInfo.setWxOpenid("abc");
        userInfo.setPhonenumber("12345678910");
        //userInfo.setWeiboId("weibo");
        //userInfo.setQqId("1014180579");
        userInfo.setUsername("akk");
        userInfo.setPassword("akk1231412");
        userInfo.setRealname("柯泓明1");
        userInfo.setSex(0);
        userInfo.setUserIcon("http://www.xxx.com/xxx.jpg");

        UserInfo result = repository.save(userInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByUsername() throws Exception {
        UserInfo result = repository.findByUsername("admin");
        Assert.assertNotNull(result);
    }

}