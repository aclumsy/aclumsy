package cn.aclumsy.framework.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户登录基本信息
 * @author Aclumsy
 * @date 2022-04-22 14:09
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class UserLoginInfo implements Serializable {

    private static final long serialVersionUID = -8490997981437940713L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 员工
     */
    private Long employeeId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     *  系统ID
     */
    private Integer systemId;

    /**
     *  客户端
     */
    private Integer clientId;

    /**
     * unionid
     */
    private String unionId;

    /**
     * token 过期时间
     */
    private LocalDateTime tokenExpireTime;

    /**
     * sessionId
     */
    private String sessionId;

    /**
     * token
     */
    private String token;
}
