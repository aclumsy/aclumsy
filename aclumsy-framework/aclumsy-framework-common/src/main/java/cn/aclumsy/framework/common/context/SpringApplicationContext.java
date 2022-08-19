package cn.aclumsy.framework.common.context;

import cn.aclumsy.framework.common.annotation.MessageBaseNames;
import cn.aclumsy.framework.common.constant.AclumsyConstant;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.util.Map;

/**
 * Spring上下文工具
 *
 * @author Aclumsy
 * @date 2022-04-22 14:09
 * @since 1.0.0
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class SpringApplicationContext implements ApplicationContextAware, EnvironmentAware, EmbeddedValueResolverAware {

    /**
     * Spring 上下文
     */
    private static ApplicationContext context;

    /**
     * Spring 环境变量
     */
    private static Environment environment;

    /**
     * Spring 属性值解析器
     */
    private static StringValueResolver stringValueResolver;

    /**
     * 获取 Spring 应用上下文
     * @return Spring应用上下文
     */
    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * 获取 Spring 容器中的 bean
     * @param clazz bean类型
     * @return bean实例
     * @param <T> bean类型
     */
    public static <T> @NotNull T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 获取 Spring 容器中的 bean
     * @param clazz bean名称
     * @return bean实例
     */
    public static <T> @NotNull Map<String, T> getBeanMap(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }

    /**
     * 获取 Spring 容器中的 bean
     * @param clazz bean名称
     * @param String bean名称
     * @param <T> bean类型
     * @return bean实例
     */
    public static <T> @NotNull T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }

    /**
     * 从 Spring 环境变量中获取值
     * @param key 属性名称
     */
    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    /**
     * 获取 spring value，和 {@code @Value }的效果相同 <br>
     * 注意：如果是获取配置，找不到配置会报异常
     *
     * @param spEL spring表达式，须用"${spEL}"括起来
     * @return 表达式的值
     */
    public static String getSpringValue(String spEl) {
        return stringValueResolver.resolveStringValue(spEl);
    }

    /**
     * 获取当前 profile
     * @return profile
     */
    public static String getProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

    /**
     * 判断当前环境是否是生产环境
     * @return true：是生产环境；false：不是生产环境
     */
    public static boolean isProdEnv() {
        return AclumsyConstant.PROD_ENV.equals(getProfile());
    }

    /**
     * 判断当前环境是否是测试环境
     * @return true：是测试环境；false：不是测试环境
     */
    public static boolean isTestEnv() {
        return AclumsyConstant.TEST_ENV.equals(getProfile());
    }

    /**
     * 判断当前环境是否是开发环境
     * @return true：是开发环境；false：不是开发环境
     */
    public static boolean isDevEnv() {
        return AclumsyConstant.DEV_ENV.equals(getProfile());
    }


    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        // 国际化消息 bean, 将 MessageBaseNames 注解的值作为 添加到 ResourceBundleMessageSource 的 basename
        ResourceBundleMessageSource messageSource = applicationContext.getBean(ResourceBundleMessageSource.class);
        applicationContext.getBeansWithAnnotation(MessageBaseNames.class).forEach((key, value) -> {
            MessageBaseNames annotation = AnnotationUtils.findAnnotation(value.getClass(), MessageBaseNames.class);
            if (annotation != null && annotation.value().length > 0) {
                messageSource.addBasenames(annotation.value());
            }
        });
        logger.info("message baseNames : {}", messageSource.getBasenameSet());
        // 上下文赋值
        SpringApplicationContext.context = applicationContext;
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        SpringApplicationContext.environment = environment;
    }

    @Override
    public void setEmbeddedValueResolver(@NotNull StringValueResolver resolver) {
        SpringApplicationContext.stringValueResolver = resolver;
    }
}
