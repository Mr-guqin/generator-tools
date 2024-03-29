package com.koo.generator.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author dengtao
 * @description 日期工具类
 * @date 2020/3/18
 */
public class DateUtil extends DateUtils {

    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String HH_MM_SS = "HH:mm:ss";
    public static String YYYY_MM_DD_CHINESE = "yyyy年M月d日";
    public static String YYYYMM = "yyyyMM";
    public static String YYYYMMDD = "yyyyMMdd";
    public static String YYYYMMDDHHMMSS = "yyyyMMdd HH:mm:ss";
    public static String YYYYMMDDHHMMSS_SPECAIL = "yyyyMMddHHmmss";
    public static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    /**
     * 01. java.util.Date --> java.time.LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    /**
     * 02. java.util.Date --> java.time.LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    /**
     * 03. java.util.Date --> java.time.LocalTime
     *
     * @param date
     * @return
     */
    public static LocalTime dateToLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime;
    }

    /**
     * 04. java.time.LocalDateTime --> java.util.Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * 05. java.time.LocalDate --> java.util.Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    public static LocalDate localDateTimeToLocalDate(LocalDateTime localDateTime) {

        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    public static Date dateTodate(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        String date1 = df.format(date);

        try {
            return df.parse(date1);
        } catch (ParseException var5) {
            return date;
        }
    }

    public static String getCurrentDate() {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
        datestr = df.format(new Date());
        return datestr;
    }

    public static String getCurrentDateTime() {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        datestr = df.format(new Date());
        return datestr;
    }

    public static String getCurrentDateTime(String Dateformat) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(Dateformat);
        datestr = df.format(new Date());
        return datestr;
    }

    public static String dateToDateTime(Date date) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        datestr = df.format(date);
        return datestr;
    }

    public static Date stringToDate(String datestr) {
        if (StringUtils.isNotEmpty(datestr)) {
            new Date();
            SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);

            Date date;
            try {
                date = df.parse(datestr);
            } catch (ParseException var4) {
                date = stringToDate(datestr, "yyyyMMdd");
            }

            return date;
        } else {
            return null;
        }
    }

    public static Date stringToDate(String datestr, String dateformat) {
        SimpleDateFormat df = new SimpleDateFormat(dateformat);

        try {
            Date date = df.parse(datestr);
            return date;
        } catch (ParseException var5) {
            throw new RuntimeException("时间转换异常");
        }
    }

    public static String dateToString(Date date) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
        datestr = df.format(date);
        return datestr;
    }

    public static String dateToString(Date date, String dateformat) {
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        datestr = df.format(date);
        return datestr;
    }


    public static Date getFirstDayOfMonth(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.set(5, 1);
        return cd.getTime();
    }

    public static Date getFirstDayOfMonth(Date date, int day) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.set(5, day);
        return cd.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        return addDay(getFirstDayOfMonth(addMonth(date, 1)), -1);
    }

    public static boolean isLeapYEAR(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int year = cd.get(1);
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static Date getDateByYMD(int year, int month, int day) {
        Calendar cd = Calendar.getInstance();
        cd.set(year, month - 1, day);
        return cd.getTime();
    }

    public static Date getYearCycleOfDate(Date date, int iyear) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(1, iyear);
        return cd.getTime();
    }


    public static int getYearByDate(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        return cd.get(1);
    }

    public static Date getMonthCycleOfDate(Date date, int i) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(2, i);
        return cd.getTime();
    }

    public static int getYearByMinusDate(Date fromDate, Date toDate) {
        Calendar df = Calendar.getInstance();
        df.setTime(fromDate);
        Calendar dt = Calendar.getInstance();
        dt.setTime(toDate);
        return dt.get(1) - df.get(1);
    }

    public static int getMonthByMinusDate(Date fromDate, Date toDate) {
        Calendar df = Calendar.getInstance();
        df.setTime(fromDate);
        Calendar dt = Calendar.getInstance();
        dt.setTime(toDate);
        return dt.get(1) * 12 + dt.get(2) - (df.get(1) * 12 + df.get(2));
    }

    public static long getDayByMinusDate(Object fromDate, Object toDate) {
        Date f = chgObject(fromDate);
        Date t = chgObject(toDate);
        if (t == null || f == null) {
            return 0;
        }
        long fd = f.getTime();
        long td = t.getTime();
        return (td - fd) / 86400000L;
    }


    public static String getBirthDayFromIDCard(String idno) {
        Calendar cd = Calendar.getInstance();
        if (idno.length() == 15) {
            cd.set(1, Integer.valueOf("19" + idno.substring(6, 8)));
            cd.set(2, Integer.valueOf(idno.substring(8, 10)) - 1);
            cd.set(5, Integer.valueOf(idno.substring(10, 12)));
        } else if (idno.length() == 18) {
            cd.set(1, Integer.valueOf(idno.substring(6, 10)));
            cd.set(2, Integer.valueOf(idno.substring(10, 12)) - 1);
            cd.set(5, Integer.valueOf(idno.substring(12, 14)));
        }

        return dateToString(cd.getTime());
    }

    public static Date addHour(Date date, int hours) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(11, hours);
        return cd.getTime();
    }

    public static Date addDay(Date date, int iday) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(5, iday);
        return cd.getTime();
    }

    public static Date addMonth(Date date, int imonth) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(2, imonth);
        return cd.getTime();
    }

    public static Date addYear(Date date, int iyear) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(1, iyear);
        return cd.getTime();
    }

    public static Date chgObject(Object date) {
        if (date != null && date instanceof Date) {
            return (Date) date;
        } else {
            return date != null && date instanceof String ? stringToDate((String) date) : null;
        }
    }

    public static long getAgeByBirthday(String date) {
        Date birthday = stringToDate(date, "yyyy-MM-dd");
        long sec = (new Date()).getTime() - birthday.getTime();
        long age = sec / 86400000L / 365L;
        return age;
    }

    public static Date beforeMonth(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, -n);
        date = calendar.getTime();
        return date;
    }

    public static Date beforeDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, -n);
        date = calendar.getTime();
        return date;
    }

    public static Date afterDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, n);
        date = calendar.getTime();
        return date;
    }

    public static Date beforeHours(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(11, -n);
        date = calendar.getTime();
        return date;
    }

    public static Date afterHours(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(11, n);
        date = calendar.getTime();
        return date;
    }

    public static Date getFirstDayDateOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int last = cal.getActualMinimum(5);
        cal.set(5, last);
        return cal.getTime();
    }

    public static Date getLastDayDateOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int last = cal.getActualMaximum(5);
        cal.set(5, last);
        return cal.getTime();
    }

    public static Date getFirstDayDateOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int last = cal.getActualMinimum(6);
        cal.set(6, last);
        return cal.getTime();
    }

    public static Date getLastDayDateOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int last = cal.getActualMaximum(6);
        cal.set(6, last);
        return cal.getTime();
    }


    /**
     * <功能描述>: 获取13个月内的月份
     *
     * @param
     * @return List<String>
     * @author xuezhuang
     * @date 2021/6/21 2:55 下午
     **/
    public static List<String> getMonthFor13Months() {
        List<String> monthList = new ArrayList<>();
        String currentDate = DateUtil.getCurrentDate();
        Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(DateUtil.stringToDate(currentDate));
        Date oneYear = DateUtil.addYear(firstDayOfMonth, 1);
        Date endDate = DateUtil.addMonth(oneYear, 1);
        while (firstDayOfMonth.compareTo(endDate) <= -1) {
            String date = DateUtil.dateToString(firstDayOfMonth);
            String month = date.substring(0, date.lastIndexOf("-"));
            monthList.add(month);
            firstDayOfMonth = DateUtil.addMonth(firstDayOfMonth, 1);
        }
        return monthList;
    }

    /**
     * 计算两个日期相差年数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int yearDateDiff(Date startDate, Date endDate) {
        Calendar calBegin = Calendar.getInstance();

        Calendar calEnd = Calendar.getInstance();

        calBegin.setTime(startDate);

        calEnd.setTime(endDate);

        return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);

    }

    /**
     * 计算两个日期相差月份
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int mothDateDiff(Date startDate, Date endDate) {
        Calendar calBegin = Calendar.getInstance();

        Calendar calEnd = Calendar.getInstance();

        calBegin.setTime(startDate);

        calEnd.setTime(endDate);

        int month = calEnd.get(Calendar.MONTH) - calBegin.get(Calendar.MONTH);
        int year = (calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR)) * 12;
        return Math.abs(month + year);

    }

    public static List<String> findDates(String dBegin, String dEnd) throws ParseException {
        //日期工具类准备
        DateFormat format = new SimpleDateFormat(YYYYMMDD);

        //设置开始时间
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(format.parse(dBegin));

        //设置结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(format.parse(dEnd));

        //装返回的日期集合容器
        List<String> DateList = new ArrayList<String>();
        //将第一个月添加里面去
        DateList.add(format.format(calBegin.getTime()));
        // 每次循环给calBegin日期加一天，直到calBegin.getTime()时间等于dEnd
        while (format.parse(dEnd).after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            DateList.add(format.format(calBegin.getTime()));
        }

        return DateList;
    }

    /**
     * 获取两个日期之间所有月份，为了更好复用，返回LocalDate，业务方自行转换
     *
     * @param beginDay
     * @param endDay
     * @return
     */
    public static List<LocalDate> findMonth(LocalDate beginDay, LocalDate endDay) {

        LocalDate begin = beginDay.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = endDay.with(TemporalAdjusters.lastDayOfMonth());

        List<LocalDate> result = new ArrayList<>();

        while (begin.compareTo(end) < 0) {
            result.add(begin);
            begin = begin.plusMonths(1);
        }
        return result;

    }

    /**
     * 判断当前日期是否在有效期内
     *
     * @param startDate      yyyy-MM-dd HH:mm:ss
     * @param endDate        yyyy-MM-dd HH:mm:ss
     * @param currenDateTime yyyy-MM-dd HH:mm:ss
     */
    public static boolean checkDateBetween(Date startDate, Date endDate, Date currenDateTime) {
        if (null == endDate && null == startDate) {
            return true;//有效没有值，返回true
        }
        if (null == startDate && null != endDate) {
            return currenDateTime.before(endDate);
        }
        if (null != startDate && null == endDate) {
            return currenDateTime.after(startDate);
        }
        return currenDateTime.before(endDate) && currenDateTime.after(startDate);
    }

    /**
     * 时间戳转换成⽇期格式字符串
     *
     * @param timeStamp 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(long timeStamp, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(timeStamp);

    }

    public static Date compareStatistic() {
        Date nowDay = new Date();
        String today = DateUtil.dateToString(nowDay, DateUtil.YYYY_MM_DD);
        Date date = DateUtil.stringToDate(today + " 08:00:00", DateUtil.YYYY_MM_DD_HH_MM_SS);
        Date yesterday = DateUtil.beforeDay(date, 1);
        return nowDay.compareTo(date) > 0 ? date : yesterday;
    }


    public static Date getStatisticTime() {
        Date nowDay = new Date();
        String today = DateUtil.dateToString(nowDay, DateUtil.YYYY_MM_DD);
        return DateUtil.stringToDate(today + " 08:00:00", DateUtil.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定日期一周的开始时间
     *
     * @param date
     * @return
     */
    public static LocalDate getStartDayOfWeek(LocalDate date) {
        TemporalField fieldIso = WeekFields.of(DayOfWeek.MONDAY, 1).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate = localDate.with(fieldIso, 1);

        return localDate;
    }

    /**
     * 获取指定日期一周的结束时间
     *
     * @param date
     * @return
     */
    public static LocalDate getEndDayOfWeek(LocalDate date) {
        TemporalField fieldIso = WeekFields.of(DayOfWeek.MONDAY, 1).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate = localDate.with(fieldIso, 7);

        return localDate;
    }

    public static LocalDate getStartDayOfMonth(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        LocalDate localDate = LocalDate.of(year, month, 1);

        return localDate;
    }

    public static LocalDate getStartDayOfYear(LocalDate date) {
        int year = date.getYear();

        return LocalDate.of(year, 1, 1);
    }

    public static LocalDate getEndDayOfMonth(LocalDate date) {
        LocalDate localDate = date.with(TemporalAdjusters.lastDayOfMonth());

        return localDate;
    }
}
