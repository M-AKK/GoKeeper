package com.gokeeper.controller.api;

import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.service.JoinService;
import com.gokeeper.utils.ResultVOUtil;
import com.gokeeper.vo.JoinVo;
import com.gokeeper.vo.ResultVO;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    //加入列表展示
    @GetMapping(value = "/list")
    public ResultVO getallList(HttpServletRequest request){

        //返回所有公开的ttp
        List<JoinVo> joinVoList = joinService.getOpenTtp();

        return ResultVOUtil.success(joinVoList);
    }

    //加入功能的实现
    @PostMapping(value = "/attend")
    public ResultVO atttend(HttpServletRequest request,
                            @RequestParam("ttpId") String ttpId){

        //1.接收需要加入的ttpId
        //2.根据session查找用户id
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if(StringUtils.isNullOrEmpty(userId)){
            log.error("【user查询】userId为空");
            throw new TTpException(ResultEnum.USER_ERROR);
        }

        try{
            joinService.attend(userId, ttpId);
        } catch (TTpException e) {
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }

        return ResultVOUtil.success();
    }

}
