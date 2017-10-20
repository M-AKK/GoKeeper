package com.gokeeper.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 系统消息实体
 * Created by Akk_Mac
 * Date: 2017/10/18 12:14
 */
@Entity
@DynamicUpdate
@Data
public class SystemNews {

    //消息id
    @Id
    private String id;

    //消息拥有者id
    private String userId;

    //消息类型
    private Integer newstype;

    //发起人name
    private String username;

    //发起人头像
    private String userIcon;

    private String newsname;

    //状态，已读(0),未读(1);
    private Integer newsstatus;

    //是否隐藏，0为公开，1为隐藏
    private Integer hidden;

    //预览信息
    private String previewText;

    //正文
    private String text;

    //更新时间
    private Date updateTime;

    //权重
    private Integer weight;
}
