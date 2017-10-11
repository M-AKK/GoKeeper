package com.gokeeper.service;

import com.gokeeper.VO.JoinVo;

import java.util.List;

/**
 * 加入界面服务层
 * Created by Akk_Mac
 * Date: 2017/10/7 09:37
 */
public interface JoinService {

    //获取所有公开ttp的信息
    List<JoinVo> getOpenTtp();
}
