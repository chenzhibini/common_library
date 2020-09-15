package com.hdyg.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author CZB
 * @describe 时间转换工具
 * @time 2020/4/6 15:24
 */
public class DateUtil {

    /**
     * 枚举日期格式
     */
    public enum DatePattern {
        /**
         * 格式："yyyy-MM-dd HH:mm:ss"
         */
        ALL_TIME {
            public String getValue() {
                return "yyyy-MM-dd HH:mm:ss";
            }
        },
        /**
         * 格式："yyyy-MM"
         */
        ONLY_MONTH {
            public String getValue() {
                return "yyyy-MM";
            }
        },
        /**
         * 格式："yyyy-MM-dd"
         */
        ONLY_DAY {
            public String getValue() {
                return "yyyy-MM-dd";
            }
        },
        /**
         * 格式："yyyy-MM-dd HH"
         */
        ONLY_HOUR {
            public String getValue() {
                return "yyyy-MM-dd HH";
            }
        },
        /**
         * 格式："yyyy-MM-dd HH:mm"
         */
        ONLY_MINUTE {
            public String getValue() {
                return "yyyy-MM-dd HH:mm";
            }
        },
        /**
         * 格式："MM-dd"
         */
        ONLY_MONTH_DAY {
            public String getValue() {
                return "MM-dd";
            }
        },
        /**
         * 格式："MM-dd HH:mm"
         */
        ONLY_MONTH_SEC {
            public String getValue() {
                return "MM-dd HH:mm";
            }
        },
        /**
         * 格式："HH:mm:ss"
         */
        ONLY_TIME {
            public String getValue() {
                return "HH:mm:ss";
            }
        },
        /**
         * 格式："HH:mm"
         */
        ONLY_HOUR_MINUTE {
            public String getValue() {
                return "HH:mm";
            }
        };

        public abstract String getValue();
    }


    /**
     * 获取系统时间戳
     *
     * @return 时间戳
     */
    public long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * 获取当前时间
     *
     * @param pattern 需要转换的格式 yyyy/MM/dd
     * @return 当前时间
     */
    public static String getCurDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date());
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param milSecond 时间戳
     * @param pattern   日期格式 yyyy/MM/dd
     * @return 时间
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString 时间戳字符串
     * @param pattern    日期格式 yyyy/MM/dd
     * @return 时间戳
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 时间戳转换成秒
     *
     * @param timeLong 时间戳
     * @return 秒数
     */
    public static String getStringToSS(long timeLong) {
        long ss = timeLong / 1000;
        String s;
        if (ss % 60 < 10) {
            s = "0" + ss % 60;
        } else {
            s = ss % 60 + "";
        }
        return s;
    }

    /**
     * 时间戳转换成小时
     *
     * @param timeLong 时间戳
     * @return 小时
     */
    public static String getStringToHH(long timeLong) {
        long hh = timeLong / 1000 / 60 / 60;
        String h;
        if (hh % 60 < 10) {
            h = "0" + hh % 60;
        } else {
            h = hh % 60 + "";
        }
        return h;
    }

    /**
     * 时间戳转换成分钟
     *
     * @param timeLong 时间戳
     * @return 分钟
     */
    public static String getStringToMM(long timeLong) {
        long mm = timeLong / 1000 / 60;
        String m;
        if (mm % 60 < 10) {
            m = "0" + mm % 60;
        } else {
            m = mm % 60 + "";
        }
        return m;
    }

    //秒转成时分秒
    public static String formatTimeS(long seconds) {
        int temp = 0;
        StringBuffer sb = new StringBuffer();
        if (seconds > 3600) {
            temp = (int) (seconds / 3600);
            sb.append((seconds / 3600) < 10 ? "0" + temp + ":" : temp + ":");
            temp = (int) (seconds % 3600 / 60);
            changeSeconds(seconds, temp, sb);
        } else {
            temp = (int) (seconds % 3600 / 60);
            changeSeconds(seconds, temp, sb);
        }
        return sb.toString();
    }

    private static void changeSeconds(long seconds, int temp, StringBuffer sb) {
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");
        temp = (int) (seconds % 3600 % 60);
        sb.append((temp < 10) ? "0" + temp : "" + temp);
    }

    //转换成天时分秒
    public static String getStrOfSeconds(final long seconds) {
        if (seconds < 0) {
            return "秒数必须大于0";
        }
        long one_day = 60 * 60 * 24;
        long one_hour = 60 * 60;
        long one_minute = 60;
        long day, hour, minute, second = 0L;

        day = seconds / one_day;
        hour = seconds % one_day / one_hour;
        minute = seconds % one_day % one_hour / one_minute;
        second = seconds % one_day % one_hour % one_minute;

        if (seconds < one_minute) {
            return seconds + "秒";
        } else if (seconds >= one_minute && seconds < one_hour) {
            return minute + "分" + second + "秒";
        } else if (seconds >= one_hour && seconds < one_day) {
            return hour + "时" + minute + "分" + second + "秒";
        } else {
            return day + "天" + hour + "时" + minute + "分" + second + "秒";
        }
    }

}
