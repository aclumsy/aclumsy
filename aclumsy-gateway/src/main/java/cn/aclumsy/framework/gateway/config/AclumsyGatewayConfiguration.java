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

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Gateway 配置类
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(RateLimiterProperties.class)
public class AclumsyGatewayConfiguration {

    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;

    private final GatewayProperties gatewayProperties;


    public AclumsyGatewayConfiguration(@NotNull ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                       ServerCodecConfigurer serverCodecConfigurer, GatewayProperties gatewayProperties) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
        this.gatewayProperties = gatewayProperties;
    }

    /**
     * 跨域设置, dev环境生效
     * @return CorsWebFilter 跨域过滤器
     */
    @Bean
    @Profile("dev")
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    /**
     * 限流异常处理器
     * @return 限流异常处理器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 限流过滤器
     * @return 限流过滤器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }


    /**
     * SpringCloud Gateway 限流解析器
     * @param rateLimiterProperties 限流配置
     * @return 限流解析器
     */
    @Bean("ipResolver")
    @Primary
    public KeyResolver ipResolver(@NotNull RateLimiterProperties rateLimiterProperties) {

        if (rateLimiterProperties.getFilter() == null) {
            return getKeyResolver(rateLimiterProperties);
        }
        List<RouteDefinition> routes = gatewayProperties.getRoutes();
        routes.forEach(routeDefinition -> {
            List<FilterDefinition> filters = routeDefinition.getFilters();
            filters.add(rateLimiterProperties.getFilter());
        });
        return getKeyResolver(rateLimiterProperties);
    }

    /**
     * 获取限流解析器
     * @param rateLimiterProperties 限流配置
     * @return 限流解析器
     */
    @Contract(pure = true)
    private @NotNull KeyResolver getKeyResolver(RateLimiterProperties rateLimiterProperties) {
        return exchange -> {
            // 获取远程请求的ip地址
            String hostName = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName();
            if (rateLimiterProperties.getWhiteList().contains(hostName)) {
                return Mono.just("____EMPTY_KEY__");
            }
            return Mono.just(hostName);
        };
    }
}
