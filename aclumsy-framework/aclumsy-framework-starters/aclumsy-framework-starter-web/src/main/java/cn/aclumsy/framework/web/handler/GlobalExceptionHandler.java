package cn.aclumsy.framework.web.handler;

import cn.aclumsy.framework.common.AclumsyException;
import cn.aclumsy.framework.common.constant.MagicalConstant;
import cn.aclumsy.framework.common.constant.enums.ResultStatusEnum;
import cn.aclumsy.framework.common.context.MessageSourceUtil;
import cn.aclumsy.framework.common.result.ResultWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常统一处理
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    /**
     * 自定义异常
     * @param exception 自定义异常 {@link AclumsyException}
     * @param request   Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ExceptionHandler(AclumsyException.class)
    public ResultWrapper<Void> handleSystemException(@NotNull AclumsyException exception, @NotNull HttpServletRequest request) {
        return loggerAndReturn(exception.getCode(), exception.getMessage(),request, exception);
    }

    /**
     * 处理 SpringMVC 请求地址不存在 <br>
     *
     * 注意，它需要设置如下两个配置项：<br>
     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true <br>
     * 2. spring.mvc.static-path-pattern 为 /statics/**
     * @param exception 异常 {@link NoHandlerFoundException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultWrapper<?> handleNoHandlerFoundExceptionHandler(@NotNull NoHandlerFoundException exception,
                                                                 @NotNull HttpServletRequest request) {

        return loggerAndReturnBundle("HS404", request, exception, request.getRequestURI());
    }

    /**
     * 请求方法不支持异常
     * <br>
     * 常见的 Request method 'xxx(GET, POST, PUT, DELETE等)'  not supported 就会抛出这个异常
     * @param exception 异常 {@link HttpRequestMethodNotSupportedException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultWrapper<Void> handleHttpRequestMethodNotSupportedException(@NotNull HttpRequestMethodNotSupportedException exception,
                                                                            @NotNull HttpServletRequest request) {
        return loggerAndReturnBundle("HS405", request, exception, exception.getMethod(), exception.getSupportedMethods());
    }

    /**
     * 请求参数类型不匹配异常
     * @param exception 异常 {@link HttpMediaTypeNotSupportedException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResultWrapper<Void> handleHttpMediaTypeNotSupportedException(@NotNull HttpMediaTypeNotSupportedException exception,
                                                                        @NotNull HttpServletRequest request) {
        return loggerAndReturnBundle("HS415A", request,exception, exception.getContentType(), exception.getSupportedMediaTypes());
    }

    /**
     * 请求参数类型不匹配异常
     * @param exception 异常 {@link HttpMediaTypeNotSupportedException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResultWrapper<Void> handleHttpMediaTypeNotAcceptableException(@NotNull HttpMediaTypeNotAcceptableException exception,
                                                                         @NotNull HttpServletRequest request) {
        return loggerAndReturnBundle("HS415B", request, exception, exception.getSupportedMediaTypes());
    }

    /**
     * 处理 SpringMVC 请求参数缺失 <br>
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     * @param exception 异常 {@link MissingServletRequestParameterException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultWrapper<?> handleMissingServletRequestParameterException(@NotNull MissingServletRequestParameterException exception,
                                                                                 @NotNull HttpServletRequest request) {
        return loggerAndReturn("HS400", exception.getMessage(), request, exception);
    }

    /**
     * 处理 SpringMVC 请求参数类型错误 <br>
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     * @param exception 异常 {@link MethodArgumentTypeMismatchException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultWrapper<?> handleMethodArgumentTypeMismatchException(@NotNull MethodArgumentTypeMismatchException exception,
                                                                             @NotNull HttpServletRequest request) {
        return loggerAndReturn("HS400", exception.getMessage(), request, exception);
    }

    /**
     * 不合法的参数异常,如类型映射错误 <br>
     * @param exception 异常 {@link IllegalArgumentException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultWrapper<?> handleIllegalArgumentException(@NotNull IllegalArgumentException exception,
                                                           @NotNull HttpServletRequest request) {
        return loggerAndReturn("HS400", exception.getMessage(), request, exception);
    }

    /**
     * 请求参数不可读异常, 比如传入的数据为 JSON, 但是 JSON 格式出现错误, 就会出现这个异常<br>
     * @param exception 异常 {@link HttpMessageNotReadableException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultWrapper<?> handleHttpMessageNotReadableException(@NotNull HttpMessageNotReadableException exception,
                                                                  @NotNull HttpServletRequest request) {
        return loggerAndReturn("HS400", exception.getMessage(), request, exception);
    }

    /**
     * 参数校验异常。<br>
     * @param exception 异常 {@link ValidationException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResultWrapper<Void> handleValidationException(@NotNull ValidationException exception,
                                                         @NotNull HttpServletRequest request) {
        return loggerAndReturn("HS400", exception.getMessage(), request, exception);
    }

    /**
     * 参数绑定异常 @Validated @Valid <br>
     * 一般当前端格式为表单格式 ({@code Content-Type: application/x-www-form-urlencoded})时，<br>
     * 且不加 {@link RequestBody} 注解的时候进行参数校验失败会抛出该异常,<br> <br>
     * {@link RequestBody} 一般用来处理非表单提交 Content-Type: application/x-www-form-urlencoded 编码格式的数据。
     * <br>比如：application/json、application/xml 等
     * @param exception 异常 {@link MethodArgumentNotValidException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResultWrapper<Void> handleBindException(@NotNull BindException exception,
                                                   @NotNull HttpServletRequest request) {
        return loggerAndReturn("HS400", transform(exception.getAllErrors()), request, exception);
    }

    /**
     * 参数校验异常 @Validated @Valid <br>
     * 一般当前端格式为 application/json 时<br>
     * 且加 {@link RequestBody} 注解的时候进行参数校验失败会抛出该异常,<br> <br>
     * {@link RequestBody} 一般用来处理非表单提交 Content-Type: application/x-www-form-urlencoded 编码格式的数据。
     * <br>比如：application/json、application/xml 等
     * @param exception 异常 {@link MethodArgumentNotValidException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultWrapper<Void> handleMethodArgumentNotValidException(@NotNull BindException exception,
                                                                     @NotNull HttpServletRequest request) {
        return loggerAndReturn("HS400", transform(exception.getAllErrors()), request, exception);
    }

    /**
     * jsr 规范中的验证异常，嵌套检验问题
     * @param exception 异常 {@link ConstraintViolationException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultWrapper<Void> handleConstraintViolationException(@NotNull ConstraintViolationException exception,
                                                                  @NotNull HttpServletRequest request) {
        List<ConstraintViolation<?>> violationList = Lists.newArrayList(exception.getConstraintViolations());
        List<String> messages = violationList.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        return loggerAndReturn("HS400", String.join(MagicalConstant.COMMA, messages.toArray(new String[]{})), request, exception);
    }

    /**
     * 数据库主键冲突异常
     * @param exception 异常 {@link DuplicateKeyException}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResultWrapper<Void> handleDuplicateKeyException(@NotNull DuplicateKeyException exception,
                                                           @NotNull HttpServletRequest request) {
        return loggerAndReturn("HS400", exception.getMessage(), request, exception);
    }

    /**
     * 其他异常
     * @param exception 异常 {@link Exception}
     * @param request Http请求 {@link HttpServletRequest}
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    @ExceptionHandler(Exception.class)
    public ResultWrapper<Void> defaultExceptionHandler(@NotNull Exception exception,
                                                       @NotNull HttpServletRequest request) {
        loggerError(ResultStatusEnum.FAILURE.getCode(), exception.getMessage(), request, exception);
        return ResultWrapper.failure(ResultStatusEnum.FAILURE.getCode(), exception.getMessage());
    }

    /**
     * 处理所有异常，主要是提供给 Filter 使用
     * 因为 Filter 不走 SpringMVC 的流程，但是我们又需要兜底处理异常，所以这里提供一个全量的异常处理过程，保持逻辑统一。
     * @param throwable 异常
     * @param request Http请求
     * @return 统一响应包装类 {@link ResultWrapper}
     */
    public ResultWrapper<Void> handle(@NotNull Throwable throwable, HttpServletRequest request) {

        if (throwable instanceof AclumsyException) {
            this.handleSystemException((AclumsyException)throwable, request);
        } else if (throwable instanceof NoHandlerFoundException) {
            this.handleNoHandlerFoundExceptionHandler((NoHandlerFoundException)throwable, request);
        } else if (throwable instanceof HttpRequestMethodNotSupportedException) {
            this.handleHttpRequestMethodNotSupportedException((HttpRequestMethodNotSupportedException)throwable, request);
        } else if (throwable instanceof HttpMediaTypeNotSupportedException) {
            this.handleHttpMediaTypeNotSupportedException((HttpMediaTypeNotSupportedException)throwable, request);
        } else if (throwable instanceof HttpMediaTypeNotAcceptableException) {
            this.handleHttpMediaTypeNotAcceptableException((HttpMediaTypeNotAcceptableException)throwable, request);
        } else if (throwable instanceof MissingServletRequestParameterException) {
            this.handleMissingServletRequestParameterException((MissingServletRequestParameterException)throwable, request);
        } else if (throwable instanceof MethodArgumentTypeMismatchException) {
            this.handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException)throwable, request);
        } else if (throwable instanceof IllegalArgumentException) {
            this.handleIllegalArgumentException((IllegalArgumentException)throwable, request);
        } else if (throwable instanceof HttpMessageNotReadableException) {
            this.handleHttpMessageNotReadableException((HttpMessageNotReadableException)throwable, request);
        } else if (throwable instanceof ValidationException) {
            this.handleValidationException((ValidationException)throwable, request);
        } else if (throwable instanceof MethodArgumentNotValidException) {
            this.handleMethodArgumentNotValidException((MethodArgumentNotValidException)throwable, request);
        } else if (throwable instanceof BindException) {
            this.handleBindException((BindException)throwable, request);
        } else if (throwable instanceof ConstraintViolationException) {
            this.handleConstraintViolationException((ConstraintViolationException)throwable, request);
        } else if (throwable instanceof DuplicateKeyException) {
            this.handleDuplicateKeyException((DuplicateKeyException)throwable, request);
        }
        return this.defaultExceptionHandler((Exception)throwable, request);

    }


    /**
     * 打印日志并且返回统一响应包装类 {@link ResultWrapper}
     * @param code         错误码 {@link String}
     * @param message      错误信息 {@link String}
     * @param request      Http请求 {@link HttpServletRequest}
     * @param exception    异常 {@link Exception}
     * @return             统一响应包装类 {@link ResultWrapper}
     */
    private static ResultWrapper<Void> loggerAndReturn(String code, String message, HttpServletRequest request, Exception exception) {
        loggerError(code, message, request, exception);
        return ResultWrapper.failure(code, message);
    }

    /**
     * 打印日志并且返回统一响应包装类 {@link ResultWrapper} <br>
     * 响应信息从国际化配置文件中获取
     * @param code         错误码 {@link String}
     * @param request      Http请求 {@link HttpServletRequest}
     * @param exception    异常 {@link Exception}
     * @param args         异常 {@link Exception}
     * @return             统一响应包装类 {@link ResultWrapper}
     */
    private static ResultWrapper<Void> loggerAndReturnBundle(String code, HttpServletRequest request, Exception exception, Object ...args) {
        String message = MessageSourceUtil.getMessage(code, args);
        loggerError(code, message, request, exception);
        return ResultWrapper.failure(code, message);
    }

    /**
     * 统一异常处理日志打印
     * @param code      错误码 {@link  String}
     * @param message   错误信息 {@link String}
     * @param request   HTTP请求 {@link HttpServletRequest}
     * @param exception 异常 {@link Exception}
     */
    private static void loggerError(String code, String message, @NotNull HttpServletRequest request, Exception exception) {
        logger.error("[Unified exception handling] Code: {}, RequestURI: {}, RequestMethod: {}, ContentType: {}, Message: {}",
                code,
                request.getRequestURI(),
                request.getMethod(),
                request.getContentType(),
                message,
                exception);
    }

    /**
     * 组装错误信息
     * @param objectErrorList 错误信息集合
     * @return 错误信息
     */
    private static @NotNull String transform(List<ObjectError> objectErrorList) {
        if (CollectionUtils.isEmpty(objectErrorList)) {
            return "";
        }
        StringBuilder objectStringBuilder = new StringBuilder();
        StringBuilder fieldStringBuilder = new StringBuilder();
        for (int i = 0; i < objectErrorList.size(); i++) {
            ObjectError objectError = objectErrorList.get(i);
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                fieldStringBuilder.append("【").append(fieldError.getField()).append("】：").append(fieldError.getDefaultMessage());
                if (i != objectErrorList.size() - 1) {
                    fieldStringBuilder.append("，");
                }
            } else {
                objectStringBuilder.append(objectError.getDefaultMessage());
                if (i != objectErrorList.size() - 1) {
                    objectStringBuilder.append("，");
                }
            }
        }
        if (objectStringBuilder.length() > 0) {
            return fieldStringBuilder.append("，").append(objectStringBuilder).toString();
        } else {
            return fieldStringBuilder.toString();
        }
    }
}
