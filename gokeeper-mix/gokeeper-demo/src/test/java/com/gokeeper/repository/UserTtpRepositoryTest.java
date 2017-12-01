package com.gokeeper.repository;

import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserTtpRepositoryTest {

    @Autowired
    private UserTtpRepository repository;

    @Test
    public void findByUserIdOrOrderByUpdateTimeDesc() throws Exception {
        List<UserTtp> result = repository.findByUserIdOrderByUpdateTimeDesc("1511062482311815587");
        log.info("【userTtp信息】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

    @Test
    public void findByTtpId() throws Exception {
    }

    @Test
    public void findByUserTtpId() throws Exception {
    }

}