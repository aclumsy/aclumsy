package cn.aclumsy.framework.common.constant;

/**
 * 定义最基础的一些变量
 * @author Aclumsy
 * @date 2022-08-14 8:55
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class AclumsyConstant {

    /**
     * 正式环境
     */
    public static final String PROD_ENV = "prod";

    /**
     * 开发环境
     */
    public static final String DEV_ENV = "dev";

    /**
     * 测试环境
     */
    public static final String TEST_ENV = "test";

    /**
     * 类路径 只会到你的class路径中查找找文件。
     */
    public static final String CLASSPATH_COLON = "classpath:";

    /**
     * 不仅包含class路径，还包括jar文件中（class路径）进行查找。
     */
    public static final String CLASSPATH_ALL_COLON = "classpath*:";

}
