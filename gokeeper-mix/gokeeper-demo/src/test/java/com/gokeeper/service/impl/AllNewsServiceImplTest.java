package com.gokeeper.service.impl;

import com.gokeeper.vo.news.AllNewsVo;
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
public class AllNewsServiceImplTest {

    @Autowired
    private AllNewsServiceImpl allNewsService;

    @Test
    public void findAllOpenMsg() throws Exception {
        List<AllNewsVo> result = allNewsService.findAllOpenMsg("1511062482311815587", 0);
        log.info("【所有公开的消息】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

}