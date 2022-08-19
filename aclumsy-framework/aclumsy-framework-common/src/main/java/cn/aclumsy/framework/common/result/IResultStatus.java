package cn.aclumsy.framework.common.result;

/**
 * 基本响应状态抽象接口
 * @author Aclumsy
 * @date 2022-08-14 23:21
 * @since 1.0.0
 */
public interface IResultStatus {

    /**
     * 获取响应信息Code
     * @return 响应信息Code
     */
    String getCode();

    /**
     * 获取响应信息
     * @return 响应信息描述
     */
    String getMessage();
}
