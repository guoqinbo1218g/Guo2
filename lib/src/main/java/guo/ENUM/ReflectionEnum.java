package guo.ENUM;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

/**
 * 作者：author
 * 时间：2017/12/9:9:09
 * 说明：
 */

public class ReflectionEnum {

    public static Set<String> analyze(Class<?> enumClass){
        System.out.println("--------------analyze   enumClass"+enumClass+"-------");
        for (Type genericInterface:enumClass.getGenericInterfaces()) {
            System.out.println("GenericInterface:"+genericInterface);
        }
        System.out.println("base:"+enumClass.getSuperclass());
        Set<String> methods = new TreeSet<>();
        for (Method method:enumClass.getMethods()) {
            methods.add(method.getName());
        }
        System.out.println("methods:"+methods);
        return methods;
    }

    @Test
    public void reflectionenum(){
        Set<String> weekMethods = analyze(WeekEnum.class);

    }
}
