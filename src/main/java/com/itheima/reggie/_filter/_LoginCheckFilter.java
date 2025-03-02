package com.itheima.reggie._filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common._BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 项目名称: reggie
 * 文件名: null.java
 * 作者: shibinsun
 * 日期: 14/12/2024
 * 描述:
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class _LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取本次请求的 URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}", requestURI);

        //定义不需要处理的请求路径
        String[] urls = {"/api/employee/login",
                "/api/employee/logout",
                "/api/backend/**",
                "/api/front/**",
                "/api/common/**",
                "/api/user/sendMsg",
                "/api/user/login",
                };

        //2.判断本次请求是否需要处理
        boolean check = check(requestURI, urls);

        //3.如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        //4-1.判断登入状态，如果已登入，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            _BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        //4-2.判断移动端登入状态，如果已登入，则直接放行
        Object userObj = request.getSession().getAttribute("user");

        Long userId = null;
        if (userObj instanceof Long) {
            userId = (Long) userObj; // 直接取 ID
        } else if (userObj instanceof User) {
            userId = ((User) userObj).getId(); // 从 User 对象里取 ID
        }

        if (userId != null && userId > 0) {
            log.info("用户已登录，用户ID：{}", userId);
            _BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }

        // 用户未登录
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


        /*if (request.getSession().getAttribute("user") != null) {
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登入");
        //5.如果未登入则返回未登入结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;*/
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param requestURL
     * @param urls
     * @return
     */
    private boolean check(String requestURL, String[] urls) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
















