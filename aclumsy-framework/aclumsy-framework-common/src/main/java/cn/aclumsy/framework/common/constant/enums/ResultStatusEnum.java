package cn.aclumsy.framework.common.constant.enums;


import cn.aclumsy.framework.common.result.IResultStatus;

/**
 * @author xiamu
 * @date 2022-04-22 14:06
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum ResultStatusEnum implements IResultStatus {

    /**
     * 	操作成功, 成功统一返回前段的 code 为 00000, 不能改
     */
    SUCCESS("00000", "OK"),

    /**
     * 	操作失败, 失败尽量以不同的失败码和失败信息返回给前端. 是错误信息和错误类别显而易见
     */
    FAILURE("99999", "ERROR"),

    /**
     * 外部接口调用异常
     */
    INTERFACE_SYSTEM_ERROR("INTERFACE_SYSTEM_ERROR", "外部接口调用异常"),

    /**
     * 业务连接处理超时
     */
    CONNECT_TIME_OUT("CONNECT_TIME_OUT", "系统超时"),

    /**
     * 参数为空
     */
    NULL_ARGUMENT("NULL_ARGUMENT", "参数为空"),

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "参数不合法"),

    /**
     * 非法请求
     */
    ILLEGAL_REQUEST("ILLEGAL_REQUEST", "非法请求"),

    /**
     * 非法配置
     */
    ILLEGAL_CONFIGURATION("ILLEGAL_CONFIGURATION", "配置不合法"),

    /**
     * 非法状态
     */
    ILLEGAL_STATE("ILLEGAL_STATE", "状态不合法"),

    /**
     * 错误的枚举编码
     */
    ENUM_CODE_ERROR("ENUM_CODE_ERROR", "错误的枚举编码"),

    /**
     * 逻辑错误
     */
    LOGIC_ERROR("LOGIC_ERROR", "逻辑错误"),

    /**
     * 并发异常
     */
    CONCURRENT_ERROR("CONCURRENT_ERROR", "并发异常"),

    /**
     * 非法操作
     */
    ILLEGAL_OPERATION("ILLEGAL_OPERATION", "非法操作"),

    /**
     * 重复操作
     */
    REPETITIVE_OPERATION("REPETITIVE_OPERATION", "重复操作"),

    /**
     * 无操作权限
     */
    NO_OPERATE_PERMISSION("NO_OPERATE_PERMISSION", "无操作权限"),

    /**
     * 资源不存在
     */
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "资源不存在"),

    /**
     * 资源已存在
     */
    RESOURCE_ALREADY_EXIST("RESOURCE_ALREADY_EXIST", "资源已存在"),

    /**
     * 类型不匹配
     */
    TYPE_UN_MATCH("TYPE_UN_MATCH", "类型不匹配"),

    /**
     * FILE_NOT_EXIST
     */
    FILE_NOT_EXIST("FILE_NOT_EXIST", "文件不存在"),

    /**
     * 请求被限流
     */
    LIMIT_BLOCK("LIMIT_BLOCK", "请求限流阻断"),

    /**
     * token 失效
     */
    TOKEN_EXPIRE("TOKEN_EXPIRE", "token过期"),

    /**
     * 业务处理异常
     */
    BUSINESS_ERROR("BUSINESS_ERROR", "业务处理异常"),

    /**
     * token
     */
    TOKEN_FAIL("TOKEN_FAIL", "token_fail"),

    /**
     * request
     */
    REQUEST_EXCEPTION("REQUEST_EXCEPTION", "request_exception"),

    /**
     * 接口限流降级
     */
    BLOCK_EXCEPTION("BLOCK_EXCEPTION", "接口限流降级");

    /**
     * 响应CODE
     */
    private final String code;

    /**
     * 响应message
     */
    private final String message;

    ResultStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {return this.code;}

    @Override
    public String getMessage() {
        return this.message;
    }
}
