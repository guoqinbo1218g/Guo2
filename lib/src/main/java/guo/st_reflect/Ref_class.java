package guo.st_reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Ref_class {
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
    public void testModifiers(){
        try {

            int modifiers = clazz.getModifiers();
            System.out.println("class���η���"+modifiers);
            System.out.println("isPublic:"+Modifier.isPublic(clazz.getModifiers()));//true
            System.out.println("isPrivate:"+Modifier.isPrivate(clazz.getModifiers()));//false



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPackage(){
        Package clazzPackage = clazz.getPackage();
        System.out.println("Package name :"+clazzPackage.getName());//guo.st_reflect
    }
    @Test
    public void testSuper(){
        try {
            Class clazz2 = Class.forName("guo.st_reflect.RefBean2");
            Class superClass = clazz2.getSuperclass();
            System.out.println("�̳��������"+superClass.getSimpleName());
            Class[] interfaces = clazz2.getInterfaces();
            for (Class in :interfaces) {
                System.out.println("�ӿ�:"+in.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testConstrutors(){
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor con:constructors) {
            System.out.println("Constructor :"+con.getName());
        }
    }
    @Test
    public void testMethod(){
        Method[] methods = clazz.getMethods();
        Method[] methods2 = clazz.getDeclaredMethods();
        for (Method me:methods) {//��ȡ��˽�к͸���ķ���
            System.out.println("getMethods  Method:"+me.getName());
        }
        System.out.println("-----------------------------------------------");
        for (Method me2:methods2){//������,���Ի�ȡ˽�з���,������ȡ���෽��
//            me2.setAccessible(true);
            System.out.println("getDeclaredMethods  Method:"+me2.getName());
        }

    }
    @Test
    public void testField(){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields) {
            System.out.println("Field:"+field.getName());
        }
    }


}
