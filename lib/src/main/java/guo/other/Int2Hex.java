package guo.other;

import org.junit.Test;

/**
 * 作者：author
 * 时间：2018/1/29:10:34
 * 说明：
 */

public class Int2Hex {

    @Test
    public void test(){
        System.out.println(32 == toHex(32));
    }

    public int  toHex(int number){
        String str = Integer.toHexString(number);
        System.out.println("str:"+str);
        int num = Integer.parseInt(str, 16);
        System.out.println("num:"+num);
        return num;
    }

    @Test
    public void testll(){
        double a = 11;
        double b = 22;
        System.out.println(a/b);
    }
}
