package cn.aclumsy.framework.common.validation;

import cn.aclumsy.framework.common.validation.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 手机号判断注解
 * @author Aclumesy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Phone.List.class)
@Documented
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {

    /**
     * 手机号不正确的时候，返回的信息 必须存在
     * @return {@link String}
     */
    String message() default "{javax.validation.constraints.Phone.message}";

    /**
     * 是否必填
     * @return {@link Boolean} 默认为true
     */
    boolean required() default true;

    /**
     * 分组。必须存在
     * @return {@link Class}
     */
    Class<?>[] groups() default {};

    /**
     * 负载 必须存在
     * @return {@link Class}
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link Phone} annotations on the same element.
     * @see Phone
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        Phone[] value();
    }

}
