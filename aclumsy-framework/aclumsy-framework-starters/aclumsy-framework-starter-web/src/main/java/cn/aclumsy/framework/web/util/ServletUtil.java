package cn.aclumsy.framework.web.util;

import cn.aclumsy.framework.common.constant.MagicalConstant;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Servlet 工具类
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unused")
public class ServletUtil {

    /**
     * 获取客户端IP
     * @param request request
     * @return String
     */
    public static String getClientIp(HttpServletRequest request) {
        String strXff = request.getHeader("X-Forwarded-For");
        if (strXff == null || strXff.length() == 0 || MagicalConstant.UNKNOWN.equals(strXff)) {
            return request.getRemoteAddr();
        }
        String[] strIps = strXff.split(MagicalConstant.COMMA);
        for (int i = 0, n = strIps.length; i < n; i++) {
            strIps[i] = strIps[i].trim();
            if (strIps[i].length() > 0 && !MagicalConstant.UNKNOWN.equals(strIps[i])) {
                return strIps[i];
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取客户端IP
     * @return String
     */
    public static String getClientIp() {
        try {
            return getClientIp(getRequest());
        } catch (Exception e) {
            try {
                InetAddress localHost = Inet4Address.getLocalHost();
                return localHost.getHostAddress();
            } catch (UnknownHostException ignored) {

            }
        }
        return "127.0.0.1";
    }

    /**
     * 获取request
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(servletRequestAttributes).getRequest();
    }

}
