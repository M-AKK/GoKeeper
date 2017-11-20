package com.gokeeper.controller.api;

import com.gokeeper.constant.UserInfoConstant;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.exception.TTpException;
import com.gokeeper.service.JoinService;
import com.gokeeper.utils.ResultVoUtil;
import com.gokeeper.vo.JoinPreVo;
import com.gokeeper.vo.JoinVo;
import com.gokeeper.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 加入界面控制层
 * @author Created by Akk_Mac
 * @Date: 2017/10/6 20:56
 */
@RestController
@RequestMapping("/join")
@Slf4j
public class JoinController {

    @Autowired
    private JoinService joinService;

    //加入预览列表展示
    @GetMapping
    public ResultVO getAllList(){
        //返回所有公开的ttp
        List<JoinPreVo> joinPreVoList = joinService.getOpenTtp();
        return ResultVoUtil.success(joinPreVoList);
    }

    //加入界面某个ttp详情
    @GetMapping(value = "/{ttpId}")
    public ResultVO getOneTtp(@PathVariable("ttpId") String ttpId) {
        JoinVo result = joinService.getOneTtp(ttpId);
        return ResultVoUtil.success(result);
    }

    //加入功能的实现
    @PostMapping(value = "/{ttpId}")
    public ResultVO atttend(HttpServletRequest request,
                            @PathVariable("ttpId") String ttpId){
        //1.接收需要加入的ttpId
        //2.根据session查找用户id
        //根据seesion获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);

        try{
            joinService.attend(user.getUserId(), ttpId);
        } catch (TTpException e) {
            return ResultVoUtil.error(e.getCode(), e.getMessage());
        }

        return ResultVoUtil.success();
    }

}
