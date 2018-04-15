package guo.st_reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;


public class Ref_Constructor {
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
    public void testConstrutors(){

        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor con:constructors) {
           Class [] parameterTypes = con.getParameterTypes();
            for (Class c:parameterTypes) {
                System.out.println(" parameterType :"+c.getName());
            }
            System.out.println("--------------------------------");
        }
        //创建
        try {
            RefBean bean = (RefBean) clazz.newInstance();//直接用class 创建无参 实例(要有无参构造)
            //使用 contructor创建实例
            Constructor constructor = clazz.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            RefBean refBean = (RefBean) constructor.newInstance("guoguo");
            System.out.println("使用constructor创建对象    "+refBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
