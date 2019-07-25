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
            //1.��ȡ�ļ���ʵ·��
            ServletContext servletContext = config.getServletContext();
            String realPath = servletContext.getRealPath("/WEB-INF/classes/���дʻ�.txt");
            //2.��ȡ�ļ�
            BufferedReader br = new BufferedReader(new FileReader(realPath));
            //3.���ļ���ÿһ��������ӵ�list��
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

    // TODO: 2019/7/17 java����ΪʲôҪ�ýӿ����������أ�������Ϊ�ӿڶ����ܷ�����ã�ĳһ�������ʵ����ķ���ô��
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1.�������������ǿgetParameter()����
        /**
         * �����Ĺ��ܲ������ˣ������������ӵ㹦��  װ��ģʽ ����ģʽ
         *
         * ֻ�д��������������Щ�ӿ���ķ���ʱ���Ż�ִ��invoke����������㲻��Ҫ��ǿ����Ӧ������ԭ������ʵ������ø÷���ʱһ��ִ��
         * �������᷵��һ��null,˭����
         * method.invoke(��ʵ����ԭ��/���) ��ʵ������ִ��ĳmethod�������ᷢ���仯��
         */
        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            // TODO: 2019/7/17  //���proxy_req������ôִ�еİ�
            //�ǰ�req����ת����˴������Ȼ�������������Ҫ�õ��ķ�������ʵ����ķ������������ͻ�ִ��invoke����
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getParameter")) {//���proxy_req���õ���
                    //1.��Ϊ����ֵ�а��������дʣ�����Ҫ�Է���ֵ����"��ǿ"
                    String value = (String) method.invoke(req, args);
                    if (value != null) {
                        for (String str : list) {
                            System.out.println(str);
                            if (value.contains(str)) {
                                 value = value.replaceAll(str, "***");
                            }
                        }
                    }
                return value; //proxy_req ����������ĳ����ʱ��������һ���µ�ֵ
                              //�ͺ��� String msg = proxy_req.getParameter("msg");
                }

/**
 * ���д�getParameterMap
 */
                    return method.invoke(req,args);//û�б仯��������һ��������ʵ������÷���һ��





            }
        });

        //2.����
        chain.doFilter(proxy_req, resp);
    }



}
