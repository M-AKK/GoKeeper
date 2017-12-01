package com.gokeeper.service.impl;

import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.enums.IfOpenEnum;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.enums.TtpTargetTemplate;
import com.gokeeper.enums.TtpTypeEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.repository.TTpDetailRepository;
import com.gokeeper.repository.UserInfoRepository;
import com.gokeeper.repository.UserTtpRepository;
import com.gokeeper.service.SearchService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.EnumUtil;
import com.gokeeper.vo.JoinPreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gokeeper.utils.DateUtil.dateFormat2;

/**
 * @Description:
 * @author: Created by Akk_Mac
 * @Date: 2017/11/20 19:14
 */
@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private TTpDetailRepository tTpDetailRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserTtpRepository userTtpRepository;

    @Override
    public List<JoinPreVo> highSearch(Integer ttpType, String minMoney, String maxMoney, String startTime, String finishTime) {
        //只有ttpType的情况
        if(minMoney == null && startTime == null) {
            //1.先查找所有公开并且符合类型ttp的详情信息
            List<TtpDetail> openttpdetaillist = tTpDetailRepository.findByIfOpenAndTtpType(IfOpenEnum.YES.getCode(), ttpType);
            List<JoinPreVo> joinPreVoList = getjoinPreVoList(openttpdetaillist);
            return joinPreVoList;
        } else if(minMoney !=null && startTime != null) {
            //2.按全条件搜索
            BigDecimal minmoney1 = new BigDecimal(minMoney);
            BigDecimal maxmoney1 = new BigDecimal(maxMoney);
            Date startTime1 = new Date();
            Date finishTime1 = new Date();
            try{
                startTime1 = DateUtil.StringToDate2(startTime);
                finishTime1 = DateUtil.StringToDate2(finishTime);
            } catch (Exception e) {
                throw new TTpException(ResultEnum.DATE_ERROE);
            }
            List<TtpDetail> openttpdetaillist = tTpDetailRepository.highsSearch(IfOpenEnum.YES.getCode(), ttpType, minmoney1, maxmoney1, startTime1, finishTime1);
            List<JoinPreVo> joinPreVoList = getjoinPreVoList(openttpdetaillist);
            return joinPreVoList;
        } else if(minMoney != null && startTime == null ) {
            BigDecimal minmoney1 = new BigDecimal(minMoney);
            BigDecimal maxmoney1 = new BigDecimal(maxMoney);
            List<TtpDetail> openttpdetaillist = tTpDetailRepository.moneySearch(IfOpenEnum.YES.getCode(), ttpType, minmoney1, maxmoney1);
            List<JoinPreVo> joinPreVoList = getjoinPreVoList(openttpdetaillist);
            return joinPreVoList;
        } else if(startTime !=null && minMoney == null) {
            Date startTime1 = new Date();
            Date finishTime1 = new Date();
            try{
                startTime1 = DateUtil.StringToDate2(startTime);
                finishTime1 = DateUtil.StringToDate2(finishTime);
            } catch (Exception e) {
                throw new TTpException(ResultEnum.DATE_ERROE);
            }
            List<TtpDetail> openttpdetaillist = tTpDetailRepository.timeSearch(IfOpenEnum.YES.getCode(), ttpType, startTime1, finishTime1);
            List<JoinPreVo> joinPreVoList = getjoinPreVoList(openttpdetaillist);
            return joinPreVoList;
        }
        return null;
    }

    //加入预览信息查询后的转换方法
    private List<JoinPreVo> getjoinPreVoList(List<TtpDetail> openttpdetaillist) {
        List<JoinPreVo> joinPreVoList = new ArrayList<>();
        for(TtpDetail detail : openttpdetaillist) {
            JoinPreVo joinPreVo = new JoinPreVo();
            //2.根据userid查找userinfo
            UserInfo userInfo = userInfoRepository.findByUserId(detail.getUserId());
            joinPreVo.setUsername(userInfo.getUsername());
            joinPreVo.setUserIcon(userInfo.getUserIcon());
            //设置ttp相关信息
            joinPreVo.setTtpId(detail.getTtpId());
            joinPreVo.setTtpName(detail.getTtpName());
            joinPreVo.setTtpType(EnumUtil.getByCode(detail.getTtpType(), TtpTypeEnum.class).getMessage());
            joinPreVo.setCreateTime(dateFormat2(detail.getCreateTime(), 0,16));
            joinPreVo.setStartTime(dateFormat2(detail.getStartTime(), 0,16));

            //TODO 输入目标数，返回对应结果,现在假定运动类ttp才有目标
            if(detail.getTtpType().equals(TtpTypeEnum.SPORTS.getCode())){
                joinPreVo.setTtpTarget(TtpTargetTemplate.runenum(detail.getTtpTarget()));
            }
            joinPreVo.setAllMoney(detail.getAllMoney());
            //设置当前参与总人数
            joinPreVo.setJoinPeopleNums(userTtpRepository.findByTtpId(detail.getTtpId()).size());
            joinPreVoList.add(joinPreVo);
        }
        return joinPreVoList;
    }


}
