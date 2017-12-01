package com.gokeeper.repository;

import com.gokeeper.dataobject.UserTtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description: user-ttp表Dao
 * @author: Created by Akk_Mac
 * @Date: 2017/10/3 11:43
 */
public interface UserTtpRepository extends JpaRepository<UserTtp, Integer> {

    /**
     * 根据userId查找所有参与的ttp并按时间排序
     * @param userId
     * @return List<UserTtp>
     */
    List<UserTtp> findByUserIdOrderByUpdateTimeDesc(String userId);
    /**
     * 根据ttpId查找所有参与此ttp的用户
     * @param TtpId
     * @return List<UserTtp>
     */
    List<UserTtp> findByTtpId(String TtpId);

    /**
     * 根据userTtpId查找某条userTtp信息
     * @param userTtpId
     * @return
     */
    UserTtp findByUserTtpId(String userTtpId);

    /**
     * 根据订单查找
     * @param orderId
     * @return
     */
    UserTtp findByOrderId(String orderId);

    /**
     * 根据用户id查找此用户参与的ttp
     * @param userId
     * @return
     */
    List<UserTtp> findByUserId(String userId);
}
