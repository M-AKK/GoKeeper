package com.gokeeper.service.impl;

import com.gokeeper.dataobject.AllNews;
import com.gokeeper.repository.AllNewsRepository;
import com.gokeeper.service.AllNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 信息界面初始化列表
 * Created by Akk_Mac
 * Date: 2017/10/13 19:26
 */
@Service
@Slf4j
public class AllNewsServiceImpl implements AllNewsService {

    @Autowired
    private AllNewsRepository allNewsRepository;

    /**
     * 现在返回的是此用户所有的信息，下一步应该还需要做过滤判断处理
     * @param userId
     * @return
     */
    @Override
    public List<AllNews> findAllByUserId(String userId) {
        return allNewsRepository.findAllByUserId(userId);
    }
}
