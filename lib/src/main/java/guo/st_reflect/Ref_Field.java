package guo.st_reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;


public class Ref_Field {
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
    public void testField(){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields) {
            System.out.println("Field :"+field.getName());
        }
        try {
            RefBean refBean = (RefBean) clazz.newInstance();
            Field field1 = clazz.getDeclaredField("name");
            field1.setAccessible(true);
            field1.set(refBean,"тете");
            System.out.println("set   field"+refBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
