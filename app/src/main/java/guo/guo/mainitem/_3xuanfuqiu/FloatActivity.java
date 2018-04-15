package guo.guo.mainitem._3xuanfuqiu;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import guo.guo.MyApplication;
import guo.guo.R;

public class FloatActivity extends AppCompatActivity {
    private static final String TAG = "FloatActivity";
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams param;
    private ImageView mLayout;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FloatView.CLICK:
                    //属性动画--旋转
                    Animator animator = AnimatorInflater.loadAnimator(FloatActivity.this, R.animator.property_animator);
                    animator.setTarget(mLayout);
                    animator.start();

                    Log.e(TAG, "onClick: ");
                    Toast.makeText(FloatActivity.this, "点击了",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_main);
        /**
         *Android6.0以上系统增加了权限管理，所以需要添加如下代码，来让用户选择打开桌面浮窗的权限 
         */
        if (Build.VERSION.SDK_INT >= 23) {
            if (! Settings.canDrawOverlays(FloatActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent,10);
            }
        }
        showView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...  
                    Toast.makeText(FloatActivity.this, "not granted", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    private void showView(){

        mLayout=new FloatView(getApplicationContext(),mHandler);

        mLayout.setBackgroundResource(R.drawable.girl);

        //获取WindowManager  
        mWindowManager=(WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数  
        param = ((MyApplication)getApplication()).getWmParams();

        param.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示类型,重要（These windows are always on top of application windows）
        param.format=1;
        param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 表示Window不需要获取焦点  
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;//可以监听MotionEvent的ACTION_OUTSIDE事件  
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版限制--即允许在可见的屏幕之外  

        param.alpha = 1.0f;

        param.gravity= Gravity.LEFT|Gravity.TOP;   //调整悬浮窗口至左上角  
        //以屏幕左上角为原点，设置x、y初始值  
        param.x=0;
        param.y=0;

        //设置悬浮窗口长宽数据  
        param.width=140;
        param.height=140;

        //显示myFloatView图像  
        mWindowManager.addView(mLayout, param);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //在程序退出(Activity销毁）时销毁悬浮窗口  
        mWindowManager.removeView(mLayout);
    }
}
