package com.gokeeper.repository;

import com.gokeeper.dataobject.SystemNews;
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
public class SystemNewsRspositoryTest {

    @Autowired
    private SystemNewsRspository rspository;

    @Test
    public void save() throws Exception{
        SystemNews systemNews = new SystemNews();
        systemNews.setId("abc123");
        systemNews.setUserId("111001");
        systemNews.setNewstype(1);//默认，可以不写
        systemNews.setUsername("akk");
        systemNews.setUserIcon("www.akaka.cn/1.jpg");
        systemNews.setNewsname("测试的系统消息");
        systemNews.setNewsstatus(0);//未读消息
        systemNews.setHidden(0);
        systemNews.setPreviewText("这是系统消息的预览信息");
        systemNews.setText("系统消息正文");
        systemNews.setWeight(1);

        SystemNews result = rspository.save(systemNews);
        log.info("【系统消息创建】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);

    }

    @Test
    public void findAllByUserIdAndHiddenOrdeOrderByUpdateTimeDesc() throws Exception{
        List<SystemNews> result = rspository.findAllByHiddenOrderByUpdateTimeDesc(0);
        log.info("【公开的系统消息ByuserId】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);

    }

}