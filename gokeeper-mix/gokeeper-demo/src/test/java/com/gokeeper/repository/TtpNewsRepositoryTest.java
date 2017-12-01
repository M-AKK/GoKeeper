package com.gokeeper.repository;

import com.gokeeper.dataobject.TtpNews;
import com.gokeeper.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TtpNewsRepositoryTest {

    @Autowired
    private TtpNewsRepository repository;

    @Test
    public void save() throws Exception{
        TtpNews ttpNews = new TtpNews();
        ttpNews.setId("5646");
        ttpNews.setUserTtpId("sdfa1212");
        ttpNews.setTtpId("123123");
        ttpNews.setUserId("111001");
        ttpNews.setTtpStatus(1);
        ttpNews.setNewstype(2);
        ttpNews.setUsername("akk");
        ttpNews.setUserIcon("www.akaka.cn/1.jpg");
        ttpNews.setNewsname("测试的系统消息");
        ttpNews.setNewsstatus(0);//未读消息
        ttpNews.setPreviewText("哈哈哈哈哈");
        ttpNews.setHidden(0);
        ttpNews.setStartTime(new Date());
        ttpNews.setFinishTime(new Date());
        ttpNews.setIfFinish(0);
        ttpNews.setFinishnums(0);
        ttpNews.setNofinishnums(0);
        ttpNews.setLeavenums(0);
        ttpNews.setUserDayBouns(new BigDecimal(1));
        ttpNews.setUserTotalBouns(new BigDecimal(10));
        ttpNews.setWeight(1);

        TtpNews result = repository.save(ttpNews);

        log.info("【TTP消息创建】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);

    }

    @Test
    public void findAllByUserIdAndHiddenOrderByUpdateTime() throws Exception{
        List<TtpNews> result = repository.findAllByUserIdAndHiddenOrderByUpdateTimeDesc("111001", 0);
        log.info("【TTP消息按userid和hidden查询】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

}