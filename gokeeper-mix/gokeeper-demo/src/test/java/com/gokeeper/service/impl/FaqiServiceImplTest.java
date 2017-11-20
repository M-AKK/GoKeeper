package com.gokeeper.service.impl;

import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dto.TtpDetailDto;
import com.gokeeper.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FaqiServiceImplTest {

    @Autowired
    private FaqiServiceImpl goService;

    private final String USER_ID = "1511091449434479239";

    @Test
    public void create() throws Exception {
        TtpDetailDto detailDto = new TtpDetailDto();

        detailDto.setUserId(USER_ID);
        detailDto.setTtpName("测试消息模板创建");
        detailDto.setTtpTarget(new Double(1));//一天一次
        detailDto.setLeaveNotesNums(5);
        detailDto.setAddress("北京路");
        detailDto.setJoinMoney(new BigDecimal(1200));
        detailDto.setIfQuit(0);
        detailDto.setIfJoin(0);
        detailDto.setIfOpen(0);
        detailDto.setDeductionRation(20);
        detailDto.setTtpType(1);
        detailDto.setStartTime(DateUtil.StringToDate("2017/11/20 08:00"));
        detailDto.setFinishTime(DateUtil.StringToDate("2017/11/27 10:00"));

        TtpDetail result = goService.create(detailDto);
        log.info("ttp创建结果， result={}", result);
        Assert.assertNotNull(result);
    }

}