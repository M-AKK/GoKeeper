package com.gokeeper.service.impl;

import com.gokeeper.dataobject.TtpNews;
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
        List<AllNewsVo> result = allNewsService.findAllOpenMsg("1511868873980290512", 1);
        log.info("【所有公开的消息】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

    @Test
    public void getOneMsg() throws Exception {
        Object result = allNewsService.getOneMsg("15117037645393815271511700393182467058");
        log.info("【某条公开的消息】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

}