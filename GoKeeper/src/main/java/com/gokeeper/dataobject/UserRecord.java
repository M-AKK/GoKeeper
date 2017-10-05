package com.gokeeper.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户记录实体表
 * Created by Akk_Mac
 * Date: 2017/10/3 12:43
 */
@Entity
@Data
@DynamicUpdate
public class UserRecord {

    @Id
    private String userRecordId;

    private String userTtpId;

    //第几天
    private Date days;

    //完成状态
    private Integer dayStatus;

    //完成时间
    private Date updateTime;
}
