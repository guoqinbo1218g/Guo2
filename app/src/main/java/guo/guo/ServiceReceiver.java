package guo.guo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 作者：author
 * 说明： 开启服务的广播，在服务的ondestory()的方法中开启此广播
 */

public class ServiceReceiver extends BroadcastReceiver{
    private static final String TAG = "ServiceReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: ");
        context.startService(new Intent(context,MyService.class));
    }
}
