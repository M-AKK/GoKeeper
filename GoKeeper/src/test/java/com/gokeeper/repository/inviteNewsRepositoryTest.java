package com.gokeeper.repository;

import com.gokeeper.dataobject.InviteNews;
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
public class inviteNewsRepositoryTest {

    @Autowired
    private InviteNewsRepository repository;

    @Test
    public void save() throws Exception {
        InviteNews inviteNews = new InviteNews();
        inviteNews.setId("111");
        inviteNews.setUserId("1110001");
        inviteNews.setNewstype(3);
        inviteNews.setUsername("akk");
        inviteNews.setUserIcon("www.akaka.com/1.jpg");
        inviteNews.setNewsname("邀请消息2号");
        inviteNews.setNewsstatus(0);
        inviteNews.setHidden(0);
        inviteNews.setPreviewText("这是邀请信息测试");
        inviteNews.setTtpId("1507010749645346189");
        //inviteNews.setPayUrl("/1507010749645346189");//支付页面url的部分，还需改进
        inviteNews.setWeight(1);
        InviteNews result = repository.save(inviteNews);
        log.info("【邀请消息】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);
    }

    @Test
    public void findAllByUserIdAndHiddenOrderByUpdateTimeDesc() throws Exception{
        List<InviteNews> result = repository.findAllByUserIdAndHiddenOrderByUpdateTimeDesc("1110001", 0);
        log.info("【邀请消息查询By UserIdAndHidden】result={}", JsonUtil.toJson(result));
        Assert.assertNotNull(result);

    }

}