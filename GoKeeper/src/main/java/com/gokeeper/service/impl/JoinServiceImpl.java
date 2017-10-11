package com.gokeeper.service.impl;

import com.gokeeper.VO.JoinVo;
import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.enums.*;
import com.gokeeper.repository.TTpRepository;
import com.gokeeper.repository.UserInfoRepository;
import com.gokeeper.repository.UserTtpRepository;
import com.gokeeper.service.JoinService;
import com.gokeeper.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TTpRepository tTpRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserTtpRepository userTtpRepository;


    @Override
    public List<JoinVo> getOpenTtp() {
        List<JoinVo> joinVoList = new ArrayList<>();

        //1.先查找所有公开ttp的详情信息
        List<TtpDetail> openttpdetaillist = tTpRepository.findByIfOpen(1);
        for(TtpDetail detail : openttpdetaillist) {
            JoinVo joinVo = new JoinVo();
            //2.根据userid查找userinfo
            UserInfo userInfo = userInfoRepository.findByUserId(detail.getUserId());
            joinVo.setUsername(userInfo.getUsername());
            joinVo.setUserIcon(userInfo.getUserIcon());
            //设置ttp相关信息
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
}
