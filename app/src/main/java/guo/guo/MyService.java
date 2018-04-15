package guo.guo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 *  使用 状态栏的方式，并把服务设置为前台服务，onStartCommand()中返回START_STICKY；
 *  如果服务被杀死则启动广播继续开启服务。
 *  有没有必要使用双服务？
 */
public class MyService extends Service {
    private static final String TAG = "MyService";
    public static final int NOTIFICATION_ID = 65535;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        startNotification();//使用状态栏 ，并设置为前台服务
    }

    @SuppressWarnings("WrongConstant")
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
//        Log.e("ServiceReceiver", "MyService  onStartCommand: ");

        doSomething();
        return Service.START_STICKY;
    }


    private void doSomething() {
    }

    private void startNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setContent(RemoteViews views)//自定义布局样式
                .setSmallIcon(R.drawable.girl)
                .setContentTitle("Notification")
                .setContentText("hello world")
                .setAutoCancel(true) //设置点击后消失
                .setPriority(Notification.PRIORITY_MAX)// 设置该通知优先级
                .setFullScreenIntent(pendingIntent,false)//浮动通知
                .setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
        //如果 id 为 0 ，那么状态栏的 notification 将不会显示。
        startForeground(NOTIFICATION_ID,builder.build());
        //stopSelf();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        stopForeground(true);
        //this,ServiceReceiver.class
        Intent intent = new Intent();
        intent.setAction("myservice.ServiceReceiver");
        sendBroadcast(intent);
        super.onDestroy();
    }
//    在内存紧张的时候，系统回收内存时，会回调OnTrimMemory
    @Override
    public void onTrimMemory(int level) {
        Log.e(TAG, "MyService  onTrimMemory: ");
        super.onTrimMemory(level);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
