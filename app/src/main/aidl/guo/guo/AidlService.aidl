// AidlService.aidl
package guo.guo;

// Declare any non-default types here with import statements

interface AidlService {
    /**         as帮忙创建,用处不大
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    //
    void startService();
    void stopService();

}
