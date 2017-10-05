package com.gokeeper.utils;

import java.text.*;
import java.util.*;

/**
 * Created by Akk_Mac
 * Date: 2017/10/2 12:48
 */

public class DateUtil {

    /*public static void main(String args[])throws Exception{
        List<String> result = getBetweenDates(DateUtil.StringToDate("2017-10-1 21:08"),DateUtil.StringToDate("2017-10-8 21:08"));
        for(int i=0; i<result.size(); i++){
            System.out.println(result.get(i));
        }
    }*/

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
     * 将"2015-08-31 21:08:06"型字符串转化为Date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String str) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = (Date) formatter.parse(str);
        return date;
    }

    /**
     * 将"2015-08-31"型字符串转化为Date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date StringToDate1(String str) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) formatter.parse(str);
        return date;
    }

    /**
     * 获取两个日期之间的所有日期（yyyy-MM-dd）
     * @Description TODO
     * @param begin
     * @param end
     * @return
     * @author XuJD
     * @date 2017-4-1
     */
    public static List<String> getBetweenDates(Date begin, Date end) throws ParseException{
        List<String> result = new ArrayList<>();
        //List<String> datelist = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
            /* Calendar tempEnd = Calendar.getInstance();
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            tempEnd.setTime(end);
            while (tempStart.before(tempEnd)) {
                result.add(tempStart.getTime());
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }*/
        while(begin.getTime()<=end.getTime()){

            result.add(format.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }


}

