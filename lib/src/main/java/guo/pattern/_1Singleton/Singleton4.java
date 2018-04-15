package guo.pattern._1Singleton;

/**
 * 作者：author
 * 时间：2017/12/7:14:34
 * 说明：
 */

public class Singleton4 {
    private Singleton4(){}

    private static class SingletonHolder{
        private static final Singleton4 SINGLETON_4 = new Singleton4();
    }
    public static final Singleton4 getInstance(){
        return SingletonHolder.SINGLETON_4;
    }

}
