package com.gokeeper.service.impl;

import com.gokeeper.vo.UserInfoVo;
import com.gokeeper.constant.RedisConstant;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.repository.UserInfoRepository;
import com.gokeeper.service.UserService;
import com.gokeeper.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public String getUserId(String token) {

        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, token));
        return tokenValue;
    }

    @Override
    public UserInfoVo getUserByphone(String phonenumber) {
        UserInfoVo result = new UserInfoVo();
        try {
            UserInfo userInfo = userInfoRepository.findByPhonenumber(phonenumber);
            log.info("【根据phone查找user1】result={}", JsonUtil.toJson(userInfo));
            if(userInfo != null) {
                BeanUtils.copyProperties(userInfo, result);
            } else{
                log.info("【根据phone查找user失败】result={}", JsonUtil.toJson(userInfo));
            }
            return result;
        } catch (IncorrectResultSizeDataAccessException e) {
            //这是捕获到手机号两个以上用户手机号重复的情况
            throw new TTpException(ResultEnum.USER_REPEAT);
        }

    }
}
