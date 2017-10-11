package com.gokeeper.repository;

import com.gokeeper.dataobject.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * UserRecord记录表Dao
 * Created by Akk_Mac
 * Date: 2017/10/3 13:05
 */
public interface UserRecordRepository extends JpaRepository<UserRecord, String> {

    //根据userTtpId查找所有此ttp的所有记录
    List<UserRecord> findByUserTtpId(String userTtpId);

    //根据userTtpId和date查询此ttp当天完成人员列表
    @Query(nativeQuery = true, value = "select * from user_record where user_ttp_id=?1 AND days=?2 ")
    UserRecord getfinishList(@Param("userTtpId") String userTtpId, @Param("currentDate") String currentDate);


}
