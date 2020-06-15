package com.tan.springcloud2producer.test.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * Cglib 动态代理需要2步：
 * 第一步：定义额外的操作
 *  通过实现 MethodInterceptor 接口，来定义在执行代理对象方法前后自己的动作。
 * 第二步：获取代理对象
 *  通过 Enhancer.create 获取代理对象，因为这个只需要。
 */
public class CglibProxy implements MethodInterceptor {
    // 获取代理对象
    public <T> T getProxy(Class<T> clazz) {
        return (T) Enhancer.create(clazz, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }

    private void before() {
        System.out.println("cglib proxy before ...");
    }

    private void after() {
        System.out.println("cglib proxy after ...");
    }

    public static void main(String[] args) {
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/admin/IdeaProjects/temp/jarTest/com/sun/proxy");
        CglibProxy cglibProxy = new CglibProxy();
        // cglib 代理的是类，它的实现方式是通过继承一个类作为它的子类来覆盖父类中的方法
        HelloImpl helloProxy = cglibProxy.getProxy(HelloImpl.class);
//        helloProxy.say("dada");
        helloProxy.add();
    }
}




// cglib生成了3个文件，一个是继承了FastClass类的文件
//public class HelloImpl$$EnhancerByCGLIB$$d77abcd1$$FastClassByCGLIB$$843af610 extends FastClass {
//    public Object invoke(int var1, Object var2, Object[] var3) throws InvocationTargetException {
//        d77abcd1 var10000 = (d77abcd1)var2;
//        int var10001 = var1;
//
//        try {
//            switch(var10001) {
//                // 这里的方法很多我们不需要关心，只需要知道我根据一个int值就可以知道要调用的是那个方法
//                // invokeSuper最终调用是在这里执行的，也就是调用了实现类的CGLIB$say$1方法，那这个方法自然应该是在代理类里面定义的，我们去看看
//                case 16:
//                    var10000.CGLIB$say$1((String)var3[0]);
//                    return null;
//
//            }
//        } catch (Throwable var4) {
//            throw new InvocationTargetException(var4);
//        }
//
//        throw new IllegalArgumentException("Cannot find matching method/constructor");
//    }
//    // 一个是代理类，代理类里面方法也很多，我们只需要关注2个方法
//    public class HelloImpl$$EnhancerByCGLIB$$d77abcd1 extends HelloImpl implements Factory {
//        // 这个就是fast类里面调用的方法，可以看到这个方法就是调用父类的方法
//        final void CGLIB$say$1(String var1) {
//            super.say(var1);
//        }
//
//        // 这个方法就是覆写的父类的say方法
//        public final void say(String var1) {
//            MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
//            if (var10000 == null) {
//                CGLIB$BIND_CALLBACKS(this);
//                var10000 = this.CGLIB$CALLBACK_0;
//            }
//// 如果拦截器不为空就调用拦截器的逻辑
//            if (var10000 != null) {
//                var10000.intercept(this, CGLIB$say$1$Method, new Object[]{var1}, CGLIB$say$1$Proxy);
//            } else {
//                // 如果没有定义拦截器就直接调用父类的方法
//                super.say(var1);
//            }
//        }
//    }