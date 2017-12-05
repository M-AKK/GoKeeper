package com.gokeeper.controller.api;

import com.gokeeper.constant.UserInfoConstant;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.dto.TtpDetailDto;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.form.TtpForm;
import com.gokeeper.service.FaqiService;
import com.gokeeper.service.UserService;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.utils.ResultVoUtil;
import com.gokeeper.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 发起界面控制层
 * @author Created by Akk_Mac
 * Date: 2017/10/1 10:17
 */
@RestController
@RequestMapping("/ttp")
@Slf4j
public class FaqiController {

    @Autowired
    private FaqiService faqiService;

    //创建一个ttp订单
    @PostMapping(value = "/create")
    public ResultVO creat(@Valid TtpForm ttpForm,
                          BindingResult bindingResult, HttpServletRequest request) throws Exception{
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确, ttpForm={}", ttpForm);
            throw new TTpException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //根据seesion获取userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        if(user != null) {
            //把部分属性复制到Dto，并不会复制两个时间
            TtpDetailDto detailDto = new TtpDetailDto();
            BeanUtils.copyProperties(ttpForm,detailDto);
            //设置开始时间和结束时间
            detailDto.setStartTime(DateUtil.StringToDate(ttpForm.getStartTime()));
            if(ttpForm.getFinishTime()!=null) {
                detailDto.setFinishTime(DateUtil.StringToDate(ttpForm.getFinishTime()));
            } else {
                detailDto.setFinishTime(DateUtil.StringToDate("2099/08/31 21:08"));
            }
            detailDto.setUserId(user.getUserId());
            detailDto.setTtpTarget(Double.valueOf(ttpForm.getTtpTarget()));
            TtpDetailDto ttpDetailDto = faqiService.create(detailDto);

            Map<String, String> map = new HashMap<>();
            map.put("ttpId", ttpDetailDto.getTtpId());
            return ResultVoUtil.success(map);
        }
        return ResultVoUtil.success();

    }

    @GetMapping(value = "/alltype")
    public ResultVO findAllType(){
        return ResultVoUtil.success(faqiService.findAllType());
    }

}
