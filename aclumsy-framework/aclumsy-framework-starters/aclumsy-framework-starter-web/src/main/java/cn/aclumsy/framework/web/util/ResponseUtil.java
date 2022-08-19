package cn.aclumsy.framework.web.util;

import com.alibaba.fastjson2.JSONObject;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Response工具类
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unused")
public class ResponseUtil {

    /**
     * response返回Object
     * @param response response
     * @param object object
     */
    public static void writeObject(HttpServletResponse response, Object object) {
        writeJson(response, JSONObject.toJSONString(object));
    }

    /**
     * response返回json
     * @param response response
     * @param jsonStr jsonStr
     */
    public static void writeJson(@NotNull HttpServletResponse response, String jsonStr) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(jsonStr);
            writer.flush();
        } catch (IOException e) {
            logger.error("response 输出 json 响应失败", e);
        }
    }

    /**
     * response写回数据
     * @param response response
     * @param data data
     */
    public static void write(HttpServletResponse response, String data) {
        try (PrintWriter writer = response.getWriter()) {
            writer.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("response 输出响应失败", e);
        }
    }

}
