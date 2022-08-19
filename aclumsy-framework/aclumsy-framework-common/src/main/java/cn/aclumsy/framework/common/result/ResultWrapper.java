package cn.aclumsy.framework.common.result;

import cn.aclumsy.framework.common.constant.enums.ResultStatusEnum;
import cn.aclumsy.framework.common.context.MessageSourceUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * <p>响应结果包装类。提供了常用的响应成功和响应失败的静态方法</p>
 * @author Aclumsy
 * @date 2022-04-22 14:09
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuppressWarnings("unused")
//@ApiModel("响应结果基本包装类")
public class ResultWrapper<R> implements Serializable {

    /**
     * 序列化serialVersionUID
     */
    private static final long serialVersionUID = 302915L;

    /**
     * 响应标志 {@code true} 响应成功，{@code false} 响应失败
     * 默认 {@code false} 响应失败
     */
    //@ApiModelProperty("响应标志")
    private boolean success = false;

    /**
     * 响应码
     * 默认 {@code 99999 } 失败
     */
    //@ApiModelProperty("响应码")
    private String code = ResultStatusEnum.FAILURE.getCode();

    /**
     * 响应消息
     * 默认 {@code ERROR} 失败
     */
    //@ApiModelProperty("响应信息")
    private String message = ResultStatusEnum.FAILURE.getMessage();

    //@ApiModelProperty("链路ID")
    private String traceId;

    /**
     * 响应数据
     * 默认 {@code null}
     */
    //@ApiModelProperty("响应结果")
    private R result;

    /**
     * 响应时间戳, 默认为当前时间
     */
    private Long timestamp = System.currentTimeMillis();

    /**
     * 无参构造
     * 响应包装类                  {@link ResultWrapper<R>}
     */
    public ResultWrapper() {
        this.setTraceId();
    }


    // 请求成功通用方法---------------------------------------------------------------------------------
    /**
     * 请求成功 <br>
     * 默认请求结果为 {@code null},
     * @param <R>                请求结果 {@link R}
     * @return                   响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> success() {
        return success(null, ResultStatusEnum.SUCCESS.getMessage());
    }

    /**
     * 请求成功
     * @param result            请求结果 {@link R}
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> success(R result) {
        return success(result, ResultStatusEnum.SUCCESS.getMessage());
    }

    /**
     * 请求成功 <br>
     * {@code 成功响应信息}       {@link IResultStatus} 的 messgae 信息
     * @param resultStatus      响应枚举 {@link IResultStatus}
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> success(@NotNull IResultStatus resultStatus) {
        return success(null, resultStatus.getMessage());
    }

    /**
     * 请求成功 <br>
     * {@code 成功响应信息}       {@link IResultStatus} 的 messgae 信息
     * @param result            请求结果 {@link R}
     * @param resultStatus      响应枚举 {@link IResultStatus}
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> success(R result, @NotNull IResultStatus resultStatus) {
        return success(result, resultStatus.getMessage());
    }

    /**
     * 请求成功
     * @param result            请求结果 {@link R}
     * @param message           成功描述信息 {@link String}
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> success(R result, String message) {
        return new ResultWrapper<R>()
            .setSuccess(true)
            .setResult(result)
            .setCode(ResultStatusEnum.SUCCESS.getCode())
            .setMessage(message);
    }

    /**
     * 请求成功 <br>
     * 返回自定义响应信息
     * @param message           成功描述信息 {@link String}
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> successMessage(String message) {
        return success(null, message);
    }

    // 请求失败通用方法---------------------------------------------------------------------------------
    /**
     * 请求失败 <br>
     * 默认失败信息
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> failure() {
        return failure(ResultStatusEnum.FAILURE);
    }

    /**
     * 请求失败 <br>
     * 并且返回自定义 {@code message}
     * @param message           失败描述信息 {@link String}
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> failure(String message) {
        return failure(ResultStatusEnum.FAILURE.getCode(), message);
    }

    /**
     * 请求失败 <br>
     *
     * @param resultStatus      响应枚举 {@link IResultStatus}
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> failure(@NotNull IResultStatus resultStatus) {
        return failure(resultStatus.getCode(), resultStatus.getMessage());
    }

    /**
     * 请求失败 <br>
     * 并且返回自定义 {@code code} 和 {@code message}
     * @param code              失败响应码  {@link String}
     * @param message           失败描述信息 {@link String}
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> failure(@NotNull String code, @NotNull String message) {
        return new ResultWrapper<R>()
                .setSuccess(false)
                .setResult(null)
                .setCode(code)
                .setMessage(message);
    }

    /**
     * 请求失败 <br>
     * 并且返回自定义 {@code code} 从国际化配置文件中读取错误信息
     * @param code              失败响应码  {@link String}
     * @param args              失败描述信息 {@link Object}
     * @param <R>               请求结果 {@link R}
     * @return                  响应包装类 {@link ResultWrapper<R>}
     */
    public static<R> ResultWrapper<R> failureBundle(@NotNull String code, Object ...args) {
        return failure(code, MessageSourceUtil.getMessage(code, args));
    }

    @Trace
    public void setTraceId() {
        this.traceId = TraceContext.traceId();
    }

}

