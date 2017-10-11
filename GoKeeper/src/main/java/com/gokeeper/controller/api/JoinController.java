package com.gokeeper.controller.api;

import com.gokeeper.VO.JoinVo;
import com.gokeeper.VO.ResultVO;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.service.JoinService;
import com.gokeeper.utils.ResultVOUtil;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 加入界面控制层
 * Created by Akk_Mac
 * Date: 2017/10/6 20:56
 */
@RequestMapping("/join")
@Slf4j
public class JoinController {

    @Autowired
    private JoinService joinService;

    @GetMapping(value = "/list")
    public ResultVO getallList(HttpServletRequest request){

        //只返回公开的ttp
        List<JoinVo> joinVoList = joinService.getOpenTtp();

        return ResultVOUtil.success(joinVoList);
    }
}
