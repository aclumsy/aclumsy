package cn.aclumsy.framework.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * 自定义国际化消息组件
 * @author Aclumsy
 * @date 2022-08-16 15:50
 * @since 1.0.0
 */
@Configuration
public class MessageSourceConfiguration {

    /**
     * 默认的国际化消息文件名称
     */
    private static final String DEFAULT_BASENAME = "i18n/messages, i18n/CommonExceptionMessages, i18n/HttpStatusMessages";

    /**
     * 将国际化 bean 注入到 Spring 容器中
     * @return {@link MessageSource} 国际化消息源
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 国际化消息文件名称
        messageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(DEFAULT_BASENAME)));
        // 国际化消息文件编码
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // 默认值是 false，这样当 Spring 在 ResourceBundle 中找不到 code 对应的值的话，就抛出NoSuchMessageException，
        // 把它设置 true，则找不到不会抛出异常，而是使用 code 值作为返回值
        messageSource.setUseCodeAsDefaultMessage(true);
        // 默认值是 true 当找不到语言的资源文件时, 则会找当前系统的语言对应的资源文件, 若当前的系统语言应该是中文，即zh_CN，所以找的是messages_zh_CN.properties
        // 把他设置 false  则会加载系统默认的文件, 而是找messages.properties, 则不会找当前系统的语言对应的资源文件，
        messageSource.setFallbackToSystemLocale(true);
        // 默认值是 false 是否始终应用 MessageFormat 规则，甚至解析没有参数的消息。
        messageSource.setAlwaysUseMessageFormat(false);
        return messageSource;
    }

}
