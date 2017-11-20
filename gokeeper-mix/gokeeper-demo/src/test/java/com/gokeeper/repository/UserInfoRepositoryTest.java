package com.gokeeper.repository;

import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
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
        userInfo.setQqOpenid("1014180579");
        userInfo.setUsername("akk");
        userInfo.setPassword("akk1231412");
        userInfo.setBirthday("2017/20/10");
        userInfo.setSex(0);
        userInfo.setCity("十堰");
        userInfo.setUserIcon("http://www.xxx.com/xxx.jpg");

        UserInfo result = repository.save(userInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByUsername() throws Exception {
        UserInfo result = repository.findByUsername("akk");
        Assert.assertNotNull(result);
    }

    @Test
    public void findByPhonenumber() throws Exception {
        UserInfo result = repository.findByPhonenumber("12345678910");
        log.info("【用户信息】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }


    @Test
    public void findByWxOpenid() throws Exception {
        UserInfo result = repository.findByWxOpenid(null);
        Assert.assertNotNull(result);
    }

}