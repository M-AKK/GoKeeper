package com.gokeeper.service.impl;

import com.gokeeper.vo.GoVo;
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
public class GoServiceImplTest {

    @Autowired
    private GoServiceImpl goService;

    @Test
    public void getmyttplist() throws Exception {

        List<GoVo> result = goService.getmyttplist("1110001", "2017-10-1");
        log.info("【Go界面信息展示，包括详情页面的信息】 result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

}