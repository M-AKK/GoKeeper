package com.gokeeper.service.impl;

import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.repository.UserInfoRepository;
import com.gokeeper.service.UserService;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.utils.KeyUtil;
import com.gokeeper.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

/**
 * @Descrption: 用户操作类
 * @author: Created by Akk_Mac
 * @Date: 2017/10/2 09:37
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfoVo getUserByphone(String searchmap) {
        UserInfoVo result = new UserInfoVo();
        try {
            UserInfo userInfo = userInfoRepository.findByPhonenumber(searchmap);
            if(userInfo != null) {
                log.info("【根据phone查找user成功】result={}", JsonUtil.toJson(userInfo));
                BeanUtils.copyProperties(userInfo, result);
            }
            return result;
        } catch (IncorrectResultSizeDataAccessException e) {
            //这是捕获到手机号两个以上用户手机号重复的情况
            throw new TTpException(ResultEnum.USER_REPEAT);
        }
    }

    @Override
    public UserInfo loginAndSave(UserInfo userInfo) {
        //1.判断用户是否已经存在,不为null说明存在就直接返回
        if(userInfo.getPhonenumber() != null) {
            UserInfo result = userInfoRepository.findByPhonenumber(userInfo.getPhonenumber());
            if(result != null){ return result; }
        } else if(userInfo.getQqOpenid() != null) {
            UserInfo result = userInfoRepository.findByQqOpenid(userInfo.getQqOpenid());
            if(result != null){ return result; }
        } else if(userInfo.getWxOpenid() != null) {
            UserInfo result = userInfoRepository.findByWxOpenid(userInfo.getWxOpenid());
            if(result != null){ return result; }
        }
        //2.不存在就新建一个用户
        String userId = KeyUtil.genUniqueKey();
        userInfo.setUserId(userId);
        UserInfo result = userInfoRepository.save(userInfo);

        return result;
    }

    @Override
    public UserInfoVo getuserById(String userId) {
        UserInfoVo result = new UserInfoVo();
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        if(userInfo == null) {
            throw new TTpException(ResultEnum.CHECK_USER);
        }
        BeanUtils.copyProperties(userInfo, result);
        return result;
    }

    @Override
    public UserInfo modifyUser(UserInfo userInfo) {

        UserInfo result = userInfoRepository.save(userInfo);
        if(result == null) {
            throw new TTpException(ResultEnum.USER_MODIFY_ERROR);
        }
        return result;
    }
}
