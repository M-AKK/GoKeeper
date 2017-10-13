package com.gokeeper.repository;

import com.gokeeper.dataobject.AllNews;
import com.gokeeper.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AllNewsRepositoryTest {

    @Autowired
    private AllNewsRepository repository;

    @Test
    public void save() throws Exception {
        AllNews allNews = new AllNews();
        allNews.setId("110");
        allNews.setUserId("1110001");
        allNews.setNewstype(1);
        allNews.setUsername("系统");
        allNews.setUserIcon("www.akaka.com/1.jpg");
        allNews.setNewsname("测试系统消息");
        allNews.setContent("<a>一个有html格式的链接</a>");

        AllNews result = repository.save(allNews);
        log.info("【所有公开的ttp】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

}