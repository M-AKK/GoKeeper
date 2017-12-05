package com.gokeeper.controller.api;

import com.gokeeper.constant.UserInfoConstant;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.dataobject.UserTtp;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.service.GoService;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.utils.ResultVoUtil;
import com.gokeeper.vo.GoPreVo;
import com.gokeeper.vo.GoVo;
import com.gokeeper.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Go界面控制层
 * @author Created by Akk_Mac
 * Date: 2017/10/5 19:30
 */
@RestController
@RequestMapping("/go")
@Slf4j
public class GoController {

    @Autowired
    private GoService goService;

    /**
     * 获取Go(我的)界面默认list，主要展示自己参与的所有ttp
     * @param currentDate 查询当天其他用户记录用
     * @return ResultVO
     */
    @GetMapping
    public ResultVO goList(@RequestParam("currentDate") String currentDate,
                             HttpServletRequest request){
        //log.info("【go界面的请求地址】"+request.getRequestURI());
        //根据seesion获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        List<GoPreVo> result = null;
        if(user != null) {
            //log.info("【go界面的user登录】"+user.getUserId());
            result = goService.getMyTtpList(user.getUserId(), currentDate);
        }
        if(result != null) {
            if(result.size() != 0 ) {
                //log.info("【GO界面数据】"+ JsonUtil.toJson(result));
                return ResultVoUtil.success(result);
            } else {
                return ResultVoUtil.success();
            }
        }
        return ResultVoUtil.success();

    }

    /**
     * 查询ttp详情
     * @param ttpId
     * @param currentDate
     * @param request
     * @return
     */
    @GetMapping(value = "/{ttpId}")
    public ResultVO getOne(@PathVariable("ttpId") String ttpId,
                           @RequestParam("currentDate") String currentDate,
                           HttpServletRequest request) {
        //根据seesion获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        GoVo result = null;
        if(user != null) {
            result = goService.getMyOneTtp(ttpId, user.getUserId(), currentDate);
        }
        return ResultVoUtil.success(result);
    }

    /**
     * 确认完成操作
     * @param ttpId
     * @param currentDate
     * @param request
     * @return
     */
    @PostMapping(value = "/finish/{ttpId}")
    public ResultVO finish(@PathVariable("ttpId") String ttpId,
                           @RequestParam("currentDate") String currentDate,
                           HttpServletRequest request) {
        //根据seesion获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        UserTtp userTtp = goService.finish(user.getUserId(), ttpId, currentDate);
        if(userTtp != null) {
            return ResultVoUtil.success();
        }
        return ResultVoUtil.error(ResultEnum.TTP_QUIT_ERROR.getCode(), ResultEnum.TTP_QUIT_ERROR.getMessage());
    }

    /**
     * 中途退出ttp操作
     * @param ttpId
     * @param currentDate
     * @param request
     * @return
     */
    @PostMapping(value = "/quit/{ttpId}")
    public ResultVO quit(@PathVariable("ttpId") String ttpId,
                         @RequestParam("currentDate") String currentDate,
                         HttpServletRequest request) {
        //根据seesion获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        goService.quit(user.getUserId(), ttpId, currentDate);
        return ResultVoUtil.success();

    }


}
