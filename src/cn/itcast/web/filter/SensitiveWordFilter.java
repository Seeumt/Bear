package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*")
public class SensitiveWordFilter implements Filter {
    private List<String> list = new ArrayList<String>();



    public void init(FilterConfig config) throws ServletException {

        try{
            //1.获取文件真实路径
            ServletContext servletContext = config.getServletContext();
            String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");
            //2.读取文件
            BufferedReader br = new BufferedReader(new FileReader(realPath));
            //3.将文件的每一行数据添加到list中
            String line = null;
            while((line = br.readLine())!=null){
                list.add(line);
            }

            br.close();

            System.out.println(list);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void destroy() {
    }

    // TODO: 2019/7/17 java里面为什么要用接口来整对象呢，就是因为接口对象能方便调用（某一个）多个实现类的方法么？
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1.创建代理对象，增强getParameter()方法
        /**
         * 方法的功能不够用了，想给这个方法加点功能  装饰模式 代理模式
         *
         * 只有代理对象来调用那些接口里的方法时，才会执行invoke方法，如果你不需要增强，就应当按照原来像真实对象调用该方法时一样执行
         * 但是最后会返回一个null,谁让你
         * method.invoke(真实对象，原参/变参) 真实对象来执行某method（参数会发生变化）
         */
        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            // TODO: 2019/7/17  //这个proxy_req但是怎么执行的啊
            //是把req对象转变成了代理对象，然后代理对象调用需要用到的方法（真实对象的方法），这样就会执行invoke方法
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getParameter")) {//如果proxy_req调用的是
                    //1.因为返回值中包含着敏感词，所以要对返回值进行"增强"
                    String value = (String) method.invoke(req, args);
                    if (value != null) {
                        for (String str : list) {
                            System.out.println(str);
                            if (value.contains(str)) {
                                 value = value.replaceAll(str, "***");
                            }
                        }
                    }
                return value; //proxy_req 代理对象调用某方法时，返回了一个新的值
                              //就好像 String msg = proxy_req.getParameter("msg");
                }

/**
 * 还有从getParameterMap
 */
                    return method.invoke(req,args);//没有变化、像往常一样，跟真实对象调用方法一样





            }
        });

        //2.放行
        chain.doFilter(proxy_req, resp);
    }



}
