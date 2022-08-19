package cn.aclumsy.framework.mysql.interceptor;

import cn.aclumsy.framework.common.context.LoginUserContext;
import cn.aclumsy.framework.common.model.UserLoginInfo;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * mybatis-plus 数据插入,更新时候字段填充拦截器
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
public class MyBatisPlusInterceptor implements MetaObjectHandler {

    /**
     * 创建时间
     */
    private static final String CREATE_TIME = "createTime";

    /**
     * 创建人
     */
    private static final String CREATE_USER = "createUser";

    /**
     * 更新时间
     */
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 更新人
     */
    private static final String UPDATE_USER = "updateUser";

    /**
     * 是否删除标志
     */
    private static final String IS_DELETED = "isDeleted";

    /**
     * 获取当前登录用户ID
     * @return 用户ID
     */
    private Long getCurrentUserId() {
        return Optional.ofNullable(LoginUserContext.get()).map(UserLoginInfo::getId).orElse(0L);
    }

    /**
     * 插入数据时候, 字段填充 新增时间, 更新时间, 新增人, 更新人, 是否删除
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, CREATE_USER, this::getCurrentUserId, Long.class);
        this.strictInsertFill(metaObject, IS_DELETED, () -> Boolean.FALSE, Boolean.class);
    }

    /**
     * 更新数据时候, 字段填充 更新时间, 更新人
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, UPDATE_USER, this::getCurrentUserId, Long.class);
    }

}
