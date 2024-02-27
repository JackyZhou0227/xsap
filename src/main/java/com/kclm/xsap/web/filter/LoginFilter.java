package com.kclm.xsap.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/******************
 * @Author yejf
 * @Description 认证过滤器【登录过滤器】
 */
@Order(1)
//@ServletComponentScan
@Slf4j
@WebFilter(urlPatterns = {"*.do","/index.html","/index", "" ,"/"},filterName = "loginFilter", dispatcherTypes =
        {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC})
public class LoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("LoginFilter.init()...执行...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //强制类型转换
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        if (session == null) {
            log.debug("登录session为null；即将重定向到登录页面");
            resp.sendRedirect(req.getServletContext().getContextPath() + "/user/toLogin");
        } else {
            //继续判断session中有没有user信息
            if (session.getAttribute("LOGIN_USER") == null) {
                log.debug("存在session但user为null，即将重定向到登录页面");
                resp.sendRedirect(req.getServletContext().getContextPath() + "/user/toLogin");
            } else {
                log.debug("session有效，放行登录");
                chain.doFilter(req, resp);
            }
        }


    }

    @Override
    public void destroy() {
        log.debug("LoginFilter.destroy()...执行...");
    }
}
