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
package cn.aclumsy.framework.swagger.console;

import cn.aclumsy.framework.swagger.config.SwaggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * Swagger 控制台打印输入信息
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class SwaggerConsole implements ApplicationRunner {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port:8080}")
    private String port;

    @Value("${server.servlet.context-path:''}")
    private String contextPath;

    @Resource
    private SwaggerProperties properties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("\n------------------------------------------------------------------------------\n" +
                "\tApplication '{}' is running! \n" +
                "\tTitle: {} \n" +
                "\tDescription: {} \n" +
                "\tSwagger Doc:  http://{}:{}{}/doc.html\n" +
                "------------------------------------------------------------------------------",
            properties.getTitle(), properties.getDescription(), appName, InetAddress.getLocalHost().getHostAddress(), port, contextPath);
    }

}
