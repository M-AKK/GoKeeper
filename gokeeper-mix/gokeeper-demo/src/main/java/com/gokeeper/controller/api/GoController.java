package com.gokeeper.controller.api;

import com.gokeeper.constant.UserInfoConstant;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.service.GoService;
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
        //根据seesion获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        List<GoPreVo> result = goService.getMyTtpList(user.getUserId(), currentDate);
        return ResultVoUtil.success(result);
    }

    @GetMapping(value = "/{ttpId}")
    public ResultVO getOne(@PathVariable("ttpId") String ttpId,
                           @RequestParam("currentDate") String currentDate,
                           HttpServletRequest request) {
        //根据seesion获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        GoVo result = goService.getMyOneTtp(ttpId, user.getUserId(), currentDate);
        return ResultVoUtil.success(result);
    }


}
