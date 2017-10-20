package com.gokeeper.VO.news;

import lombok.Data;

/**
 * Created by Akk_Mac
 * Date: 2017/10/18 21:25
 */
@Data
public class SystemNewsVo {

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
    private String updateTime;

    private Integer weight;
}
