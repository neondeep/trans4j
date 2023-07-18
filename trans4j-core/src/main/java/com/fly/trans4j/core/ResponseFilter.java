package com.fly.trans4j.core;

import com.fly.trans4j.util.TransHolder;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author 谢飞
 * @since 2023/6/27 16:58
 */
public class ResponseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            TransHolder.remove();
        }
    }
}
