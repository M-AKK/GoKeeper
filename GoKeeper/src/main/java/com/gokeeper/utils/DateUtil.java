package com.gokeeper.utils;

import java.text.*;
import java.util.*;

/**
 * @author: Created by Akk_Mac
 * @Date: 2017/10/2 12:48
 */

public class DateUtil {

    public static void main(String[] args)throws Exception{
        /*List<String> result = getBetweenDates(DateUtil.StringToDate("2017-10-1 21:08"),DateUtil.StringToDate("2017-10-8 21:08"));
        for(int i=0; i<result.size(); i++){
            System.out.println(result.get(i));
        }

        String str = dateFormat2(DateUtil.StringToDate("2017-10-1 21:08"), 11,16);//16或者10
        System.out.println(str);*/

        Date currentTime = new Date();
        String s = DateUtil.dateFormat2(currentTime, 0, 10);
        System.out.println(s);
    }

    /**
     * date类型进行格式化输出
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 现将date转成字符串
     * 再将"2017-10-01 21:08:00"型字符串转换成"2017/10/01"型字符串或者自定义区域
     * @param date
     * @return
     */
    public static String dateFormat2(Date date, int start, int end) {
        //1.首先把date转成string
        String str = (dateFormat(date)).substring(start, end);
        String str1 = str.replace("-", "/");

        return str1;
    }

    /**
     * 将"2015/08/31 21:08"型字符串转化为Date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String str) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = (Date) formatter.parse(str);
        return date;
    }

    /**
     * 将"2015/08/31"型字符串转化为Date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date StringToDate1(String str) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = (Date) formatter.parse(str);
        return date;
    }

    /**
     * 获取两个日期之间的所有日期（yyyy/MM/dd）
     * @Description 传入date，传出string
     * @param begin
     * @param end
     * @return
     * @author XuJD
     * @date 2017-4-1
     */
    public static List<String> getBetweenDates(Date begin, Date end) throws ParseException{
        List<String> result = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while(begin.getTime()<=end.getTime()){
            result.add(format.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }


}

