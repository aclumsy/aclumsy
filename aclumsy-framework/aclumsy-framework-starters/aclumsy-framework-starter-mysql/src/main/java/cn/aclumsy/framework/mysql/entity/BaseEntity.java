package cn.aclumsy.framework.mysql.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 基类
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8002641527438916364L;
    /**
     * 创建人（id）
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人（id）
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
