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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger 配置属性文件
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "aclumsy.swagger")
@SuppressWarnings("all")
public class SwaggerProperties {

    /**
     * 是否开启 Swagger
     */
    private boolean enabled = true;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 服务条款网址
     */
    private String termsOfServiceUrl = "https://swagger.io/";

    /**
     * 文档维护人姓名
     */
    private String contactName = "Aclumsy";

    /**
     * 文档维护人URL地址
     */
    private String contactUrl = "https://github.com/aclumsy/aclumsy";

    /**
     * 文档维护人邮箱
     */
    private String contactEmail = "827577959@qq.com";

    /**
     * 许可
     */
    private String license = "Apache 2.0";

    /**
     * 许可链接
     */
    private String licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0";

    /**
     * 版本
     */
    private String version = "1.0.0";

}
