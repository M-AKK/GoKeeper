package com.gokeeper.service.impl;

import com.gokeeper.VO.JoinVo;
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
import com.gokeeper.service.JoinService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.gokeeper.utils.DateUtil.dateFormat2;

/**
 * joinservice具体实现
 * Created by Akk_Mac
 * Date: 2017/10/7 09:45
 */
@Service
@Slf4j
public class JoinServiceImpl implements JoinService {

    @Autowired
    private TTpDetailRepository tTpDetailRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserTtpRepository userTtpRepository;

    @Autowired
    private UserRecordRepository userRecordRepository;

    /**
     * 加入界面列表展示
     * @return
     */
    @Override
    public List<JoinVo> getOpenTtp() {
        List<JoinVo> joinVoList = new ArrayList<>();

        //1.先查找所有公开ttp的详情信息
        List<TtpDetail> openttpdetaillist = tTpDetailRepository.findByIfOpen(1);
        for(TtpDetail detail : openttpdetaillist) {
            JoinVo joinVo = new JoinVo();
            //2.根据userid查找userinfo
            UserInfo userInfo = userInfoRepository.findByUserId(detail.getUserId());
            joinVo.setUsername(userInfo.getUsername());
            joinVo.setUserIcon(userInfo.getUserIcon());
            //设置ttp相关信息
            joinVo.setTtpId(detail.getTtpId());
            joinVo.setTtpName(detail.getTtpName());
            joinVo.setTtpType(EnumUtil.getByCode(detail.getTtpType(), TtpTypeEnum.class).getMessage());
            joinVo.setCreateTime(dateFormat2(detail.getCreateTime(), 0,16));
            joinVo.setStartTime(dateFormat2(detail.getStartTime(), 0,16));
            joinVo.setFinishTime(dateFormat2(detail.getFinishTime(), 0, 16));
            joinVo.setTtpTarget(TtpTargetEnum.runenum(detail.getTtpTarget()));
            joinVo.setJoinMoney(detail.getJoinMoney());
            joinVo.setAllMoney(detail.getAllMoney());
            //设置当前参与总人数
            joinVo.setJoinPeopleNums(userTtpRepository.findByTtpId(detail.getTtpId()).size());
            joinVo.setLeaveNotesNums(detail.getLeaveNotesNums());
            joinVo.setIfJoin(EnumUtil.getByCode(detail.getIfJoin(), IfJoinEnum.class).getMessage());
            joinVo.setIfQuit(EnumUtil.getByCode(detail.getIfQuit(), IfQuitEnum.class).getMessage());
            joinVoList.add(joinVo);
        }
        return joinVoList;
    }

    /**
     * 加入功能，把用户加入到已知ttp列表中
     * @param userId
     * @param ttpId
     * @return
     */
    @Transactional//事务管理，一旦失败就回滚
    public UserTtp attend(String userId, String ttpId){
        //设置起始每日奖金总额为0
        BigDecimal zeroBouns = new BigDecimal(BigInteger.ZERO);

        //生成user-ttp关联表信息
        UserTtp userTtp = new UserTtp();
        userTtp.setUserTtpId(ttpId+userId);//根据ttpid和userid生成
        userTtp.setUserId(userId);
        userTtp.setTtpId(ttpId);
        userTtp.setUserDayBouns(zeroBouns);
        userTtp.setUserTotalBouns(zeroBouns);//用户个人得到的总奖金，初始也为0
        userTtp.setPayStatus(0);
        userTtp.setTtpSchedule(0);//设置开始进度为0
        userTtpRepository.save(userTtp);
        //查找ttpDetail信息
        TtpDetail ttpDetail = tTpDetailRepository.findByTtpId(ttpId);
        //用户记录表录入
        try{
            //根据开始时间和结束时间算出中间日期
            List<String> datelist = DateUtil.getBetweenDates(ttpDetail.getStartTime(), ttpDetail.getFinishTime());
            for(int i=0; i<datelist.size(); i++){
                UserRecord userRecord = new UserRecord();
                userRecord.setUserRecordId(ttpId + datelist.get(i));
                userRecord.setUserTtpId(userTtp.getUserTtpId());
                userRecord.setDays(DateUtil.StringToDate1(datelist.get(i)));
                userRecord.setDayStatus(0);
                userRecordRepository.save(userRecord);
            }
        } catch(ParseException e) {
            log.error("【日期转换出问题】");
            throw new TTpException(ResultEnum.CREATE_ERROR);
        }

        return userTtp;
    }
}
