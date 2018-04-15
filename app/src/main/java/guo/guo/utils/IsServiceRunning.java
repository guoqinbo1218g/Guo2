package guo.guo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * 作者：author
 * 时间：2017/12/16:14:20
 * 说明： 判断服务是否开启的工具类
 */

public class IsServiceRunning {
    public static boolean isServiceRunning(Context context , String serviceName){
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfoList = activityManager.getRunningServices(100);
        if (runningServiceInfoList.size() <= 0)
            return false;
        for (ActivityManager.RunningServiceInfo info:runningServiceInfoList) {
            String infoName = info.service.getClassName().toString();
            if (TextUtils.equals(infoName,serviceName)){
                isRunning = true;
                break;
            }
        }

        return isRunning;

    }
}
