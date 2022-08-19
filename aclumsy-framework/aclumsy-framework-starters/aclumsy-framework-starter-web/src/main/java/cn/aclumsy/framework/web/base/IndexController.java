package cn.aclumsy.framework.web.base;

import cn.aclumsy.framework.common.context.SpringApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基础控制器
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Controller
public class IndexController {

    /**
     * 访问默认路径重定向到 swagger 接口文档
     * @param response 响应对象
     * @throws IOException 异常
     */
    @GetMapping
    public void index(HttpServletResponse response) throws IOException {
        String contextPath = SpringApplicationContext.getProperty("server.servlet.context-path");
        response.sendRedirect((StringUtils.isNotEmpty(contextPath) ? contextPath : StringUtils.EMPTY).concat("/doc.html"));
    }

}
