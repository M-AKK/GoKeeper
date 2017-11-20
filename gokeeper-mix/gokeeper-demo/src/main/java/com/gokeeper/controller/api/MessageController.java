package com.gokeeper.controller.api;

import com.gokeeper.constant.UserInfoConstant;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.service.AllNewsService;
import com.gokeeper.utils.ResultVoUtil;
import com.gokeeper.vo.ResultVO;
import com.gokeeper.vo.news.AllNewsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author:Created by Akk_Mac
 * @Date: 2017/10/13 18:23
 */
@RestController
@RequestMapping("/msg")
@Slf4j
public class MessageController {

    @Autowired
    private AllNewsService allNewsService;

    /**
     * 主要在用户登录后用来显示需要显示的信息，根据传值来判断显示
     * @param request
     * @return
     */
    @GetMapping
    public ResultVO list(@RequestParam("hidden") Integer hidden,
                         HttpServletRequest request){

        //1.首先根据session获取userId
        //UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        //log.info("查询所有消息，userid="+user.getUserId());
        List<AllNewsVo> allNewsVo = allNewsService.findAllOpenMsg("1511091449434479239", hidden);
        return ResultVoUtil.success(allNewsVo);
    }

    @GetMapping(value = "/{msgId}")
    public ResultVO getOneNew(@PathVariable("msgId") String msgId) {

        Object result = allNewsService.getOneMsg(msgId);
        return ResultVoUtil.success(result);
    }

    @DeleteMapping(value = "/{msgId}")
    public ResultVO deleteOneMsg(@PathVariable("msgId") String msgId) {
        //改变这条消息的hidden属性即可
        //TODO 删除失败是否判断？
        Object result = allNewsService.deleteOneMsg(msgId);
        return ResultVoUtil.success();
    }

    @PostMapping(value = "/{msgId}")
    public ResultVO dingOneMsg(@PathVariable("msgId") String msgId) {
        Object result = allNewsService.dingOneMsg(msgId);
        return ResultVoUtil.success();
    }

}
