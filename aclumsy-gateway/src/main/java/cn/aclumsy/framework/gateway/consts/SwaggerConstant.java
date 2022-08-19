package cn.aclumsy.framework.gateway.consts;

/**
 * swagger 常量
 * @author Aclumsy
 * @date 2022-08-19 5:19
 * @since 1.0.0
 */
public class SwaggerConstant {

    /**
     * X-Forwarded-Prefix 在Swagger中会根据X-Forwarded-Prefix这个Header来获取 BasePath
     */
    public static final String HEADER_NAME = "X-Forwarded-Prefix";

    /**
     * 接口文档路径
     */
    public static final String URI = "/v3/api-docs";

    /**
     * 接口文档路径
     */
    public static final String URI_V3 = "v3/api-docs";

    /**
     * Swagger版本
     */
    public static final String SWAGGER_VERSION = "3.0";

    /**
     * Swagger标题
     */
    public static final String SWAGGER_TITLE = "swaggerTitle";
}
