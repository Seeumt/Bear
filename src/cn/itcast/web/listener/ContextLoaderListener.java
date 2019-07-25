package cn.itcast.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    /**
     * 监听ServletContext对象创建的，ServletContext对象服务器启动后自动创建
     * 该方法在服务器启动后自动调用
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
       // System.out.println("该对象被创建");
        //加载资源文件
        //1.获取ServletContext对象
        ServletContext servletContext = servletContextEvent.getServletContext();
        //2.加载资源文件


    }

    /**
     * 在服务器正常关闭后该方法调用
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
       // System.out.println("该对象被销毁");

    }
}
