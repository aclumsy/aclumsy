package cn.aclumsy.framework.web.context;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * RequestContext
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class RequestContext {

    /**
     * Get request or null
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequestOrNull() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(requestAttributes -> ((ServletRequestAttributes)requestAttributes).getRequest())
                .orElse(null);
    }

    /**
     * Get optional request
     * @return Optional<HttpServletRequest>
     *
     */
    public static Optional<HttpServletRequest> getOptionalRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes).getRequest());
    }

    /**
     * Get request uri
     * @return String
     */
    public static String getRequestUri() {
        return getOptionalRequest().map(HttpServletRequest::getRequestURI).orElse("");
    }

    /**
     * Get header
     * @param header String
     * @return String
     */
    public static String getHeader(String header) {
        HttpServletRequest requestOrNull = getRequestOrNull();
        return requestOrNull == null ? null : requestOrNull.getHeader(header);
    }
}
