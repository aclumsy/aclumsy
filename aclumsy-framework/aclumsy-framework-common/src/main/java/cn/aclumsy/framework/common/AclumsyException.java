package cn.aclumsy.framework.common;

import cn.aclumsy.framework.common.result.IResultStatus;
import cn.aclumsy.framework.common.context.MessageSourceUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 项目最顶层异常,
 * @author Aclumsy
 * @date 2022-08-14 17:38
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class AclumsyException extends RuntimeException {

    private static final long serialVersionUID = 100000000000000L;

    private String code;

    private String message;

    public AclumsyException(String message) {
        super(message);
        this.message = message;
    }

    public AclumsyException(IResultStatus resultStatus) {
        super(resultStatus.getMessage());
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public AclumsyException(IResultStatus resultStatus, Throwable cause) {
        super(resultStatus.getMessage(), cause);
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public AclumsyException(String message, Throwable cause) {
        super(message, cause);
    }

    public AclumsyException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public AclumsyException(String code, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    @Contract("_ -> new")
    public static @NotNull AclumsyException exception(String message) {
        return new AclumsyException(message);
    }

    @Contract("_ -> new")
    public static @NotNull AclumsyException exception(@NotNull IResultStatus resultStatus) {
        return new AclumsyException(resultStatus.getCode(), resultStatus.getMessage());
    }

    @Contract("_, _ -> new")
    public static @NotNull AclumsyException exception(String code, String message) {
        return new AclumsyException(code, message);
    }

    @Contract("_, _ -> new")
    public static @NotNull AclumsyException exceptionBundle(String code, Object ...args) {
        return new AclumsyException(code, MessageSourceUtil.getMessage(code, args));
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
