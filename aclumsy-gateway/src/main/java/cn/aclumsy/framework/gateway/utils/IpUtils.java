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
package cn.aclumsy.framework.gateway.utils;

import cn.aclumsy.framework.common.constant.MagicalConstant;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

/**
 * IP 工具类
 *
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public class IpUtils {

    /**
     * x-forwarded-for
     */
    private static final String X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * Proxy-Client-IP
     */
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";

    /**
     * WL-Proxy-Client-IP
     */
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * HTTP_CLIENT_IP
     */
    private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

    /**
     * HTTP_X_FORWARDED_FOR
     */
    private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    /**
     * X-Real-IP
     */
    private static final String X_REAL_IP = "X-Real-IP";

    /**
     * unknown
     */
    private static final String UNKNOWN = "unknown";

    /**
     * 获取请求的真实IP
     *
     * @param request
     * @return
     */
    public static String getRealIp(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst(X_FORWARDED_FOR);
        logger.info("x-forwarded-for ip {}", headers.get(X_FORWARDED_FOR));
        logger.info("X-Real-IP ip {}", headers.get(X_REAL_IP));
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            logger.debug("多次反向代理后会有多个ip值，第一个ip才是真实ip: {}", ip);
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            boolean contains = ip.contains(MagicalConstant.COMMA);
            if (contains) {
                ip = ip.split(MagicalConstant.COMMA)[0];
            }
        }
        if (checkIp(ip)) {
            ip = headers.getFirst(PROXY_CLIENT_IP);
            logger.info("PROXY_CLIENT_IP: {}", ip);
        }
        if (checkIp(ip)) {
            ip = headers.getFirst(WL_PROXY_CLIENT_IP);
            logger.info("WL_PROXY_CLIENT_IP: {}", ip);
        }
        if (checkIp(ip)) {
            ip = headers.getFirst(HTTP_CLIENT_IP);
            logger.info("HTTP_CLIENT_IP: {}", ip);
        }
        if (checkIp(ip)) {
            ip = headers.getFirst(HTTP_X_FORWARDED_FOR);
            logger.info("HTTP_X_FORWARDED_FOR: {}", ip);
        }
        if (checkIp(ip)) {
            ip = headers.getFirst(X_REAL_IP);
            logger.info("X_REAL_IP: {}", ip);
        }
        if (checkIp(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }
        return ip;
    }

    /**
     * 判断Ip是否为空或者为 unknown
     * @param ip ip
     * @return boolean
     */
    private static boolean checkIp(String ip) {
        return ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip);
    }
}
