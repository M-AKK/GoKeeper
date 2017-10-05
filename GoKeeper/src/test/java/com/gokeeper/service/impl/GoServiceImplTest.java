package com.gokeeper.service.impl;

import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dto.TtpDetailDto;
import com.gokeeper.form.TtpForm;
import com.gokeeper.utils.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoServiceImplTest {

    @Autowired
    private GoServiceImpl goService;

    private final String TTP_ID = "1110000";

    private final String USER_ID = "1110001";

    @Test
    public void create() throws Exception {

        TtpDetailDto detailDto = new TtpDetailDto();

        detailDto.setUserId(USER_ID);
        detailDto.setTtpName("测试ttp");
        detailDto.setTtpTarget(1);//一天一次
        detailDto.setLeaveNotesNums(5);
        detailDto.setAddress("北京路");
        detailDto.setJoinMoney(new BigDecimal(1200));

        detailDto.setIfQuit(0);
        detailDto.setIfJoin(0);
        detailDto.setIfOpen(1);
        detailDto.setDeductionRation(20);
        detailDto.setTtpType(1);

        detailDto.setStartTime(DateUtil.StringToDate("2017-10-1 21:08"));
        detailDto.setFinishTime(DateUtil.StringToDate("2017-10-8 21:08"));

        TtpDetail result = goService.create(detailDto);

        Assert.assertNotNull(result);


    }

}