package com.gokeeper.utils.converter;

import com.gokeeper.dto.InviteNewsDto;
import com.gokeeper.dto.SystemNewsDto;
import com.gokeeper.dto.TtpNewsDto;
import com.gokeeper.utils.DateUtil;
import com.gokeeper.vo.news.InviteNewsVo;
import com.gokeeper.vo.news.SystemNewsVo;
import com.gokeeper.vo.news.TtpNewsVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 把Dto转换为Vo对象，也就是转化一下updateTime属性
 * @author Created by Akk_Mac
 * @Date: 2017/10/21 19:12
 */
public class NewsDtoZNewsVoConverter {

    /**
     * 系统Dto转Vo
     * @param c
     * @return
     */
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

    /**
     * TTPNEWS Dto转Vo
     * @param c
     * @return
     */
    public static TtpNewsVo TtpNewsDtoZVoconvert(TtpNewsDto c){
        TtpNewsVo result = new TtpNewsVo();
        BeanUtils.copyProperties(c, result);
        result.setUpdateTime(DateUtil.dateFormat2(c.getUpdateTime(), 0, 16));
        result.setStartTime(DateUtil.dateFormat2(c.getStartTime(), 0, 16));
        result.setFinishTime(DateUtil.dateFormat2(c.getFinishTime(), 0, 16));
        return result;
    }

    public static List<TtpNewsVo> TtpNewsDtoZVoconvert(List<TtpNewsDto> c){
        return c.stream().map(e ->
                TtpNewsDtoZVoconvert(e)
        ).collect(Collectors.toList());
    }

    /**
     * 邀请消息Dto转Vo
     * @param c
     * @return
     */
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
