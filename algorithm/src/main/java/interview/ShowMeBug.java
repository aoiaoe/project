package interview;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ShowMeBug {
    public static void main(String[] arges) throws Exception {
        IA ia = (IA) createObject(IA.class.getName() + "$getName=Abc");
        System.out.println(ia.getName()); //输出Abc
        ia = (IA) createObject(IA.class.getName() + "$getName=Bcd");
        System.out.println(ia.getName()); //输出Bcd
    }

    //请实现方法createObject
    public static Object createObject(String str) throws Exception {
        String[] param = str.split("\\$");
        Class clazz = Class.forName(param[0]);
        String[] param1 = param[1].split("=");
        return Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if(method.getName().equals(param1[0])) {
                            return param1[1];
                        }
                        return null;
                    }
                });
    }


}

interface IA {
    String getName();
}


//    final String s = str;
//        return new IA() {
//@Override
//public String getName() {
//        return s.replace(IA.class.getName() + "$getName=", "");
//        }
//        };