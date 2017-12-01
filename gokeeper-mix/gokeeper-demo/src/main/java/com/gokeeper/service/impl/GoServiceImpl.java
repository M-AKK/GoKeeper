package com.gokeeper.service.impl;

import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.dataobject.UserRecord;
import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.enums.*;
import com.gokeeper.exception.TTpException;
import com.gokeeper.repository.TTpDetailRepository;
import com.gokeeper.repository.UserInfoRepository;
import com.gokeeper.repository.UserRecordRepository;
import com.gokeeper.repository.UserTtpRepository;
import com.gokeeper.service.FaqiService;
import com.gokeeper.service.GoService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.EnumUtil;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.vo.GoPreVo;
import com.gokeeper.vo.GoVo;
import com.gokeeper.vo.OthersRecordVo;
import com.gokeeper.vo.UserRecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import static com.gokeeper.utils.DateUtil.dateFormat2;

/**
 * Go（我的）界面具体操作
 * @author: Created by Akk_Mac
 * @Date: 2017/10/6 09:07
 */
@Service
@Slf4j
public class GoServiceImpl implements GoService{

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserTtpRepository userTtpRepository;

    @Autowired
    private TTpDetailRepository tTpDetailRepository;

    @Autowired
    private UserRecordRepository userRecordRepository;

    @Autowired
    private FaqiService faqiService;


    @Override
    public List<GoPreVo> getMyTtpList(String userId, String currentDate) {
        List<GoPreVo> goPreVoList = new ArrayList<>();

        //1.根据userId查找所有此用户参与的ttp
        List<UserTtp> userTtpList = userTtpRepository.findByUserIdOrderByUpdateTimeDesc(userId);
        if(userTtpList == null){
            log.info("【查询我参与的所有ttp】还没有参加任何ttp");
            return null;
        }
        //2.遍历userTtp中的各项信息
        for(UserTtp userTtp : userTtpList) {
            GoPreVo goPreVo = new GoPreVo();
            //3.根据userId查找到发起人信息


            //4.根据ttpId查找到对用ttp信息
            TtpDetail ttpDetail = tTpDetailRepository.findByTtpId(userTtp.getTtpId());
            UserInfo faqiuser = userInfoRepository.findByUserId(ttpDetail.getUserId());
            goPreVo.setUserName(faqiuser.getUsername());
            goPreVo.setUserIcon(faqiuser.getUserIcon());
            goPreVo.setTtpId(ttpDetail.getTtpId());
            goPreVo.setTtpName(ttpDetail.getTtpName());
            //中途退出的状态来自userTtpStatus
            goPreVo.setTtpStatus(userTtp.getUserTtpStatus());
            //当为进行中状态时才计算具体属性
            if(userTtp.getUserTtpStatus().equals(UserTtpStatusEnum.WORKING.getCode())) {
                //5.录入userTtp表的信息
                goPreVo.setUserTotalBouns(userTtp.getUserTotalBouns());
                //5-1. 根据这一个ttpId查找到所有参与此ttp的userTtp
                List<UserTtp> userTtpList1 = userTtpRepository.findByTtpId(userTtp.getTtpId());
                BigDecimal allUserTtpBounds = new BigDecimal(BigInteger.ZERO);
                int i = 0;
                for(UserTtp userTtp1 : userTtpList1) {
                    allUserTtpBounds.add(userTtp1.getUserTotalBouns());
                    i++;
                }
                //小于的情况
                if(userTtp.getUserTotalBouns().compareTo(allUserTtpBounds.divide(new BigDecimal(i))) == -1) {
                    goPreVo.setHighAverage(EnumUtil.getByCode(3, AverageEnum.class).getMessage());
                }
                //等于的情况
                else if(userTtp.getUserTotalBouns().compareTo(allUserTtpBounds.divide(new BigDecimal(i))) == 0) {
                    goPreVo.setHighAverage(EnumUtil.getByCode(2, AverageEnum.class).getMessage());
                }
                //大于的时候
                else if(userTtp.getUserTotalBouns().compareTo(allUserTtpBounds.divide(new BigDecimal(i))) == 1) {
                    goPreVo.setHighAverage(EnumUtil.getByCode(1, AverageEnum.class).getMessage());
                }

                //设置用户当日状态
                if(ttpDetail.getTtpStatus().equals(TtpStatusEnum.WORKING.getCode())) {
                    List<UserRecord> userRecordList = userRecordRepository.findByUserTtpId(userTtp.getUserTtpId());
                    for(UserRecord userRecord : userRecordList){
                        try {
                            if(DateUtil.StringToDate1(currentDate).equals(userRecord.getDays())) {
                                goPreVo.setUserCurrentRecord(EnumUtil.getByCode(userRecord.getDayStatus(), DayStatusEnum.class).getMessage());
                            }
                        } catch (ParseException e) {
                            log.error("2017/01/01转换Date日期格式出错");
                        }

                    }
                }
            }

            //8.设置总的govoList
            goPreVoList.add(goPreVo);
        }
        return goPreVoList;
    }

    @Override
    public GoVo getMyOneTtp(String ttpId, String userId, String currentDate) {
        //1.根据userttpId查找对应的某条userttp信息
        UserTtp userTtp = userTtpRepository.findByUserTtpId(userId+ttpId);
        //log.info("【UserTtp信息】：result={}", JsonUtil.toJson(userTtp));
        GoVo goVo = new GoVo();

        //4.根据ttpId查找到对用ttp信息
        TtpDetail ttpDetail = tTpDetailRepository.findByTtpId(userTtp.getTtpId());
        //3.根据ttpId查找到发起人信息
        UserInfo faqiuser = userInfoRepository.findByUserId(ttpDetail.getUserId());
        log.info("[go页面的发起人信息：]"+faqiuser.getUsername());
        goVo.setUserName(faqiuser.getUsername());
        goVo.setUserIcon(faqiuser.getUserIcon());
        goVo.setTtpId(ttpDetail.getTtpId());
        goVo.setTtpName(ttpDetail.getTtpName());
        goVo.setFaqiType(ttpDetail.getFaqiType());
        goVo.setTtpStatus(userTtp.getUserTtpStatus());
        //返回时间需处理下格式,转换成"2017/10/01 19:00"的字符串
        goVo.setStartTime(dateFormat2(ttpDetail.getStartTime(), 0,16));
        goVo.setFinishTime(dateFormat2(ttpDetail.getFinishTime(), 0,16));
        //TODO 输入目标数，返回对应结果,现在假定运动类ttp才有目标
        if(ttpDetail.getTtpType().equals(TtpTypeEnum.SPORTS.getCode())){
            goVo.setTtpTarget(TtpTargetTemplate.runenum(ttpDetail.getTtpTarget()));
        }

        goVo.setJoinMoney(ttpDetail.getJoinMoney());
        goVo.setLeaveNotesNums(ttpDetail.getLeaveNotesNums());
        goVo.setUserTotalBouns(userTtp.getUserTotalBouns());
        goVo.setLeaveNotes(userTtp.getLeaveNotes());
        //goVo.setIfQuit(EnumUtil.getByCode(ttpDetail.getIfQuit(), IfQuitEnum.class).getMessage());
        goVo.setIfJoin(EnumUtil.getByCode(ttpDetail.getIfJoin(), IfJoinEnum.class).getMessage());
        goVo.setIfOpen(EnumUtil.getByCode(ttpDetail.getIfOpen(), IfOpenEnum.class).getMessage());
        //5.录入userTtp表的信息

        goVo.setLeaveNotes(userTtp.getLeaveNotes());
        //如果ttp状态在进行中时才进行用户记录查询操作，否则不查询
        if (userTtp.getUserTtpStatus().equals(TtpStatusEnum.WORKING.getCode())) {
            long allTime = (ttpDetail.getFinishTime().getTime() - ttpDetail.getStartTime().getTime()) / (86400 * 1000);
            long finishTime = (System.currentTimeMillis() - ttpDetail.getStartTime().getTime()) / (86400 * 1000);
            log.info("完成时间={},总时间={}", finishTime, allTime);
            goVo.setTtpSchedule(new Formatter().format("%.2f", finishTime * 1.0 / allTime).toString());
            /**设置是否高于平均奖金**/
            List<UserTtp> userTtpList3 = userTtpRepository.findByTtpId(userTtp.getTtpId());
            BigDecimal allUserTtpBounds = new BigDecimal(BigInteger.ZERO);
            int i = 0;
            for(UserTtp userTtp1 : userTtpList3) {
                allUserTtpBounds.add(userTtp1.getUserTotalBouns());
                i++;
            }
            //小于的情况
            if(userTtp.getUserTotalBouns().compareTo(allUserTtpBounds.divide(new BigDecimal(i))) == -1) {
                goVo.setHighAverage(EnumUtil.getByCode(3, AverageEnum.class).getMessage());
            }
            //等于的情况
            else if(userTtp.getUserTotalBouns().compareTo(allUserTtpBounds.divide(new BigDecimal(i))) == 0) {
                goVo.setHighAverage(EnumUtil.getByCode(2, AverageEnum.class).getMessage());
            }
            //大于的时候
            else if(userTtp.getUserTotalBouns().compareTo(allUserTtpBounds.divide(new BigDecimal(i))) == 1) {
                goVo.setHighAverage(EnumUtil.getByCode(1, AverageEnum.class).getMessage());
            }

            //6.根据userTtpId查找此ttp的用户记录表
            List<UserRecordVo> userRecordVoList = new ArrayList<>();
            List<UserRecord> userRecordList = userRecordRepository.findByUserTtpId(userTtp.getUserTtpId());
            for(UserRecord userRecord : userRecordList){
                UserRecordVo userRecordVo = new UserRecordVo();
                userRecordVo.setDays(dateFormat2(userRecord.getDays(), 5, 10));
                userRecordVo.setDayStatus(userRecord.getDayStatus());
                userRecordVoList.add(userRecordVo);
            }
            goVo.setUserRecordList(userRecordVoList);
            //7.根据ttpId查到所有参加此ttp的用户
            List<OthersRecordVo> othersRecordVoList = new ArrayList<>();
            List<OthersRecordVo> othersRecordVoList1 = new ArrayList<>();
            List<UserTtp> userTtpList1 = userTtpRepository.findByTtpId(userTtp.getTtpId());
            //依次遍历参加了此ttp的userTtp信息
            for(UserTtp userTtp1 : userTtpList1){
                OthersRecordVo othersRecordVo = new OthersRecordVo();
                //根据userId查询到其中一个用户的userInfo信息，并设置一条OthersRecordVo信息
                UserInfo userInfo = userInfoRepository.findByUserId(userTtp1.getUserId());
                if(userInfo != null){
                    //设置username
                    othersRecordVo.setUsername(userInfo.getUsername());
                    log.info("查询用户记录："+userTtp1.getUserTtpId()+currentDate);
                    UserRecord userRecord = userRecordRepository.findByUserRecordId(userTtp1.getUserTtpId()+currentDate);
                    //再根据userTtpId信息查找到此用户对应的ttp的所有完成记录，形参为当天日期，查询当天完成记录情况
                    if(userRecord.getDayStatus().equals(DayStatusEnum.FINIS.getCode())){
                        //说明完成了，显示信息为21：00，设置finishTime录入list
                        othersRecordVo.setDayStatus(dateFormat2(
                                (userRecord.getUpdateTime()),
                                11, 16));
                        othersRecordVoList.add(othersRecordVo);
                    } else if(userRecord.getDayStatus().equals(DayStatusEnum.QINGJIA.getCode())) {
                        //请假，设置finishTime为具体信息
                        othersRecordVo.setDayStatus(EnumUtil.getByCode(DayStatusEnum.QINGJIA.getCode(), DayStatusEnum.class).getMessage());
                        othersRecordVoList1.add(othersRecordVo);
                    } else {
                        //未完成，只用显示用户名即可
                        othersRecordVo.setDayStatus("");
                        othersRecordVoList1.add(othersRecordVo);
                    }
                } else {
                    log.error("【查询我参与的所有ttp】userRecord为空");
                    throw new TTpException(ResultEnum.PARAM_ERROR);
                }
            }
            goVo.setOthersfinishList(othersRecordVoList);
            goVo.setOthersnofinishList(othersRecordVoList1);
        }
        //log.info("Go详情页面的数据，result={}", JsonUtil.toJson(goVo));
        return goVo;
    }

    @Override
    public UserTtp quit(String userId, String ttpId, String currentDate) {
        //1.查询此userTtp信息
        String userTtpId = userId+ttpId;
        UserTtp userTtp = userTtpRepository.findByUserTtpId(userTtpId);
        if(userTtp == null) {
            log.error("【取消订单】 查不到该订单，userTtpId={}", userTtp.getUserTtpId());
            throw new TTpException(ResultEnum.ORDER_NOT_EXIST);
        }

        return faqiService.canel(userTtp, currentDate);

    }

}
