package cn.itcast.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        //1.创建真实对象
        Lenovo lenovo = new Lenovo();






        // 2.动态代理增强Lenovo对象
            /*
            三个参数：
                1.类加载器：真实对象lenovo.getClass().getClassLoader()
                2.接口数组：真实对象lenovo.getClass().getInterfaces()（确保代理对象和真实对象实现相同的接口）
                3.处理器 ：new InvocationHandler


             */
        // TODO: 2019/7/17 朝真实对象的接口类型转  目的是为了能调用真实对象的所有方法
        SaleComputer proxy_lenovo = (SaleComputer) Proxy.newProxyInstance(lenovo.getClass().getClassLoader(), lenovo.getClass().getInterfaces(), new InvocationHandler() {
            //此时 proxy_lenovo和lenovo实现了相同的接口，所以可以把proxy_lenovo转型为接口（从Object转）

            /**
             * 代理逻辑编写的方法，代理对象调用的所有方法都会触发该方法执行
             *
             * @param proxy  代理对象 一般不用
             * @param method  代理对象调用的方法（之一）封装成为对象
             * @param args 代理的对象调用某方法时，传递的参数   数组哦  还是object 什么 都可以当成参数
             * @return
             * @throws Throwable
             */
            @Override // TODO: 2019/7/17 为啥这个要返回一个null呢？如果没有"增强" 的话
            // TODO: 2019/7/17 然而我们希望不用增加某方法时，还可以按照原方法执行（不要返回null），就是用method.invoke(真实对象，原参数)

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println(method.getName());
//                System.out.println("该方法被执行了");
//                System.out.println(args[0]);
                /**
                 * 增强方式
                 * 1.增强参数
                 * 2.增强参数列表
                 * 3.增强方法体
                 */
                //判断是否为sale方法，是否需要增强
                if (method.getName().equals("sale")) {
                    //1.增强参数
                    double money = (double) args[0];//arg[],是代理对象调用方法时传进来的那个参数
                    money = money * 0.85;
                    System.out.println("专车接您");
                    //method是当前正在调用的某个方法，
                    //method.invoke(真实对象，变参)的意思就是 可以理解成还是在用真实对象执行这个method方法，只是参数变了
                    String obj = (String) method.invoke(lenovo, money);// TODO: 2019/7/17  这儿不是要数组么，为什么还能传递一个变量？
                    // TODO: 2019/7/17 貌似这个invoke跟上面那个invoke的参数列表不一样
                    //2.增强返回值
                    System.out.println("专车送您");

                    return obj+"_鼠标垫";
                } else {
                    Object obj = method.invoke(lenovo, args);

                    return obj;
                }





            }
        });

    //   3.调用方法
        String computer = proxy_lenovo.sale(8000);//没该变的话，就像原封不动一般，变参没出现，就是method.invoke(真实对象，args原来的参数))
        //代理对象调用真实对象的方法时，就会执行
        //obj就是proxy_lenovo.sale(8000)的返回值
        //proxy_lenovo.show();
        System.out.println(computer);
    }
}
