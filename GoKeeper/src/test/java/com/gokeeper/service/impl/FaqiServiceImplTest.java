package com.gokeeper.service.impl;

import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dto.TtpDetailDto;
import com.gokeeper.utils.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FaqiServiceImplTest {

    @Autowired
    private FaqiServiceImpl goService;

    private final String USER_ID = "1110003";

    @Test
    public void create() throws Exception {
        TtpDetailDto detailDto = new TtpDetailDto();

        detailDto.setUserId(USER_ID);
        detailDto.setTtpName("测试ttp2");
        detailDto.setTtpTarget(new Double(1));//一天一次
        detailDto.setLeaveNotesNums(5);
        detailDto.setAddress("北京路");
        detailDto.setJoinMoney(new BigDecimal(1200));
        detailDto.setIfQuit(1);
        detailDto.setIfJoin(1);
        detailDto.setIfOpen(0);
        detailDto.setDeductionRation(20);
        detailDto.setTtpType(1);
        detailDto.setStartTime(DateUtil.StringToDate("2017-10-1 21:08"));
        detailDto.setFinishTime(DateUtil.StringToDate("2017-10-8 21:08"));

        TtpDetail result = goService.create(detailDto);
        Assert.assertNotNull(result);
    }

}