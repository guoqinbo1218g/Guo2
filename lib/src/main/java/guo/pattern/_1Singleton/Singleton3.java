package guo.pattern._1Singleton;

/**
 * 作者：author
 * 时间：2017/12/7:14:28
 * 说明： 双重加锁校验
 */

public class Singleton3 {
    private Singleton3(){}
    private volatile static Singleton3 singleton3;
    public static Singleton3 getInstance(){
        if (singleton3 == null){
            synchronized (Singleton3.class){
                if (singleton3 ==null){
                    singleton3 = new Singleton3();
                }
            }
        }
        return singleton3;
    }
}
