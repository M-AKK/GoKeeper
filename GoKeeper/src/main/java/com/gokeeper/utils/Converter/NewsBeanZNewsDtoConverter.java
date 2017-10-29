package com.gokeeper.utils.converter;

import com.gokeeper.dataobject.InviteNews;
import com.gokeeper.dataobject.SystemNews;
import com.gokeeper.dataobject.TtpNews;
import com.gokeeper.dto.InviteNewsDto;
import com.gokeeper.dto.SystemNewsDto;
import com.gokeeper.dto.TtpNewsDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * news实体类转化到Dto对象，过滤掉不需要的字段
 * @author Created by Akk_Mac
 * @Date: 2017/10/21 18:39
 */
public class NewsBeanZNewsDtoConverter {

    /**
     * 系统Bean转Dto
     * @param c
     * @return
     */
    public static SystemNewsDto SystemNewsZDtoconvert(SystemNews c){
        SystemNewsDto systemNewsDto = new SystemNewsDto();
        BeanUtils.copyProperties(c, systemNewsDto);
        return systemNewsDto;
    }

    public static List<SystemNewsDto> SystemNewsZDtoconvert(List<SystemNews> c){
        return c.stream().map(e ->
                SystemNewsZDtoconvert(e)
        ).collect(Collectors.toList());
    }

    /**
     * TTPNEWS Bean转Dto
     * @param c
     * @return
     */
    public static TtpNewsDto TtpNewsZDtoconvert(TtpNews c){
        TtpNewsDto ttpNewsDto = new TtpNewsDto();
        BeanUtils.copyProperties(c, ttpNewsDto);
        return ttpNewsDto;
    }

    public static List<TtpNewsDto> TtpNewsZDtoconvert(List<TtpNews> c){
        return c.stream().map(e ->
                TtpNewsZDtoconvert(e)
        ).collect(Collectors.toList());
    }

    /**
     * 邀请消息Bean转Dto
     * @param c
     * @return
     */
    public static InviteNewsDto InviteNewsZDtoconvert(InviteNews c){
        InviteNewsDto inviteNewsDto = new InviteNewsDto();
        BeanUtils.copyProperties(c, inviteNewsDto);
        return inviteNewsDto;
    }

    public static List<InviteNewsDto> InviteNewsZDtoconvert(List<InviteNews> c){
        return c.stream().map(e ->
                InviteNewsZDtoconvert(e)
        ).collect(Collectors.toList());
    }
}
