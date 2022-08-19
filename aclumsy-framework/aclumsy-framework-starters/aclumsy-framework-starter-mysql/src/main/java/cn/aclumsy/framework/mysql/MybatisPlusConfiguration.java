package cn.aclumsy.framework.mysql;

import cn.aclumsy.framework.mysql.interceptor.MyBatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 配置类
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Configuration
@MapperScan(basePackages = "cn.aclumsy.**.mapper")
public class MybatisPlusConfiguration {

    /**
     * 加入插件 <br>
     * {@link BlockAttackInnerInterceptor} 提供了防止全表更新与删除插件 <br>
     * {@link OptimisticLockerInnerInterceptor} 提供了乐观锁插件 <br>
     * {@link PaginationInnerInterceptor} 提供了分页插件 <br>
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    /**
     * mybatis-plus 数据插入,更新时候字段填充拦截器, 交给Spring容器管理
     * @return {@link MyBatisPlusInterceptor}
     */
    @Bean
    public MyBatisPlusInterceptor dataOperationInterceptor(){
        return new MyBatisPlusInterceptor();
    }
}
