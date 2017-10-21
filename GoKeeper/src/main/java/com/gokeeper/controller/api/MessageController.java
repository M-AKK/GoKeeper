package com.gokeeper.controller.api;

import com.gokeeper.VO.news.AllNewsVo;
import com.gokeeper.VO.ResultVO;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.service.AllNewsService;
import com.gokeeper.utils.ResultVOUtil;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Akk_Mac
 * Date: 2017/10/13 18:23
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
    @GetMapping(value = "/list")
    public ResultVO list(HttpServletRequest request, @RequestParam("hidden") Integer hidden){

        //1.首先根据session获取userId
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if(StringUtils.isNullOrEmpty(userId)){
            log.error("【查询我参与的所有ttp】userId为空");
            throw new TTpException(ResultEnum.USER_ERROR);
        }

        AllNewsVo allNewsVo = allNewsService.findAllOpenMsg(userId, hidden);
        return ResultVOUtil.success(allNewsVo);
    }

}
