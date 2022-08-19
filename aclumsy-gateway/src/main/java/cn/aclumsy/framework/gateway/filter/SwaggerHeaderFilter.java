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
package cn.aclumsy.framework.gateway.filter;

import cn.aclumsy.framework.gateway.consts.SwaggerConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


/**
 * 如果网关路由为 BasePath/test/{a}，在 swagger会显示为 test/{a}，缺少了 BasePath 这个路由节点。 <br>
 * 断点源码时发现在 Swagger 中会根据 X-Forwarded-Prefix 这个 Header来获取 BasePath. <br>
 * 将它添加至接口路径与 host 中间，这样才能正常做接口测试，而 Gateway 在做转发的时候并没有这个 Header添加进 Request. <br>
 * 所以发生接口调试的 404 错误。解决思路是在 Gateway 里加一个过滤器来添加这个 Header。<br>
 *
 * 高版本 Spring 将给我们添加上了这个 Header，不再需要人为添加，如果重复添加将导致 baseurl 出现两个路由节点名称 <br>
 * 即配置文件中不需要添加 <br>
 * filters: <br>
 *   - SwaggerHeaderFilter <br><br>
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Component
public class SwaggerHeaderFilter implements GatewayFilterFactory<SwaggerHeaderFilter> {

    @Override
    public GatewayFilter apply(SwaggerHeaderFilter config) {
        return (exchange, chain) -> {

            // 如果请求的是 Swagger 的 API 文档，放行
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (!StringUtils.endsWithIgnoreCase(path, SwaggerConstant.URI)) {
                return chain.filter(exchange);
            }
            // 构建新的请求，添加 X-Forwarded-Prefix 字段 放行
            String basePath = path.substring(0, path.lastIndexOf(SwaggerConstant.URI));
            ServerHttpRequest serverHttpRequest = request.mutate().header(SwaggerConstant.HEADER_NAME, basePath).build();
            ServerWebExchange serverWebExchange = exchange.mutate().request(serverHttpRequest).build();
            return chain.filter(serverWebExchange);

        };
    }
}
