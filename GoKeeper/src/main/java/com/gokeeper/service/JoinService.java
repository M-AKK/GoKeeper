package com.gokeeper.service;

import com.gokeeper.vo.JoinVo;
import com.gokeeper.dataobject.UserTtp;

import java.util.List;

/**
 * 加入界面服务层
 * @author Created by Akk_Mac
 * @Date: 2017/10/7 09:37
 */
public interface JoinService {

    /**
     * 获取所有公开ttp的信息
     * @return
     */
    List<JoinVo> getOpenTtp();

    /**
     * user-ttp新增一个用户，就是新用户加入ttp
     * @param userId
     * @param ttpId
     * @return
     */
    UserTtp attend(String userId, String ttpId);
}
