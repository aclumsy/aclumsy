/**
 * Copyright (c), Aclumsy. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.aclumsy.framework.swagger.config;

import cn.aclumsy.framework.swagger.plugin.EnumPropertyBuilderPlugin;
import cn.aclumsy.framework.swagger.plugin.ValidatePropertyBuilderPlugin;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger 配置<br>
 * 基于 SpringFox 配置
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Configuration
@EnableOpenApi
@ConditionalOnClass({Docket.class, ApiInfoBuilder.class})
@ConditionalOnProperty(prefix = "aclumsy.swagger", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 构建 Swagger Docket 对象, 并交给 Spring 容器管理
     * @param properties Swagger 配置属性
     * @return Docket 对象实例
     */
    @Bean
    public Docket createRestApi(@NotNull SwaggerProperties properties) {

        return new Docket(DocumentationType.OAS_30)
            // 是否开启 Swagger
            .enable(properties.isEnabled())
            // 接口文档相关信息
            .apiInfo(apiInfo(properties))
            .select()
            // 注解@Api注解的类
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
            // 这个是筛选接口地址中是以什么开头的, 这里未做筛选
            .paths(PathSelectors.any())
            // 返回一个 Docket 实例
            .build();
    }

    /**
     * 构建 API 接口文档信息
     * @param properties Swagger 配置属性
     * @return ApiInfo 接口文档信息实例
     */
    private ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
            // API文档标题, 配置文件未声明, 则使用应用名称作为标题
            .title(StringUtils.isBlank(properties.getTitle()) ? applicationName : properties.getTitle())
            // API文档描述, 配置文件未声明, 则使用应用名称作为描述
            .description(StringUtils.isBlank(properties.getDescription()) ? applicationName : properties.getDescription())
            // 服务条款地址
            .termsOfServiceUrl(properties.getTermsOfServiceUrl())
            // 作者,维护人相关信息. 维护人姓名, 维护人邮箱, 维护人网址
            .contact(new Contact(properties.getContactName(), properties.getContactUrl(), properties.getContactEmail()))
            // 许可证
            .license(properties.getLicense())
            // 许可证链接
            .licenseUrl(properties.getLicenseUrl())
            // 版本
            .version(properties.getVersion())
            // 返回一个 ApiInfo 实例
            .build();
    }

    @Bean
    public EnumPropertyBuilderPlugin enumPropertyBuilderPlugin() {
        return new EnumPropertyBuilderPlugin();
    }


    @Bean
    public ValidatePropertyBuilderPlugin validatePropertyBuilderPlugin() {
        return new ValidatePropertyBuilderPlugin();
    }
}
