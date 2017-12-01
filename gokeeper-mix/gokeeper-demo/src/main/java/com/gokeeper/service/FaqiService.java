package com.gokeeper.service;

import com.gokeeper.dataobject.TtpType;
import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.dto.TtpDetailDto;

import java.util.List;

/**
 * Go页面的请求操作
 * @author: Created by Akk_Mac
 * @Date: 2017/10/1 22:56
 */
public interface FaqiService {

    /**
     * 发起一个ttp
     * @param ttpDetailDto
     * @return
     */
    TtpDetailDto create(TtpDetailDto ttpDetailDto);

    /**
     * 查找所有ttp类型
     * @return
     */
    List<TtpType> findAllType();

    /**
     * 取消一个user参加的ttp，相当于退出
     * @return
     */
    UserTtp canel(UserTtp userTtp, String currentDate);
}
