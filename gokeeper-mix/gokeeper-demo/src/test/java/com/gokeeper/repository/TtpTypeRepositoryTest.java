package com.gokeeper.repository;

import com.gokeeper.dataobject.TtpType;
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
public class TtpTypeRepositoryTest {

    @Autowired
    private TtpTypeRepository repository;

    @Test
    public void findall() throws Exception {
        List<TtpType> result = repository.findAll();
        log.info("【TTP消息创建】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);

    }

}