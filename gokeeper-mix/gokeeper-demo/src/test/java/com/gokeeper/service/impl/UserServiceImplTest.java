package com.gokeeper.service.impl;

import com.gokeeper.vo.UserInfoVo;
import com.gokeeper.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void getUserByphone() throws Exception {
        List<UserInfoVo> result = userService.getUserByphone("176");
        log.info("【模糊查找用户】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);

    }

}