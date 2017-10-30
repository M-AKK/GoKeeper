package com.gokeeper.repository;

import com.gokeeper.dataobject.UserRecord;
import com.gokeeper.utils.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRecordRepositoryTest {

    @Autowired
    private UserRecordRepository repository;

    @Test
    public void findByUserTtpId() throws Exception {
        List<UserRecord> findByUserTtpId = repository.findByUserTtpId("15070107496453461891110001");
        Assert.assertNotNull(findByUserTtpId);
    }

    @Test
    public void getdayStatus() throws Exception {
        UserRecord result = repository.findByUserRecordId("15070107496453461891110001"+"2017-10-01");
        System.out.println("更新时间:"+result.getUpdateTime());
        Assert.assertNotNull(result);
    }

}