package com.gokeeper.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by KHM
 * 2017/7/27 17:50
 */
public class KeyUtil {

    /**
     * 生成唯一的主键(订单Id)
     * 格式：时间+随机数
     * 防止多线程会重复要加synchronized
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        System.currentTimeMillis();
        //这样生成的随机数就是6位了
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }

    //下面就是实现为数据库获取一个唯一的主键id的代码

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }


}
