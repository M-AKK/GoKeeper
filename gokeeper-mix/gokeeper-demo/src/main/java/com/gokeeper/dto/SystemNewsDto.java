package com.gokeeper.dto;

import lombok.Data;

import java.util.Date;

/**
 * 系统消息的Dto对象，用来转换Vo对象中的updateTime属性，方便复制Bean属性
 * Created by Akk_Mac
 * Date: 2017/10/21 11:31
 */
@Data
public class SystemNewsDto {
    //消息id
    private String id;

    //发起人name
    private String username;

    //发起人头像
    private String userIcon;

    //消息类型
    private Integer newstype;


    private String newsname;

    //状态，已读(0),未读(1);
    private Integer newsstatus;

    //预览信息
    private String previewText;

    //正文
    private String text;

    //更新时间
    private Date updateTime;

    private Integer weight;
}
