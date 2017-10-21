package com.gokeeper.utils.Converter;

import com.gokeeper.VO.news.InviteNewsVo;
import com.gokeeper.VO.news.SystemNewsVo;
import com.gokeeper.VO.news.TtpNewsVo;
import com.gokeeper.dataobject.InviteNews;
import com.gokeeper.dataobject.SystemNews;
import com.gokeeper.dataobject.TtpNews;
import com.gokeeper.dto.InviteNewsDto;
import com.gokeeper.dto.SystemNewsDto;
import com.gokeeper.dto.TtpNewsDto;
import com.gokeeper.utils.DateUtil;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 把Dto转换为Vo对象，也就是转化一下updateTime属性
 * Created by Akk_Mac
 * Date: 2017/10/21 19:12
 */
public class NewsDtoZNewsVoConverter {

    //系统Bean转Dto
    public static SystemNewsVo SystemNewsDtoZVoconvert(SystemNewsDto c){
        SystemNewsVo result = new SystemNewsVo();
        BeanUtils.copyProperties(c, result);
        result.setUpdateTime(DateUtil.dateFormat2(c.getUpdateTime(), 0, 16));
        return result;
    }

    public static List<SystemNewsVo> SystemNewsDtoZVoconvert(List<SystemNewsDto> c){
        return c.stream().map(e ->
                SystemNewsDtoZVoconvert(e)
        ).collect(Collectors.toList());
    }

    //TTPNEWS Bean转Dto
    public static TtpNewsVo TtpNewsDtoZVoconvert(TtpNewsDto c){
        TtpNewsVo result = new TtpNewsVo();
        BeanUtils.copyProperties(c, result);
        result.setUpdateTime(DateUtil.dateFormat2(c.getUpdateTime(), 0, 16));
        return result;
    }

    public static List<TtpNewsVo> TtpNewsDtoZVoconvert(List<TtpNewsDto> c){
        return c.stream().map(e ->
                TtpNewsDtoZVoconvert(e)
        ).collect(Collectors.toList());
    }

    //邀请消息Bean转Dto
    public static InviteNewsVo InviteNewsDtoZVoconvert(InviteNewsDto c){
        InviteNewsVo result = new InviteNewsVo();
        BeanUtils.copyProperties(c, result);
        result.setUpdateTime(DateUtil.dateFormat2(c.getUpdateTime(), 0, 16));
        return result;
    }

    public static List<InviteNewsVo> InviteNewsDtoZVoconvert(List<InviteNewsDto> c){
        return c.stream().map(e ->
                InviteNewsDtoZVoconvert(e)
        ).collect(Collectors.toList());
    }
}
