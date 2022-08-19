package cn.aclumsy.framework.common.annotation;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 该注解目的是可以直接注解到类上面, value 为国际化消息文件的名称, <br>
 * 比如 @MessageBaseNames({"i18n/message", "i18n/message2"}) <br>
 * 就可以通过 getMessage() 读取到 i18n/message 和 i18n/message2 两个文件配置的消息
 * @author Aclumsy
 * @date 2022-08-16 16:24
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
@Configuration
public @interface MessageBaseNames {

    /**
     * @return 国际化消息文件名称 比如 i18n/messages
     */
    String[] value();
}
