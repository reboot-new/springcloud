package com.tan.springcloud2producer.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {

    /**
     * 获取代理对象
     *
     * @param <T>
     * @return
     */
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    private Object target;

    public JDKProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object object = method.invoke(target, args);
        after();
        return object;
    }

    private void before() {
        System.out.println("interface proxy before ...");
    }

    private void after() {
        System.out.println("interface proxy after ...");
    }

    /**
     * 默认输出到当前项目的跟目录下面的包中：com/sun/proxy/$Proxy0.class
     *
     * @param args
     */
    public static void main(String[] args) {
        // 设置系统属性，输出生成的.class文件
//        -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello helloImpl = new HelloImpl();
        JDKProxy interfaceProxy = new JDKProxy(helloImpl);
        IHello hello = interfaceProxy.getProxy();
//        hello.say("dada");
        hello.add();
    }
}


//public final class $Proxy0 extends Proxy implements Hello {
//    //可以看到这里面生成了至少4个方法属性
//    private static Method m1;
//    private static Method m2;
//    private static Method m3;
//    private static Method m0;
//
//    /**
//     * 动态代理类有一个带有InvocationHandler参数的构造方法
//     * 目的是接受一个InvocationHandler参数然后在调用具体方法的时候通过
//     * InvocationHandler去调用
//     */
//    public $Proxy0(InvocationHandler var1) throws {
//        super(var1);
//    }
//
//    public final boolean equals(Object var1) throws {
//        try {
//            return (Boolean) super.h.invoke(this, m1, new Object[]{var1});
//        } catch (RuntimeException | Error var3) {
//            throw var3;
//        } catch (Throwable var4) {
//            throw new UndeclaredThrowableException(var4);
//        }
//    }
//
//    public final String toString() throws {
//        try {
//            return (String) super.h.invoke(this, m2, (Object[]) null);
//        } catch (RuntimeException | Error var2) {
//            throw var2;
//        } catch (Throwable var3) {
//            throw new UndeclaredThrowableException(var3);
//        }
//    }
//
//    /**
//     * 可以看到我们的say方法被重写了，调用的过程是通过 InvocationHandler
//     * 来调用的，而InvocationHandler在我们创建这个代理的时候就传入进了，
//     * 而我们真正的对象，在创建InvocationHandler实例的时候被传入到
//     * InvocationHandler中了，所以我们可以在InvocationHandler的
//     * invoke方法中调用我们真正的代理对象的方法。然后在invoke调用真实对象
//     * 方法的前后添加自己的处理逻辑
//     */
//    public final void say(String var1) throws {
//        try {
//            super.h.invoke(this, m3, new Object[]{var1});
//        } catch (RuntimeException | Error var3) {
//            throw var3;
//        } catch (Throwable var4) {
//            throw new UndeclaredThrowableException(var4);
//        }
//    }
//
//    public final int hashCode() throws {
//        try {
//            return (Integer) super.h.invoke(this, m0, (Object[]) null);
//        } catch (RuntimeException | Error var2) {
//            throw var2;
//        } catch (Throwable var3) {
//            throw new UndeclaredThrowableException(var3);
//        }
//    }
//
//    static {
//        try {
//            // 通过反射机制生成N个默认方法
//            // n=3+我们接口的方法数，如果接口中有一个方法那么n=3+1=4
//            // 另外的3个方法分别是Object的equals、hashCode、toString()，这么做的目的是跟我们自己的类保持一致
//            // 其中m1是Object类的equals方法
//            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
//            // 其中m2是Object类的toString方法
//            m2 = Class.forName("java.lang.Object").getMethod("toString");
//            // 其中m3是我们自己的方法，其实就是根据反射获取接口的方法
//            m3 = Class.forName("com.dada.test.delegate.Hello").getMethod("say", Class.forName("java.lang.String"));
//            // 其中m0是Object的hashCode方法
//            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
//        } catch (NoSuchMethodException var2) {
//            throw new NoSuchMethodError(var2.getMessage());
//        } catch (ClassNotFoundException var3) {
//            throw new NoClassDefFoundError(var3.getMessage());
//        }
//    }
//}


