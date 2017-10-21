package com.gokeeper.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by Akk_Mac
 * Date: 2017/10/21 18:36
 */
@Data
public class InviteNewsDto {

    private String id;

    //消息类型
    private Integer newstype;

    //发起人name
    private String username;

    //发起人头像
    private String userIcon;

    private String newsname;

    //状态，已读(0),未读(1);
    private Integer newsstatus;

    //预览消息
    private String preview_text;

    private String ttpId;

    //更新时间
    private Date updateTime;

    private Integer weight;
}
