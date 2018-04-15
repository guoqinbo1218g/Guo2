package guo.st_reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class Ref_T<T> {

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
    public void testGenericReturnType() throws Exception{
        Method method = clazz.getDeclaredMethod("getStringList",null);
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type type:actualTypeArguments) {
                Class typeClass = (Class) type;
                System.out.println("    typeClass :"+typeClass);
            }

        }
    }
//    getType: class java.util.HashMap
//    getGenericType:java.util.HashMap<java.lang.String, java.lang.Integer>
//    rawType:class java.util.HashMap
//    actualTypeArgument:class java.lang.String
//    actualTypeArgument:class java.lang.Integer
    @Test
    public void testGenericType() throws Exception {
        //   hashmap的成员变量  stringHashMap   list的变量   stringList
        Field stringList = clazz.getDeclaredField("stringHashMap");
        // 直接使用getType()取出Field类型只对普通类型的Field有效
        Class<?> type = stringList.getType();
        System.out.println("getType: "+type);
        Type genericType = stringList.getGenericType(); // 获得Field实例f的泛型类型
        System.out.println("getGenericType:"+genericType);
        if (genericType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type rawType = parameterizedType.getRawType();//获取原始类型
            System.out.println("rawType:" + rawType);
            // 取得泛型类型的泛型参数
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type actualType:actualTypeArguments) {
                System.out.println("actualTypeArgument:"+actualType);
            }
        }

    }
    @Test
    public void test3() throws Exception {
        Field field = clazz.getDeclaredField("stringList");
        Type type = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        System.out.println(" test3 "+type);
    }



}
