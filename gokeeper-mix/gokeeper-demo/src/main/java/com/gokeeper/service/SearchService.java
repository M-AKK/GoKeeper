package com.gokeeper.service;

import com.gokeeper.vo.JoinPreVo;

import java.util.List;

/**
 * @Description: 搜索服务
 * @author: Created by Akk_Mac
 * @Date: 2017/11/20 19:10
 */
public interface SearchService {

    List<JoinPreVo> highSearch(Integer ttpType, String minMoney, String maxMoney, String startTime, String finishTime);
}
