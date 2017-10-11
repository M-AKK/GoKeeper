package com.gokeeper.service.impl;

import com.gokeeper.VO.JoinVo;
import com.gokeeper.service.JoinService;
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
public class JoinServiceImplTest {

    @Autowired
    private JoinServiceImpl joinService;

    @Test
    public void getOpenTtp() throws Exception {
        List<JoinVo> result = joinService.getOpenTtp();
        log.info("【所有公开的ttp】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

}