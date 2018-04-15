package guo.guo.mainitem._3xuanfuqiu;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.WindowManager;

import guo.guo.MyApplication;

/**
 * 作者：author
 * 时间：2017/11/11:15:43
 * 说明：
 */

public class FloatView extends AppCompatImageView {
    private static final String TAG = "FloatView";
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private float beginX,endX,beginY,endY;

    private WindowManager wm=(WindowManager)getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    private WindowManager.LayoutParams wmParams = ((MyApplication)getContext().getApplicationContext()).getWmParams();
    private Context context;
    private Handler mHandler;
    public static final int CLICK = 1;
    public FloatView(Context context, Handler mHandler) {
        super(context);
        this.context = context;
        this.mHandler = mHandler;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY()/*-25*/;   //25是系统状态栏的高度
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取相对View的坐标，即以此View左上角为原点
                beginX = endX = mTouchStartX =  event.getX();
                beginY = endY = mTouchStartY =  event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;

            case MotionEvent.ACTION_UP:
                endX = event.getX();
                endY = event.getY();

                if (endX - beginX == 0 && endY - beginY == 0){
                    //点击事件
                    Message msg = Message.obtain();
                    msg.what = CLICK;
                    msg.obj = "点击事件";
                    mHandler.sendMessage(msg);
                }else {
                    //发生了拖拽
                }

                mTouchStartX=mTouchStartY=0;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
        }
        return true;
    }

    private void updateViewPosition(){
        //更新浮动窗口位置参数
        wmParams.x=(int)( x-mTouchStartX);
        wmParams.y=(int) (y-mTouchStartY);

        wm.updateViewLayout(this, wmParams);

    }

}
