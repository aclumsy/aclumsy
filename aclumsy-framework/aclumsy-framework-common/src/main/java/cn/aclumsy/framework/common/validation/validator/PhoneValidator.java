package cn.aclumsy.framework.common.validation.validator;

import cn.aclumsy.framework.common.util.ValidationUtil;
import cn.aclumsy.framework.common.validation.Phone;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 判断是否为手机号的注解的实现
 * @author Aclumsy
 * @date 2022-04-22 14:09
 * @since 1.0.0
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    /**
     * 是否必填
     */
    private final AtomicBoolean required = new AtomicBoolean();

    /**
     * 初始化过程
     * @param annotation {@link Phone}注解
     */
    @Override
    public void initialize(Phone annotation) {
        this.required.set(annotation.required());
    }

    /**
     * 判断校验是否成功
     * @param value 注解的值
     * @param context 上下文
     * @return {@link Boolean}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 手机号为空切为非必填的时候。直接跳过校验
        if (StringUtils.isEmpty(value) && !required.get()) {
            return true;
        }
        // 校验手机
        return ValidationUtil.isPhone(value);
    }


}
