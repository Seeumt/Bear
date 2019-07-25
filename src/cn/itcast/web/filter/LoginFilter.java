package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*
登陆验证的过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println(req);
        //强制类型转化一下，因为里面没有
        HttpServletRequest request = (HttpServletRequest) req;
        //1. 获取资源请求路径
        String uri = request.getRequestURI();
        //2.判断是否包含登录相关资源路径
        /**
         * 还要排除css/js/图片/验证码等资源
         */
        if (uri.contains("/login.jsp") || uri.contains("/loginServlet")|| uri.contains("/css/")|| uri.contains("/js/")|| uri.contains("/fonts")|| uri.contains("/loginServlet")|| uri.contains("/checkCodeServlet")) {
            //包含，证明用户就是想登陆 想去登陆界面
            chain.doFilter(req, resp);

        } else {
            //不包含，需要验证是否登录
            //3.从session里面获得user
            Object user = request.getSession().getAttribute("user");
            if (user != null) {
                chain.doFilter(req, resp);
            } else {
                //为空则代表没有登陆，跳转登陆页面
                request.setAttribute("login_msg","您尚未登录，请登录！");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
