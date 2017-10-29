package com.gokeeper.controller.api;

import com.gokeeper.vo.ResultVO;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.dto.TtpDetailDto;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.form.TtpForm;
import com.gokeeper.service.FaqiService;
import com.gokeeper.service.UserService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 发起界面控制层
 * Created by Akk_Mac
 * Date: 2017/10/1 10:17
 */
@RestController
@RequestMapping("/ttp")
@Slf4j
public class FaqiController {


    @Autowired
    private FaqiService faqiService;

    @Autowired
    private UserService userService;

    //创建一个ttp订单
    @PostMapping(value = "/create")
    public ResultVO creat(@Valid TtpForm ttpForm,
                          BindingResult bindingResult, HttpServletRequest request) throws Exception{
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确, ttpForm={}", ttpForm);
            throw new TTpException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //根据token获取userId,获取不到会返回错误
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if(StringUtils.isEmpty(user.getUserId())) {
            log.warn("【登录校验】Redis中查不到tokenValue");
            throw new TTpException(ResultEnum.TOKEN_MISS);
        }

        //把部分属性复制到Dto，并不会复制两个时间
        TtpDetailDto detailDto = new TtpDetailDto();
        BeanUtils.copyProperties(ttpForm,detailDto);
        //设置开始时间和结束时间
        detailDto.setStartTime(DateUtil.StringToDate(ttpForm.getStartTime()));
        detailDto.setFinishTime(DateUtil.StringToDate(ttpForm.getFinishTime()));
        detailDto.setUserId(user.getUserId());
        TtpDetailDto ttpDetailDto = faqiService.create(detailDto);

        Map<String, String> map = new HashMap<>();
        map.put("ttpId", ttpDetailDto.getTtpId());
        return ResultVOUtil.success(map);
    }
}
