package com.gokeeper.repository;

import com.gokeeper.dataobject.TtpDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
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
     * 获取所有公开并且ttp状态不是完结按创建时间进行排序的ttp
     * @param ifOpen
     * @param ttpStatus
     * @return
     */
    @Query(value = "SELECT * FROM ttp_detail t WHERE t.if_open = ?1 AND t.ttp_status != ?2 ORDER BY t.create_time DESC ", nativeQuery = true)
    List<TtpDetail> findByIfOpen(Integer ifOpen, Integer ttpStatus);

    /**
     * 获取公开并且相应类型的ttp
     * @param ifOpen
     * @param ttpType
     * @return
     */
    List<TtpDetail> findByIfOpenAndTtpType(Integer ifOpen, Integer ttpType);

    /**
     * 全条件搜索
     * @param ifOpen
     * @param ttpType
     * @param minMoeny
     * @param maxMoeny
     * @param startTime
     * @param finishTime
     * @return
     */
    @Query(value="SELECT * FROM ttp_detail t WHERE t.if_open = :ifOpen AND t.ttp_type = :ttpType AND t.join_money BETWEEN :minMoeny AND :maxMoeny AND t.start_time > :startTime AND t.finish_time < :finishTime", nativeQuery = true)
    List<TtpDetail> highsSearch(@Param("ifOpen") Integer ifOpen,
                                @Param("ttpType") Integer ttpType,
                                @Param("minMoeny") BigDecimal minMoeny,
                                @Param("maxMoeny") BigDecimal maxMoeny,
                                @Param("startTime") Date startTime,
                                @Param("finishTime") Date finishTime);

    /**
     * 主要按金额范围查找
     * @param ifOpen
     * @param ttpType
     * @param minMoeny
     * @param maxMoeny
     * @return
     */
    @Query(value = "SELECT * FROM ttp_detail t WHERE t.if_open = :ifOpen AND t.ttp_type = :ttpType AND t.join_money BETWEEN :minMoeny AND :maxMoeny", nativeQuery = true)
    List<TtpDetail> moneySearch(@Param("ifOpen") Integer ifOpen,
            @Param("ttpType") Integer ttpType,
            @Param("minMoeny") BigDecimal minMoeny,
            @Param("maxMoeny") BigDecimal maxMoeny
            );

    /**
     * 主要按活动时间查找
     * @param ifOpen
     * @param ttpType
     * @param startTime
     * @param finishTime
     * @return
     */
    @Query(value="SELECT * FROM ttp_detail t WHERE t.if_open = :ifOpen AND t.ttp_type = :ttpType AND t.start_time > :startTime AND t.finish_time < :finishTime", nativeQuery = true)
    List<TtpDetail> timeSearch(@Param("ifOpen") Integer ifOpen,
                               @Param("ttpType") Integer ttpType,
                               @Param("startTime") Date startTime,
                               @Param("finishTime") Date finishTime);

}
