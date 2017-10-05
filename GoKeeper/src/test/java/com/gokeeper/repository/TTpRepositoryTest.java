package com.gokeeper.repository;

import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.utils.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TTpRepositoryTest {

    @Autowired
    private TTpRepository repository;

    private final String TTP_ID = "1110001";

    private final String USER_ID = "1110002";

    @Test
    public void save() throws Exception {
        TtpDetail detailDto = new TtpDetail();
        detailDto.setTtpId(TTP_ID);
        detailDto.setUserId(USER_ID);
        detailDto.setTtpName("测试ttp");
        detailDto.setTtpTarget(1);//一天一次
        detailDto.setLeaveNotesNums(5);
        detailDto.setAddress("北京路");
        detailDto.setJoinMoney(new BigDecimal(1200));
        detailDto.setAllMoney(new BigDecimal(1200));
        detailDto.setIfQuit(0);
        detailDto.setIfJoin(0);
        detailDto.setIfOpen(1);
        detailDto.setDeductionRation(20);
        detailDto.setTtpType(1);
        detailDto.setTtpStatus(0);
        detailDto.setPayStatus(0);
        detailDto.setStartTime(DateUtil.StringToDate("2017-10-1 21:08"));
        detailDto.setFinishTime(DateUtil.StringToDate("2017-10-8 21:08"));

        TtpDetail result = repository.save(detailDto);
        Assert.assertNotNull(result);

    }



}