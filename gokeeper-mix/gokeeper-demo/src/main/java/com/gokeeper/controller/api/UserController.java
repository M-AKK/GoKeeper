package com.gokeeper.controller.api;

import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.service.UserService;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.utils.ResultVoUtil;
import com.gokeeper.vo.ResultVO;
import com.gokeeper.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * 模糊查找用户信息，邀请是搜索用户使用
     * @param searchmap
     * @return
     */
    @GetMapping(value = "/SUser")
    public ResultVO getUserByPhone(@RequestParam("searchmap") String searchmap){
        System.out.println(searchmap);
        //根据phone查找用户信息
        List<UserInfoVo> userInfoVo = userService.getUserByphone(searchmap);
        if(userInfoVo!=null){
            //log.info("【根据phone查找user】result={}", JsonUtil.toJson(userInfoVo));
            return ResultVoUtil.success(userInfoVo);
        } else {
            return ResultVoUtil.error(ResultEnum.CHECK_USER.getCode(), ResultEnum.CHECK_USER.getMessage());
        }
    }

}