package com.gokeeper.repository;

import com.gokeeper.dataobject.TtpDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Created by Akk_Mac
 * Date: 2017/10/2 10:31
 */
public interface TTpDetailRepository extends JpaRepository<TtpDetail, String> {

    /**
     * 根据ttpId查找对应ttp
     * @param ttpId
     * @return
     */
    TtpDetail findByTtpId(String ttpId);

    /**
     * 获取所有公开的ttp
     * @param ifopen
     * @return
     */
    List<TtpDetail> findByIfOpen(int ifopen);
}
