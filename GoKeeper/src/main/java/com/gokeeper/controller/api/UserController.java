package com.gokeeper.controller.api;

import com.gokeeper.vo.ResultVO;
import com.gokeeper.vo.UserInfoVo;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.service.UserService;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息操作类
 * @author:Created by Akk_Mac
 * @Date: 2017/10/22 17:13
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/getUserByPhone")
    public ResultVO getUserByPhone(@RequestParam("phonenumber") String phonenumber){
        System.out.println(phonenumber);
        //根据phone查找用户信息
        UserInfoVo userInfoVo = userService.getUserByphone(phonenumber);
        if(userInfoVo!=null){
            log.info("【根据phone查找user】result={}", JsonUtil.toJson(userInfoVo));
            return ResultVOUtil.success(userInfoVo);
        } else {
            return ResultVOUtil.error(ResultEnum.CHECK_USER.getCode(), ResultEnum.CHECK_USER.getMessage());
        }

    }

}
