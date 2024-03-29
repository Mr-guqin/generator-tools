package com.koo.generator.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 功能描述 日期工具类
 * <p>
 * <a href="DateUtils.java"><i>View Source</i></a>
 *
 * @author wangcong
 * @version 1.0
 * @since 1.0
 */
public class DateUtils {

    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_FORMAT_YYYY = "yyyy";

    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMMDD_CHINESE = "yyyy年MM月dd日";

    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";

    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    public static final String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_MDHM = "M月d日H时m分";

    public static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";

    public static final String DATE_FORMAT_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_YYYY_MM_DD_HHMM = "yyyy/MM/dd HH:mm";

    public static final String TIME_FORMAT_HH_MM_SS = "HH:mm:ss";

    public static final String TIME_FORMAT_HHMMSS = "HHmmss";

    public static final String LETTER_T = "T";

    public static final Locale LOCALE = Locale.CHINA;

    /**
     * 日期（正则）
     */
    public static final String DATE_REG = "^\\d{4}年\\d{1,2}月\\d{1,2}日$";

    public static final String PERMANENT = "(永久|长期|9999|不约定期限|无固定期限)";

    public static final String FLAG = "至";

    private static final Object lock = new Object();

    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    /**
     * 将TemporalAccessor格式化成指定格式的字符串
     *
     * @param temporal 时间
     * @param pattern  格式
     * @return
     */
    public static String format(TemporalAccessor temporal, String pattern) {
        if (temporal == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(pattern).format(temporal);
    }

    /**
     * 将date格式化成指定格式的字符串
     *
     * @param pattern 格式
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        return format(LocalDateTime.from(date.toInstant()), pattern);
    }

    public static String format(LocalDate date, String pattern) {
        if (Objects.isNull(date)) {
            return null;
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }

        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(LocalDateTime date, String pattern) {
        if (Objects.isNull(date)) {
            return null;
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = DATE_FORMAT_YYYYMMDD_HHMMSS;
        }

        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将dateStr解析成LocalDate类型
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }

        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将dateStr解析成LocalDateTime类型
     *
     * @param dateStr 时间
     * @param pattern 时间格式
     * @return
     */
    public static LocalDateTime parse(String dateStr, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DATE_FORMAT_YYYYMMDD_HHMMSS;
        }
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 将dateStr解析成Date类型,兼容
     *
     * @param dateStr 时间
     * @return
     */
    public static Date parseToDate(String dateStr) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(parse(dateStr, DATE_FORMAT_YYYYMMDD_HHMMSS),
                ZoneId.systemDefault());
        return toDate(zonedDateTime);
    }

    /**
     * 时间相差天数
     *
     * @param smdate
     * @param bdate
     * @return
     */
    public static long daysBetween(Date smdate, Date bdate) {
        final LocalDateTime localDate1 = LocalDateTime.from(smdate.toInstant());
        final LocalDateTime localDate2 = LocalDateTime.from(bdate.toInstant());
        return daysBetween(localDate1, localDate2);
    }

    /**
     * 时间相差天数
     *
     * @param localDate1
     * @param localDate2
     * @return
     */
    public static long daysBetween(LocalDateTime localDate1, LocalDateTime localDate2) {
        return Math.abs(localDate1.until(localDate2, ChronoUnit.DAYS));
    }

    /**
     * 时间相差天数
     *
     * @param localDate1
     * @param localDate2
     * @return
     */
    public static long daysBetween(LocalDate localDate1, LocalDate localDate2) {
        return Math.abs(localDate1.until(localDate2, ChronoUnit.DAYS));
    }

    /**
     * 获取当前时间:java.util.date格式
     *
     * @return
     */
    public static Date now() {
        return toDate(Instant.now());
    }

    /**
     * 获取时间:java.util.date格式
     *
     * @param temporal
     * @return
     */
    private static Date toDate(TemporalAccessor temporal) {
        return Date.from(Instant.from(temporal));
    }

    /**
     * 获取时间:LocalDate格式
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 获取时间:LocalDate格式
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * localDate 转让成date
     *
     * @param date
     * @return
     */
    public static Date LocalDateTime2Date(LocalDateTime date) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * localDate 转让成date
     *
     * @param date
     * @return
     */
    public static Date LocalDate2Date(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse2date(String dateStr, String pattern) {
        if (StringUtils.isNotEmpty(dateStr)) {
            try {
                return getSdf(pattern).parse(dateStr);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (lock) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    // Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                    // SimpleDateFormat
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    public static long getBetweenDay(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime() + 1000000) / (3600 * 24 * 1000);
    }

    public static long getBetweenDay(LocalDate startDate, LocalDate endDate) {
        return (LocalDate2Date(endDate).getTime() - LocalDate2Date(startDate).getTime() + 1000000) / (3600 * 24 * 1000);
    }

    public static Date LocalDate2Date(LocalDate date, String patterm) {
        return parse2date(date.format(DateTimeFormatter.ofPattern(patterm)), patterm);
    }

    public static int getMonthDiff(Date end, Date start) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(end);
        c2.setTime(start);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) {
            return 0;
        }
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16 d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || (month1 == month2 && day1 < day2)) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval + 1;
    }

    /**
     * 判断current是否在other日期前或同一日期
     *
     * @param current
     * @param other
     * @return
     */
    public static boolean isBeforeOrEqual(ChronoLocalDate current, ChronoLocalDate other) {
        return current.isBefore(other) || current.isEqual(other);
    }

    /**
     * 判断current是否在other日期后或同一日期
     *
     * @param current
     * @param other
     * @return
     */
    public static boolean isAfterOrEqual(ChronoLocalDate current, ChronoLocalDate other) {
        return current.isAfter(other) || current.isEqual(other);
    }

    public static LocalDate parse2LocateDate(String strDate, String pattern) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        return LocalDate.parse(strDate, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate max(LocalDate date1, LocalDate date2) {
        if (date1.isAfter(date2)) {
            return date1;
        }
        return date2;
    }

    /**
     * 获取两个localdate之间的所有日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<LocalDate> getBetweenDate(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> list = new ArrayList<>();
        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 0) {
            return list;
        }
        Stream.iterate(startDate, d -> {
            return d.plusDays(1);
        }).limit(distance + 1).forEach(f -> {
            list.add(f);
        });
        return list;
    }

    /**
     * 解析交易日配置
     * T-2->-2
     * T-1->-1
     * T->0,T+0->0,T-0->0
     * T+1->1
     * T+2->2
     *
     * @param config
     * @return
     * @Author huangliwei
     * @Date 2021/6/29 10:19
     **/
    public static Integer parseTradeDateConfig(String config) {
        if (StringUtils.isEmpty(config) || !config.startsWith(LETTER_T)) {
            throw new IllegalArgumentException("参数配置错误 config:" + config);
        }
        if (LETTER_T.equals(config)) {
            return 0;
        }
        return Integer.parseInt(config.replace(LETTER_T, ""));
    }

    public static String LocalDateTimeToString(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(dateTime);
    }

    public static String LocalDateToString(LocalDate date, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return date.format(df);
    }

    public static String LocalTimeToString(LocalTime time, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return time.format(df);
    }

    public static String formatConversion(String dateTime, String pattern) {
        String result = "";
        if (TIME_FORMAT_HH_MM_SS.equals(pattern) && StringUtils.isNotEmpty(dateTime)) {
            result = dateTime.substring(0, 2) + ":" + dateTime.substring(2, 4) + ":" + dateTime.substring(4, 6);
        }
        return result;
    }

    /**
     * 字符串转日期---支持多种不同日期格式
     *
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        String separator = String.valueOf(date.charAt(4));
        String pattern = "yyyyMMdd";
        if (!separator.matches("\\d*")) {
            pattern = "yyyy" + separator + "MM" + separator + "dd";
            if (date.length() < 10) {
                pattern = "yyyy" + separator + "M" + separator + "d";
            }
            pattern += " HH:mm:ss.SSS";
        } else {
            if (date.length() < 8) {
                pattern = "yyyyMd";
            }
            pattern += "HHmmssSSS";
        }

        pattern = pattern.substring(0, Math.min(pattern.length(), date.length()));
        try {
            return getSdf(pattern).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

}
