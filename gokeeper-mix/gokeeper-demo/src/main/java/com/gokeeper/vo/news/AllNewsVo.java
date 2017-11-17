package com.gokeeper.vo.news;

import lombok.Data;

import java.util.List;

/**
 * 返回消息的总包装类型
 * @author Created by Akk_Mac
 * @Date: 2017/10/18 18:40
 */
@Data
public class AllNewsVo {

    private List<SystemNewsVo> systemNewsList;

    private List<TtpNewsVo> ttpNewsList;

    private List<InviteNewsVo> inviteNewsList;
}
