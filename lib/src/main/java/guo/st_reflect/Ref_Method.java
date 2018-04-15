package guo.st_reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;


public class Ref_Method {
    private Class clazz;
    @Before
    public void init(){
        try {
            clazz = Class.forName("guo.st_reflect.RefBean");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testMethod() throws Exception {
        Method [] methods = clazz.getDeclaredMethods();
//        for (Method method:methods) {
//            System.out.println("Method:"+method.getName());
//            for (Class cla:method.getParameterTypes()) {
//                System.out.println("Method:parameterType:"+cla.getName());
//            }
//            System.out.println("----------------------");
//        }
        RefBean refBean = (RefBean) clazz.newInstance();
        Method method = clazz.getDeclaredMethod("test",String.class);
        method.setAccessible(true);
        method.invoke(refBean,"guo method");
        System.out.println(" invoke method "+refBean.toString());
        //下面 的是静态方法,具体调用的方法是由 class调用者决定和   invoke中的一个obj传入null即可
        Class clazz2 = Class.forName("guo.st_reflect.RefBean2");
        Method methodStatic = clazz2.getDeclaredMethod("testStaticMethod",String.class);
        methodStatic.setAccessible(true);
        methodStatic.invoke(null,"statci method");

    }
}
