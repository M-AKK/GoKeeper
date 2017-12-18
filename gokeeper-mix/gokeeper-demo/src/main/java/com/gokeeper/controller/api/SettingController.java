package com.gokeeper.controller.api;

import com.gokeeper.constant.UserInfoConstant;
import com.gokeeper.dataobject.User;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.dataobject.UserRecord;
import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.repository.UserInfoRepository;
import com.gokeeper.repository.UserRecordRepository;
import com.gokeeper.repository.UserTtpRepository;
import com.gokeeper.service.UserService;
import com.gokeeper.utils.ResultVoUtil;
import com.gokeeper.vo.ResultVO;
import com.gokeeper.vo.UserInfoVo;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 设置界面
 * @author: Created by Akk_Mac
 * @Date: 2017/11/20 11:27
 */
@RestController
@RequestMapping(value = "/setting")
@Slf4j
public class SettingController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserTtpRepository userTtpRepository;

    @Autowired
    private UserRecordRepository userRecordRepository;


    /**
     * 设置界面获取用户基本信息
     * @return
     */
    @GetMapping
    public ResultVO getUserSetting(HttpServletRequest request) {
        //1.首先根据session获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        UserInfoVo result = null;
        if(user != null) {
            result  = userService.getuserById(user.getUserId());
        }

        return ResultVoUtil.success(result);
    }

    /**
     * 保存用户信息
     * @param username
     * @param sex
     * @param birthday
     * @param city
     * @param phonenumber
     * @param request
     * @return
     */
    @PostMapping
    public ResultVO modifyUser( @RequestParam("userIcon") String userIcon,
                                @RequestParam("username") String username,
                                @RequestParam("sex") Integer sex,
                                @RequestParam("birthday") String birthday,
                                @RequestParam("city") String city,
                                @RequestParam("phonenumber") String phonenumber,
                                HttpServletRequest request) {
        //1.首先根据session获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);

        //2.直接把值设置到session的user中
        user.setUsername(username);
        user.setSex(sex);
        user.setBirthday(birthday);
        user.setPhonenumber(phonenumber);
        user.setCity(city);
        user.setUserIcon(userIcon);
        UserInfo result = null;
        if(user != null) {
            result  = userService.modifyUser(user);
        }
        return ResultVoUtil.success(result);
    }

    /**
     * QQ绑定操作
     * @return
     */
    @PostMapping(value = "/qq/binding")
    public ResultVO qqBinDing(@RequestParam("openid") String openId,
                            HttpServletRequest request) {
        //1.首先根据session获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        //2.查找原来有没有用QQ登录过
        UserInfo userInfo = userInfoRepository.findByQqOpenid(openId);
        if(userInfo != null) {
            //以前用QQ登录过，先合并记录再删除QQ登录账号
            List<UserTtp> userTtpList = userTtpRepository.findByUserId(userInfo.getUserId());
            if(userTtpList != null) {
                for(UserTtp userTtp : userTtpList) {
                    List<UserRecord> userRecordList = userRecordRepository.findByUserTtpId(userTtp.getUserTtpId());
                    for(UserRecord userRecord : userRecordList) {
                        userRecord.setUserTtpId(userInfo.getUserId()+userTtp.getTtpId());
                        String date = userRecord.getUserRecordId().substring(38, 48);
                        userRecord.setUserRecordId(userInfo.getUserId()+userTtp.getTtpId()+date);
                        userRecordRepository.save(userRecord);
                    }
                    userTtp.setUserId(userInfo.getUserId());
                    userTtp.setUserTtpId(userInfo.getUserId()+userTtp.getTtpId());
                    userTtpRepository.save(userTtp);
                }
            }
        }
        user.setQqOpenid(openId);
        UserInfo result = null;
        if(user != null) {
            result = userService.modifyUser(user);
        }
        return ResultVoUtil.success();
    }

    /**
     * QQ解绑操作
     * @param request
     * @return
     */
    @PostMapping(value = "/qq/unbundling")
    public ResultVO qqUnBundling(HttpServletRequest request) {
        //1.首先根据session获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        user.setQqOpenid("");
        UserInfo result = null;
        if(user != null ) {
            result = userService.modifyUser(user);
        }
        return ResultVoUtil.success();
    }


    /**
     * 微信绑定操作
     * @return
     */
    @PostMapping(value = "/wx/binding")
    public ResultVO wxBinDing(@RequestParam("openid") String openId,
                            HttpServletRequest request) {
        //1.首先根据session获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        //2.查找原来有没有用QQ登录过
        UserInfo userInfo = userInfoRepository.findByWxOpenid(openId);
        if(userInfo != null) {
            //以前用QQ登录过，先合并记录再删除QQ登录账号
            List<UserTtp> userTtpList = userTtpRepository.findByUserId(userInfo.getUserId());
            if(userTtpList != null) {
                for(UserTtp userTtp : userTtpList) {
                    List<UserRecord> userRecordList = userRecordRepository.findByUserTtpId(userTtp.getUserTtpId());
                    for(UserRecord userRecord : userRecordList) {
                        userRecord.setUserTtpId(userInfo.getUserId()+userTtp.getTtpId());
                        String date = userRecord.getUserRecordId().substring(38, 48);
                        userRecord.setUserRecordId(userInfo.getUserId()+userTtp.getTtpId()+date);
                        userRecordRepository.save(userRecord);
                    }
                    userTtp.setUserId(userInfo.getUserId());
                    userTtp.setUserTtpId(userInfo.getUserId()+userTtp.getTtpId());
                    userTtpRepository.save(userTtp);
                }
            }
        }
        user.setWxOpenid(openId);
        UserInfo result = null;
        if(user != null) {
            result = userService.modifyUser(user);
        }

        return ResultVoUtil.success();
    }

    /**
     * 微信解绑操作
     * @param request
     * @return
     */
    @PostMapping(value = "/wx/unbundling")
    public ResultVO wxUnBundling(HttpServletRequest request) {
        //1.首先根据session获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        user.setWxOpenid("");
        UserInfo result = null;
        if(user != null) {
            result = userService.modifyUser(user);
        }

        return ResultVoUtil.success();
    }


}
