package com.gokeeper.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Go界面展示的自己参与的所有ttp的信息
 * @author Created by Akk_Mac
 * Date: 2017/10/5 19:56
 */
@Data
public class GoVo extends GoPreVo{

    /**
     * ttp开始时间
     */
    private String startTime;

    private String finishTime;

    /**
     * ttp完成进度
     */
    private Integer ttpSchedule;

    /**
     * ttp目标
     */
    private String ttpTarget;

    /**
     * ttp所需押金
     */
    private BigDecimal joinMoney;

    /**
     * ttp规定总请假条
     */
    private Integer leaveNotesNums;

    /**
     * 已使用请假条数
     */
    private Integer leaveNotes;

    /**
     * 是否允许中途退出
     */
    private String ifQuit;

    /**
     * 是否允许中途退出
     */
    private String ifJoin;

    /**
     * 是否公开
     */
    private String ifOpen;

    /**
     * 用户完成记录表
     */
    private List<UserRecordVo> userRecordList;

    /**
     * 同一个ttp其他人当天完成记录表
     */
    private List<OthersRecordVo> othersfinishList;

    /**
     * 同一个ttp其他人当天未完成记录表
     */
    private List<OthersRecordVo> othersnofinishList;


}
