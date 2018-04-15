package guo.st_reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 作者：author
 * 时间：2017/12/7:11:28
 * 说明：
 */

public class Ref_array {
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
    public void testNewArray(){
       //Array.newInstance() 参数一:数组类型  参数二: 数组长度
        int[] intArray = (int[]) Array.newInstance(int.class,7);
        Array.set(intArray,0,10);
        Array.set(intArray,2,22);
        Array.set(intArray,4,44);
        Array.set(intArray,5,77);
        System.out.println(" intArray  :"+ Arrays.toString(intArray));

    }
    @Test
    public void testArray() throws Exception{
        Class<?> arrayClass = Class.forName("[Ljava.lang.String;");
        Class<?> componentType = arrayClass.getComponentType();
        System.out.println("componentType  :"+componentType);

    }
}
