package com.gokeeper.service;

import com.gokeeper.VO.GoVo;

import java.util.Date;
import java.util.List;

/**
 * Go界面服务层
 * Created by Akk_Mac
 * Date: 2017/10/5 19:52
 */
public interface GoService {

    //获取我的界面ttp列表
    List<GoVo> getmyttplist(String userId, String currrentDate);
}
