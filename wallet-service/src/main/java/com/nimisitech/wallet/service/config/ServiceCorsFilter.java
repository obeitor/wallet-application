package com.nimisitech.wallet.service.config;

import lombok.extern.slf4j.Slf4j;

//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Component
@Slf4j
public class ServiceCorsFilter  {
    /*@Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,origin,accept,content-type,access-control-request-method,access-control-request-headers,authorization");
        response.setHeader("Content-Security-Policy", "default-src https: http:");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        if (!"OPTIONS".equals(request.getMethod())) {
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (IOException | ServletException e) {
                log.error(e.getMessage(),e);
            }
        }
    }

    @Override
    public void destroy() {

    }*/
}
