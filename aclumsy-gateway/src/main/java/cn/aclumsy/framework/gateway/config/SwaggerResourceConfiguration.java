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
package cn.aclumsy.framework.gateway.config;

import cn.aclumsy.framework.gateway.consts.SwaggerConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/***
 * Swagger 资源提供者 <br>
 * 详见: <a href="https://doc.xiaominfo.com/docs/action/springcloud-gateway">Spring Cloud Gateway集成Knife4j</a>
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfiguration implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    /**
     * 从 Gateway 的路由列表中获取 Swagger 资源
     * 如果配置了 swaggerTitle 属性, 则优先使用 swaggerTitle, 否则使用 配置的 ID 值
     * @return SwaggerResource 资源列表
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        /*
          1) 从所有的路由配置中匹配所有包含路由ID的配置
          2) predicates 筛选出 predicates 配置的 Path 属性
          3) 若位置了 swaggerTitle 属性, 则使用 swaggerTitle, 否则使用配置的 ID 值 作为 SwaggerResource 的 name 值
          4) 将 Path 属性 最后的** 拼接为 /v3/api-docs, 组成完整的location
          5) 组装完整的 SwaggerResource 对象并且返回
         */
        gatewayProperties.getRoutes().stream()
            .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
            .forEach(route -> route.getPredicates().stream()
                .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                .forEach(predicateDefinition -> {
                    String swaggerTitle = (String)Optional.ofNullable(route.getMetadata().get(SwaggerConstant.SWAGGER_TITLE)).orElse("");
                    resources.add(swaggerResource(StringUtils.isEmpty(swaggerTitle) ? route.getId() : swaggerTitle,
                    predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("**", SwaggerConstant.URI_V3)));
                }));

        return resources;
    }

    /**
     * 组装 SwaggerResource 对象
     * @param name SwaggerResource 的 name 值 也就是 Swagger 的 title 值
     * @param location SwaggerResource 的 location 值 也就是 Swagger 的 url 值
     * @return SwaggerResource 对象
     */
    private @NotNull SwaggerResource swaggerResource(String name, String location) {
        logger.info("SwaggerResource name: {}, location: {}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(SwaggerConstant.SWAGGER_VERSION);
        return swaggerResource;
    }
}
