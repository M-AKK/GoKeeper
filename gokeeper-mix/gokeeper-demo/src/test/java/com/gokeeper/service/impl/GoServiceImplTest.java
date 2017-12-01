package com.gokeeper.service.impl;

import com.gokeeper.vo.GoPreVo;
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
        String userId = "1511062482311815587";
        String currentDate = "2017/11/20";
        List<GoPreVo> result = goService.getMyTtpList(userId, currentDate);
        log.info("【Go界面预览信息展示】 result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

    @Test
    public void getMyOneTtp() throws Exception {
        String ttpId = "1511424137588587249";
        String userId = "1511091449434479239";
        String currentDate = "2017/11/23";

        GoVo result = goService.getMyOneTtp(ttpId, userId, currentDate);
        log.info("【Go界面信息展示，包括详情页面的信息】 result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

}