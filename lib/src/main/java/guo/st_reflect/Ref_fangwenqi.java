package guo.st_reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;


public class Ref_fangwenqi {
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
    public void testGetAndSet(){
        Method[] methods = clazz.getMethods();
        for (Method method:methods) {
            if(isGetter(method)) System.out.println("getter: " + method);
            if(isSetter(method)) System.out.println("setter: " + method);
        }
    }

    public boolean isGetter(Method method){
        if(!method.getName().startsWith("get"))      return false;
        if(method.getParameterTypes().length != 0)   return false;
        if(void.class.equals(method.getReturnType())) return false;
        return true;
    }

    public boolean isSetter(Method method){
        if(!method.getName().startsWith("set")) return false;
        if(method.getParameterTypes().length != 1) return false;
        return true;
    }

}
