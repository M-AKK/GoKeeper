package com.gokeeper.service;

import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.vo.JoinPreVo;
import com.gokeeper.vo.JoinVo;

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
    List<JoinPreVo> getOpenTtp();

    /**
     * 根据ttpid查找对应ttp
     * @param ttpId
     * @return
     */
    JoinVo getOneTtp(String ttpId);

    /**
     * user-ttp新增一个用户，就是新用户加入ttp
     * @param userId
     * @param ttpId
     * @return
     */
    UserTtp attend(String userId, String ttpId);

    /**
     * 修改ttp的支付状态
     * @param userTtp
     * @return
     */
    UserTtp paid(UserTtp userTtp);
}
