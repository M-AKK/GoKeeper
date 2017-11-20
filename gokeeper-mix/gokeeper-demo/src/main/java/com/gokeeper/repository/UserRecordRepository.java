package com.gokeeper.repository;

import com.gokeeper.dataobject.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * UserRecord记录表Dao
 * @author Created by Akk_Mac
 * @Date: 2017/10/3 13:05
 */
public interface UserRecordRepository extends JpaRepository<UserRecord, Integer> {

    /**
     * 根据userTtpId查找所有此ttp的所有记录
     * @param userTtpId
     * @return
     */
    List<UserRecord> findByUserTtpId(String userTtpId);

    /**
     * 根据userRecordId查找当天userRecord记录
     * @param userRecordId
     * @return
     */
    UserRecord findByUserRecordId(String userRecordId);


}
