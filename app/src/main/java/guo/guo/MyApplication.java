package guo.guo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 作者：author
 * 时间：2017/11/11:15:41
 * 说明：
 */

public class MyApplication extends Application {
    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getWmParams(){
        return wmParams;
    }

    private List<Activity> mActivityList = Collections.synchronizedList(new ArrayList<>());//activit管理list
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        registerActivityListener();
    }

    // 对外提供Application实例,主义不要new
    public static MyApplication getInstance(){
        return myApplication;
    }

    public void pushActivity(Activity activity){
        mActivityList.add(activity);
    }
    public void popActivity(Activity activity){
        mActivityList.remove(activity);
    }

    public void clearAllActivity(){
        for (Activity activity:mActivityList) {
            activity.finish();
        }
    }
    //管理activity的生命周期  ,>=API19
    private void registerActivityListener(){

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                pushActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (mActivityList.contains(activity))
                    popActivity(activity);
            }
        });
    }
}
