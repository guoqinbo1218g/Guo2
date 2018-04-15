package guo.st_reflect;

import java.io.Serializable;


/**
 *   测试 getSuperclass() ,getInterfaces()的类,除此之外没啥作用
 */
public class RefBean2 extends Ref_class implements Serializable,Cloneable{
    private static void testStaticMethod(String test){
        System.out.println("  RefBean22222222222: testStaticMethod ");
    }

}
