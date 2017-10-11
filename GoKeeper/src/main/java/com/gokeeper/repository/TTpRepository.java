package com.gokeeper.repository;

import com.gokeeper.dataobject.TtpDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Akk_Mac
 * Date: 2017/10/2 10:31
 */
public interface TTpRepository extends JpaRepository<TtpDetail, String> {

    //根据ttpId查找对应ttp
    TtpDetail findByTtpId(String ttpId);

    //获取所有公开的ttp
    List<TtpDetail> findByIfOpen(int ifopen);
}
