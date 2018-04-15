package guo.st_reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;

import guo.annotation.HelloAnnotation;


public class Ref_annotation {
    private Class clazz;
    @Before
    public void init(){
        try {
            clazz = Class.forName("guo.st_reflect.RefBean");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testAnnotation(){
        Annotation[] annotations = clazz.getAnnotations();
        System.out.println("       annotations.lenth:"+annotations.length);

        for (Annotation annotation:annotations) {
            System.out.println(" annotation "+annotation.toString());
            if (annotation instanceof HelloAnnotation){
                HelloAnnotation helloAnnotation = (HelloAnnotation) annotation;
                System.out.println("    HelloAnnotation   "+helloAnnotation.name()+"  "+helloAnnotation.value());
            }
        }
        //直接根据 注解.class 找到注解
        HelloAnnotation helloAnnotation = (HelloAnnotation) clazz.getAnnotation(HelloAnnotation.class);

    }
}
