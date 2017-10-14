package com.gokeeper.service.impl;

import com.gokeeper.dataobject.TtpDetail;
import com.gokeeper.dataobject.UserRecord;
import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.dto.TtpDetailDto;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.repository.TTpDetailRepository;
import com.gokeeper.repository.UserRecordRepository;
import com.gokeeper.repository.UserTtpRepository;
import com.gokeeper.service.FaqiService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;


/**
 * 发起界面操作的具体实现
 * Created by Akk_Mac
 * Date: 2017/10/1 22:57
 */
@Service
@Slf4j
public class FaqiServiceImpl implements FaqiService {

    @Autowired
    private TTpDetailRepository tTpDetailRepository;

    @Autowired
    private UserTtpRepository userTtpRepository;

    @Autowired
    private UserRecordRepository userRecordRepository;

    /**
     * 创建一个新ttp
     * @param ttpDetailDto
     * @return
     */
    @Override
    @Transactional//事务管理，一旦失败就回滚
    public TtpDetailDto create(TtpDetailDto ttpDetailDto) {
        //设置起始每日奖金总额为0
        BigDecimal zeroBouns = new BigDecimal(BigInteger.ZERO);

        //1.设置下订单id(是个随机，这里调用了根据时间产生16位随机数的方法)
        String ttpId = KeyUtil.genUniqueKey();

        //2.把剩下的属性再设置好,ttp详情入库
        ttpDetailDto.setTtpId(ttpId);
        ttpDetailDto.setAllMoney(ttpDetailDto.getJoinMoney());
        ttpDetailDto.setTtpStatus(0);
        TtpDetail ttpDetail = new TtpDetail();
        BeanUtils.copyProperties(ttpDetailDto, ttpDetail);
        tTpDetailRepository.save(ttpDetail);

        //3.user-ttp关联信息入库
        UserTtp userTtp = new UserTtp();
        userTtp.setUserTtpId(ttpId+ttpDetailDto.getUserId());//根据ttpid和userid生成
        userTtp.setUserId(ttpDetailDto.getUserId());
        userTtp.setTtpId(ttpId);
        userTtp.setUserDayBouns(zeroBouns);
        userTtp.setUserTotalBouns(zeroBouns);//用户个人得到的总奖金，初始也为0
        userTtp.setPayStatus(0);
        userTtp.setTtpSchedule(0);//设置开始进度为0
        userTtpRepository.save(userTtp);

        //4.创建此ttp的用户完成情况记录表
        try{
            //根据开始时间和结束时间算出中间日期
            List<String> datelist = DateUtil.getBetweenDates(ttpDetailDto.getStartTime(), ttpDetailDto.getFinishTime());
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

        /**TODO 消息推送1：
         当用户填写完ttp基本信息后点击下一步时到达支付页面之前，后台会有个消息推送为:
         Go页面前端会检测到有新的发送消息，然后css判断用户有咩有点击，没有点击就是未读
        */

        return ttpDetailDto;
    }
}
