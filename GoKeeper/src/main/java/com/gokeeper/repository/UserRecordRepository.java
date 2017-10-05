package com.gokeeper.repository;

import com.gokeeper.dataobject.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRecord记录表Dao
 * Created by Akk_Mac
 * Date: 2017/10/3 13:05
 */
public interface UserRecordRepository extends JpaRepository<UserRecord, String> {
}
