package cn.aclumsy.framework.common.util;


import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * 校验工具类
 * @author Aclumsy
 * @date 2022-04-22 14:09
 * @since 1.0.0
 */
@SuppressWarnings("all")
@UtilityClass
public class ValidationUtil {

    /**
     * 判断手机号格式是否正确
     * @param phone 手机号
     * @return {@link Boolean} true - 是手机号, false - 不是手机号
     */
    public static boolean isPhone(String phone) {
        return StringUtils.length(phone) == 11;
    }

}
