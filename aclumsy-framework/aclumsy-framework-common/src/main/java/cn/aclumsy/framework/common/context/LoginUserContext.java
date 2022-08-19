package cn.aclumsy.framework.common.context;

import cn.aclumsy.framework.common.model.UserLoginInfo;

import java.util.Optional;

/**
 * 登录用户上下文
 * @author Aclumsy
 * @date 2022-08-14 8:55
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class LoginUserContext {

    /**
     * 登录用户信息
     */
    private static final ThreadLocal<UserLoginInfo> LOGIN_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 添加登录用户信息
     * @param user 登录用户信息
     */
    public static void set(UserLoginInfo user) {
        LOGIN_USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取登录用户信息
     * @return 登录用户信息
     */
    public static UserLoginInfo get() {
        return LOGIN_USER_THREAD_LOCAL.get();
    }

    /**
     * 用户是否登录
     * @return boolean
     */
    public static boolean isLogin() {
        return Optional.ofNullable(LOGIN_USER_THREAD_LOCAL.get()).isPresent();
    }

    /**
     * 获取当前登录用户ID
     * @return 当前登录用户id
     */
    public static Long getUserId() {
        return LOGIN_USER_THREAD_LOCAL.get().getId();
    }

    /**
     * 获取当前登录用户名, 如果不存在返回null
     * @return 获取登录用户ID
     */
    public static Long getUserIdOrNull() {
        Optional<UserLoginInfo> optional = Optional.ofNullable(LOGIN_USER_THREAD_LOCAL.get());
        return optional.map(UserLoginInfo::getId).orElse(null);
    }

    /**
     * 获取当前登录用户名, 如果不存在返回null
     * @return 当前登录用户名, 如果不存在返回默认值
     */
    public static Long getUserIdOrDefault() {
        Optional<UserLoginInfo> optional = Optional.ofNullable(LOGIN_USER_THREAD_LOCAL.get());
        return optional.isPresent() ? optional.get().getId() : 0L;
    }

    /**
     * 获取当前系统用户ID
     * @return 获取系统用户ID
     */
    public static long getSysUserId() {
        return 1L;
    }

    /**
     * 删除登录用户信息
     */
    public static void remove() {
        LOGIN_USER_THREAD_LOCAL.remove();
    }
}
