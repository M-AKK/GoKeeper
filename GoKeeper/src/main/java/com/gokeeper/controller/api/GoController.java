package com.gokeeper.controller.api;

import com.gokeeper.VO.GoVo;
import com.gokeeper.VO.ResultVO;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.service.GoService;
import com.gokeeper.service.UserService;
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
import java.util.Date;
import java.util.List;

/**
 * Go界面控制层
 * Created by Akk_Mac
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
    @GetMapping(value = "/list")
    public ResultVO faqilist(HttpServletRequest request,
                             @RequestParam("currentDate") String currentDate){
        //1.首先根据session获取userId
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if(StringUtils.isNullOrEmpty(userId)){
            log.error("【查询我参与的所有ttp】userId为空");
            throw new TTpException(ResultEnum.USER_ERROR);
        }

        List<GoVo> goVoList = goService.getmyttplist(userId, currentDate);
        return ResultVOUtil.success(goVoList);
    }


}
