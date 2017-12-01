package com.gokeeper.enums;

import java.math.BigDecimal;

/**
 * @Description:
 * @author: Created by Akk_Mac
 * @Date: 2017/10/29 11:17
 */
public class NewsTemplate {

    /**
     * 个人创建ttp后没有支付发出的消息
     * @param newsname
     * @param startTime
     * @return
     */
    public static String createTtpNews(String newsname, String startTime) {
        return "你成功创建" + newsname + "活动" + "，" +
                "请在活动开始时间:"+ startTime + "，前进行支付";
    }

    /**
     * 他人加入一个ttp后没有支付发出的消息
     * @param newsname
     * @param startTime
     * @return
     */
    public static String joinTtpNews(String newsname, String startTime) {
        return "你成功加入" + newsname + "活动" + "，" +
                "请在活动开始时间:"+ startTime + "，前进行支付";
    }

    /**
     * 这是支付成功后的ttp消息
     * @param newsname
     * @param startTime
     * @return
     */
    public static String payTtpNews(String newsname, String startTime) {
        return "你已成功支付" + newsname + "活动" + "，" +
                "活动将在:" +  startTime + "开始";
    }


    public static String dayTtpNews() {
        return "活动已正式开始";
    }

    public static String noPayTtpNews() {
        return "活动已开始，还未支付，支付成功才能参加活动";
    }
}
