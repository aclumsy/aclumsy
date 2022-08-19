package cn.aclumsy.framework.common.asserts;

import cn.aclumsy.framework.common.AclumsyException;
import cn.aclumsy.framework.common.result.IResultStatus;
import cn.aclumsy.framework.common.constant.enums.ResultStatusEnum;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Aclumsy
 * @date 2022-08-15 23:08
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class AclumsyAssert {

    /**
     * 断言为 true 的时候抛出异常
     * @param expression 判断条件
     * @param message   异常信息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw AclumsyException.exception(ResultStatusEnum.BUSINESS_ERROR.getCode(), message);
        }
    }

    /**
     * 断言为 true 的时候抛出异常, 并且从 IResultStatus 中获取异常信息
     * @param expression 判断条件
     * @param resultStatus 异常信息
     */
    public static void isTrue(boolean expression, IResultStatus resultStatus) {
        if (!expression) {
            throw AclumsyException.exception(resultStatus);
        }
    }

    /**
     * 断言为 true 的时候抛出异常, 并且从国际化资源文件中获取异常信息
     * @param expression 判断条件
     * @param  code 异常code
     * @param args 变量参数
     */
    public static void isTrueBundle(boolean expression, String code, Object... args) {
        if (!expression) {
            throw AclumsyException.exceptionBundle(code, args);
        }
    }

    /**
     * 断言为false 的时候抛出异常
     * @param expression 判断条件
     * @param message  异常信息
     */
    public static void isNotTrue(boolean expression, String message) {
        if (expression) {
            throw AclumsyException.exception(ResultStatusEnum.BUSINESS_ERROR.getCode(), message);
        }
    }

    /**
     * 断言为 false 的时候抛出异常, 并且从 IResultStatus 中获取异常信息
     * @param expression 判断条件
     * @param resultStatus 异常信息
     */
    public static void isNotTrue(boolean expression, IResultStatus resultStatus) {
        if (expression) {
            throw AclumsyException.exception(resultStatus);
        }
    }

    /**
     * 断言为 false 的时候抛出异常, 并且从国际化资源文件中获取异常信息
     * @param expression 判断条件
     * @param  code 异常code
     * @param args 变量参数
     */
    public static void isNotTrueBundle(boolean expression, String code, Object... args) {
        if (expression) {
            throw AclumsyException.exceptionBundle(code, args);
        }
    }

    /**
     * 断言为 null 的时候抛出异常
     * @param object 判断对象
     * @param message 异常信息
     */
    public static void isNull(Object object, String message) {
        if (!Objects.isNull(object)) {
            throw AclumsyException.exception(ResultStatusEnum.BUSINESS_ERROR.getCode(), message);
        }
    }

    /**
     * 断言为 null 的时候抛出异常, 并且从 IResultStatus 中获取异常信息
     * @param object 判断对象
     * @param resultStatus 异常信息
     */
    public static void isNull(Object object, IResultStatus resultStatus) {
        if (!Objects.isNull(object)) {
            throw AclumsyException.exception(resultStatus);
        }
    }

    /**
     * 断言为 null 的时候抛出异常, 并且从国际化资源文件中获取异常信息
     * @param object 判断对象
     * @param  code 异常code
     * @param args 变量参数
     */
    public static void isNullBundle(Object object, String code, Object... args) {
        if (!Objects.isNull(object)) {
            throw AclumsyException.exceptionBundle(code, args);
        }
    }

    /**
     * 断言为非 null 的时候抛出异常
     * @param object 判断对象
     * @param message 异常信息
     */
    public static void isNotNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw AclumsyException.exception(ResultStatusEnum.BUSINESS_ERROR.getCode(), message);
        }
    }

    /**
     * 断言为非 null 的时候抛出异常, 并且从 IResultStatus 中获取异常信息
     * @param object 判断对象
     * @param resultStatus 异常信息
     */
    public static void isNotNull(Object object, IResultStatus resultStatus) {
        if (Objects.isNull(object)) {
            throw AclumsyException.exception(resultStatus);
        }
    }

    /**
     * 断言为非 null 的时候抛出异常, 并且从国际化资源文件中获取异常信息
     * @param object 判断对象
     * @param  code 异常code
     * @param args 变量参数
     */
    public static void isNotNullBundle(Object object, String code, Object... args) {
        if (Objects.isNull(object)) {
            throw AclumsyException.exceptionBundle(code, args);
        }
    }

    /**
     * 断言为 empty 的时候抛出异常
     * @param collection 判断集合
     * @param message 异常信息
     */
    public static void isEmpty(Collection<?> collection, String message) {
        if (!Objects.isNull(collection) && !collection.isEmpty()) {
            throw AclumsyException.exception(ResultStatusEnum.BUSINESS_ERROR.getCode(), message);
        }
    }

    /**
     * 断言为 empty 的时候抛出异常 ,并且从 IResultStatus 中获取异常信息
     * @param collection 判断集合
     * @param resultStatus 异常信息
     */
    public static void isEmpty(Collection<?> collection, IResultStatus resultStatus) {
        if (!Objects.isNull(collection) && !collection.isEmpty()) {
            throw AclumsyException.exception(resultStatus);
        }
    }

    /**
     * 断言为 empty 的时候抛出异常, 并且从国际化资源文件中获取异常信息
     * @param collection 判断集合
     * @param  code 异常code
     * @param args 变量参数
     */
    public static void isEmptyBundle(Collection<?> collection, String code, Object... args) {
        if (!Objects.isNull(collection) && !collection.isEmpty()) {
            throw AclumsyException.exceptionBundle(code, args);
        }
    }

    /**
     * 断言为非 empty 的时候抛出异常
     * @param collection 判断集合
     * @param message 异常信息
     */
    public static void isNotEmpty(Collection<?> collection, String message) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw AclumsyException.exception(ResultStatusEnum.BUSINESS_ERROR.getCode(), message);
        }
    }

    /**
     * 断言为非 empty 的时候抛出异常,并且从 IResultStatus 中获取异常信息
     * @param collection 判断集合
     * @param resultStatus 异常信息
     */
    public static void isNotEmpty(Collection<?> collection, IResultStatus resultStatus) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw AclumsyException.exception(resultStatus);
        }
    }

    /**
     * 断言为非 empty 的时候抛出异常, 并且从国际化资源文件中获取异常信息
     * @param collection 判断集合
     * @param  code 异常code
     * @param args 变量参数
     */
    public static void isNotEmptyBundle(Collection<?> collection, String code, Object... args) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw AclumsyException.exceptionBundle(code, args);
        }
    }

}
