package com.gokeeper.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Description:ttptype的实体类
 * @author: Created by Akk_Mac
 * @Date: 2017/11/6 22:11
 */
@Data
@Entity
public class TtpType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String typeName;

    private String imgUrl;

    private Integer parentId;

    private Integer orderNum;
}
