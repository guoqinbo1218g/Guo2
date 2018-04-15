package guo.pattern._1Singleton;

/**
 * 作者：author
 * 时间：2017/12/7:14:22
 * 说明：  饿汉式单例
 */

public class Singleton1 {
    private Singleton1(){}
    private static Singleton1 singleton1 = new Singleton1();
    public static Singleton1 getInstance(){
        return singleton1;
    }
}
