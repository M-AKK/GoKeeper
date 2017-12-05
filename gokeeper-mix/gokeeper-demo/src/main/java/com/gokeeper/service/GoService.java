package com.gokeeper.service;

import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.vo.GoPreVo;
import com.gokeeper.vo.GoVo;

import java.util.List;

/**
 * Go界面服务层
 * @author: Created by Akk_Mac
 * @Date: 2017/10/5 19:52
 */
public interface GoService {

    /**
     * 获取我的界面ttp列表
     * @param userId
     * @param currentDate
     * @return
     */
    List<GoPreVo> getMyTtpList(String userId, String currentDate);

    /**
     * 查找用户某一条参与的ttp信息
     * @param ttpId
     * @param currentDate
     * @return GoVo
     */
    GoVo getMyOneTtp(String ttpId, String userId, String currentDate);

    /**
     * 用户中途退出操作
     * @param userId
     * @param ttpId
     * @param currentDate
     * @return UserTtp
     */
    UserTtp quit(String userId, String ttpId, String currentDate);

    UserTtp finish(String userId, String ttpId, String currentDate);
}
