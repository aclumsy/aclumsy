package cn.aclumsy.framework.web.config;

import cn.aclumsy.framework.common.constant.PatternConstant;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期转换配置，全局统一
 * @author Aclumsy
 * @date 2022-08-14 17:38
 * @since 1.0.0
 */
@Configuration
public class DateTimeConfiguration {

    /**
     * jackson 序列化日期格式化 bean
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    @Primary
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(PatternConstant.DATETIME_PATTERN)))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(PatternConstant.DATE_PATTERN)))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(PatternConstant.TIME_PATTERN)))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(PatternConstant.DATETIME_PATTERN)))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(PatternConstant.DATE_PATTERN)))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(PatternConstant.TIME_PATTERN)));
    }

    /**
     * 日期转换器: 将字符串转换为 LocalDate
     * @author Aclumsy
     * @date 2022-08-14 17:38
     * @since 1.0.0
     */
    @Component
    public static class LocalDateConverter implements Converter<String, LocalDate> {

        /**
         * 将字符串转换为 LocalDate
         * @param source 字符串
         * @return LocalDate
         */
        @Override
        public LocalDate convert(@NonNull String source) {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern(PatternConstant.DATE_PATTERN));
        }
    }

    /**
     * 日期转换: 将字符串转换为 LocalDateTime
     * @author Aclumsy
     * @date 2022-08-14 17:38
     * @since 1.0.0
     */
    @Component
    public static class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

        /**
         * 将字符串转换为 LocalDateTime
         * @param source 字符串
         * @return LocalDateTime
         */
        @Override
        public LocalDateTime convert(@NonNull String source) {
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(PatternConstant.DATETIME_PATTERN));
        }
    }

}
