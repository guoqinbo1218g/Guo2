package guo.pattern._1Singleton;

/**
 * 作者：author
 * 时间：2017/12/7:14:24
 * 说明：  懒汉式单例,这是加锁了 ,线程安全的  没写线程不安全的其实去掉synchronized就是了
 */

public class Singleton2 {
    private Singleton2(){}
    private static Singleton2 singleton2;
    public static synchronized Singleton2 getInstance(){
        if (singleton2 == null){
            singleton2 = new Singleton2();
        }
        return singleton2;
    }
}
