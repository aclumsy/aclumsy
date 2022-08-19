package cn.aclumsy.framework.common.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

/**
 * 日期工具类
 * @author Aclumsy
 * @date 2022-04-22 14:09
 * @since 1.0.0
 */
@UtilityClass
public class DateUtil {

    /**
     * 日期格式化 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化 yyyy-MM-dd
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 日期格式化 HH:mm:ss
     */
    public static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 日期格式化 yyyy-MM-dd HH:mm
     */
    public static final String DATE_HOURS_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * LocalDate 转 Date
     * @param localDate {@link LocalDate} 日期
     * @return {@link Date} 日期
     */
    public static Date transformDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime 转 Date
     * @param localDateTime {@link LocalDateTime} 日期时间
     * @return {@link Date} 日期
     */
    public static Date transformDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转 LocalDate
     * @param date {@link Date} 日期
     * @return {@link LocalDate} 日期
     */
    public static LocalDate transformLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     * @param date {@link Date} 日期
     * @return {@link LocalDateTime} 日期时间
     */
    public static LocalDateTime transformLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 日期转为 yyyy-MM-dd HH:mm:ss 格式 转 LocalDateTime
     * @param dateTime 日期时间
     * @return {@link LocalDateTime} 日期时间
     */
    public static LocalDateTime transformLocalDateTime(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return null;
        }
        return LocalDateTime.parse(dateTime, ISO_DATE_TIME);
    }

    /**
     * 时间戳转 LocalDateTime
     *
     * @param timestamp 时间戳
     * @return {@link LocalDateTime} 日期时间
     */
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

/**
     * 时间戳转 LocalDate
     *
     * @param timestamp 时间戳
     * @return {@link LocalDate} 日期时间
     */
    public static LocalDate toLocalDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 判断 localDateTime 是否在 start 和 endTime 之间
     * @param localDateTime {@link LocalDateTime} 日期时间
     * @param startTime {@link LocalDateTime} 开始日期时间
     * @param endTime {@link LocalDateTime} 结束日期时间
     * @return boolean 是否在时间范围内
     */
    public static Boolean isBetweenDateTime(LocalDateTime localDateTime, LocalDateTime startTime, LocalDateTime endTime) {
        if (localDateTime != null && startTime != null && endTime != null) {
            return !(localDateTime.isBefore(startTime) || localDateTime.isAfter(endTime));
        }
        return false;
    }

    /**
     * 判断 localTime 是否在 startTime 和 endTime 之间
     * @param localTime {@link LocalDate} 日期
     * @param startTime {@link LocalDate} 开始日期
     * @param endTime {@link LocalDate} 结束日期
     * @return boolean 是否在时间范围内
     */
    public static Boolean isBetweenTime(LocalTime localTime, LocalTime startTime, LocalTime endTime) {
        if (localTime != null && startTime != null && endTime != null) {
            return !(localTime.isBefore(startTime) || localTime.isAfter(endTime));
        }
        return false;
    }

    /**
     * 获取两个时间之间的小时数
     * @param startTime {@link LocalDate} 开始日期
     * @param endTime {@link LocalDate} 结束日期
     * @return boolean 是否在时间范围内
     */
    public static Long betweenHours(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours();
    }

    /**
     * 获取两个时间之间的分钟数
     * @param startTime {@link LocalDate} 开始日期
     * @param endTime {@link LocalDate} 结束日期
     * @return boolean 是否在时间范围内
     */
    public static Long betweenMinutes(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toMinutes();
    }

    /**
     * 时间转毫秒
     * @return long 毫秒数
     */
    public static Long toMill(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 获取今天的开始时间
     * @return {@link LocalDateTime} 日期时间
     */
    public LocalDateTime todayStart() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 获取今天的结束时间
     * @return {@link LocalDateTime} 日期时间
     */
    public LocalDateTime todayEnd() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    /**
     * 获取本周一的时间
     * @return {@link LocalDateTime} 日期时间
     */
    public LocalDate getWeekOfMonday() {
        return LocalDate.now().with(DayOfWeek.MONDAY);
    }

    public LocalDate getLastDay() {
        return LocalDate.now().minusDays(1);
    }

    public LocalDate getLastWeekMonday() {
        TemporalAdjuster LastMonday = TemporalAdjusters.ofDateAdjuster(
                temporal -> {
                    DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                    int value = -(dow.getValue() + 6);
                    return temporal.plus(value, ChronoUnit.DAYS);
                });
        return LocalDate.now().with(LastMonday);
    }

    public LocalDate getLastWeekSunday() {
        return LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
    }

    public LocalDate getLastMonthFirstDay() {
        return LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate getLastMonthLastDay() {
        return LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    }

    public LocalDate getMonthFirstDay() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate getMonthLastDay() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

    public static String format(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化时间戳为小时 类似 2020-02-02 02:00
     *
     * @param timestamp 时间戳
     * @return 格式化后时间
     */
    public static String format2Hours(@Validated long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.MINUTE, 0);
        return DateUtil.format(calendar.getTime(), DATE_HOURS_PATTERN);
    }

    /**
     * 解析起止时间戳中的连续小时
     *
     * @param start 开始
     * @param end 截止
     * @return 连续小时
     */
    public static List<String> parseHoursRange(long start, long end) {
        if (end < start) {
            throw new IllegalArgumentException("end小于start");
        }
        List<String> hoursRange = new LinkedList<>();
        Calendar cStart = Calendar.getInstance();
        cStart.setTimeInMillis(start);
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTimeInMillis(end);
        while (!cEnd.before(cStart)) {
            hoursRange.add(DateUtil.format(cStart.getTime(), DATE_HOURS_PATTERN));
            cStart.add(Calendar.HOUR_OF_DAY, 1);
        }
        return hoursRange;
    }

    /**
     * 格式化时间戳为日期 类似 2020-02-02
     *
     * @param timestamp 时间戳
     * @return 格式化后时间
     */
    public static String format2Days(Long timestamp) {
        Date date = new Date(timestamp);
        return DateUtil.format(date, DATE_PATTERN);
    }

    /**
     * 解析起止时间戳中的连续天数
     *
     * @param start 开始
     * @param end 截止
     * @return 连续天数
     */
    public static List<String> parseDaysRange(Long start, Long end) {
        if (end < start) {
            throw new IllegalArgumentException("end小于start");
        }
        List<String> hoursRange = new LinkedList<>();
        Calendar cStart = Calendar.getInstance();
        cStart.setTimeInMillis(start);
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTimeInMillis(end);
        while (!cEnd.before(cStart)) {
            hoursRange.add(DateUtil.format(cStart.getTime(), DATE_PATTERN));
            cStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        return hoursRange;
    }
}
