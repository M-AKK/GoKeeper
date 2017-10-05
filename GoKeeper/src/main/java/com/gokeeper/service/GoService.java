package com.gokeeper.service;

import com.gokeeper.dto.TtpDetailDto;

/**
 * Go页面的请求操作
 * Created by Akk_Mac
 * Date: 2017/10/1 22:56
 */
public interface GoService {

    //发起一个ttp
    TtpDetailDto create(TtpDetailDto ttpDetailDto);
}
