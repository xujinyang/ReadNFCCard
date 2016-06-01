package readcard.lauway.com.readcard.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.ele.omniknight.common.tools.AppLogger;

/**
 * 
 * @author Jinyang
 * 
 */
public class StringUtils {
    public static final int WEEKDAYS = 7;
    public static String[] WEEK = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     * 
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input)||"NULL".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * @param b
     * @return
     */
    public static String hexToHexString(byte[] b) {
        int len = b.length;
        int[] x = new int[len];
        String[] y = new String[len];
        StringBuilder str = new StringBuilder();
        int j = 0;
        for (; j < len; j++) {
            x[j] = b[j] & 0xff;
            y[j] = Integer.toHexString(x[j]);
            while (y[j].length() < 2) {
                y[j] = "0" + y[j];
            }
            str.append(y[j]);
            str.append("");
        }
        return new String(str).toUpperCase(Locale.getDefault());
    }

    /**
     * 日期变量转成对应的星期字符串
     * 
     * @param date
     * @return
     */
    public static String DateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }

    /**
     * 状态改变为int型
     * 
     * @param status
     * @return
     */
    public static int statusToInt(String status) {
        int i = 0;
        if (status.equals("R")) {
            i = 1;
        } else if (status.equals("T")) {
            i = 2;
        } else if (status.equals("D")) {
            i = 3;
        } else if (status.equals("F")) {
            i = 4;
        } else if (status.equals("S")) {
            i = 5;
        }
        return i;

    }

    public static String getMatchDayStr(int i) {
        String dayStr = "";
        if (i >= 0) {
            dayStr = i + "天后";
        } else {
            dayStr = -i + "天前";
        }
        switch (i) {
            case -2:
                dayStr = "前天";
                break;
            case -1:
                dayStr = "昨天";

                break;
            case 0:
                dayStr = "今天";

                break;
            case 1:
                dayStr = "明天";

                break;
            case 2:
                dayStr = "后天";

                break;

            default:
                break;
        }
        return dayStr;

    }

    /**
     * 获得两个日期之间相差的天数
     * 
     * @param time1
     *            ：开始时间
     * @param time2
     *            ：结束时间
     * @return：两个日期之间相差的天数
     */
    public static final int daysBetween(Date early, Date late) {

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        // 设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        // 得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;
        return days;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMouth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * 获得两个日期之间相差的小时
     * 
     * @param time1
     *            ：开始时间
     * @param time2
     *            ：结束时间
     * @return：两个日期之间相差的天数
     */
    public static final int hoursBetween(Date early, Date late) {

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        // 设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        // 得到两个日期相差的天数
        int hours = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600;
        return hours;
    }

    /**
     * 获得两个日期之间相差的分钟
     * 
     * @param time1
     *            ：开始时间
     * @param time2
     *            ：结束时间
     * @return：两个日期之间相差的天数
     */
    @SuppressWarnings("deprecation")
    public static final double minutesBetween(Date early, Date late) {

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        // 设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        // 得到两个日期相差的天数
        double minutes = ((long) (caled.getTimeInMillis() / 1000) - (long) (calst.getTimeInMillis() / 1000));
        AppLogger.e("开始时间" + early.toLocaleString() + "结束时间" + late.toLocaleString() + "相隔时间：" + minutes);
        return minutes;
    }

    /**
     * 将数字字符串转换成groupScale一组的
     * 
     * @param stringToSplit
     * @param groupScale
     * @return
     */
    public static String split(String stringToSplit, int groupScale) {

        String pduCodeString = stringToSplit; // 要分隔的字符串

        int splitNum = groupScale; // 设置每多少个字组成一组

        int arrayNum = 0; // 初始化arrayNum，计算得到的分组个数
        String str = "";

        int yuShu = pduCodeString.length() % splitNum; // 是否正好除尽

        if (yuShu == 0) // 正好除尽
        {
            arrayNum = pduCodeString.length() / splitNum;
        } else // 未除尽
        {
            arrayNum = pduCodeString.length() / splitNum + 1;
        }
        for (int i = 0; i < arrayNum - 1; i++) {
            str = str + pduCodeString.substring(0, splitNum) + " , ";
            pduCodeString = pduCodeString.substring(splitNum);
        }
        str = str + pduCodeString.substring(0); // 防止splitNum越界，所以最后那组单独来赋值。
        AppLogger.e(str);
        return str;
    }

}
