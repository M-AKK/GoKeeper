package com.gokeeper.VO.news;

import lombok.Data;

/**
 * 返回邀请消息的包装类型
 * Created by Akk_Mac
 * Date: 2017/10/18 21:26
 */
@Data
public class InviteNewsVo {

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
    private String updateTime;

    private Integer weight;
}
