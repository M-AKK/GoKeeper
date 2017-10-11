package com.gokeeper.repository;

import com.gokeeper.dataobject.UserTtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * user-ttp表Dao
 * Created by Akk_Mac
 * Date: 2017/10/3 11:43
 */
public interface UserTtpRepository extends JpaRepository<UserTtp, String>{

    //根据userId查找所有参与的ttp
    List<UserTtp> findByUserId(String userId);

    //根据ttpId查找所有参与此ttp的用户
    List<UserTtp> findByTtpId(String TtpId);
}
