package guo.guo.mainitem._1myview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：author
 * 时间：2017/10/24:13:59
 * 说明：
 */

public abstract class CanvasPathView extends View{
    private static final String TAG = "CanvasPathView";
    protected int viewWidth;
    protected int viewHeigh;
    protected Paint paint;
    public CanvasPathView(Context context) {
        super(context,null);
    }

    public CanvasPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CanvasPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        Log.e(TAG, "CanvasPathView: 333");
        paint = new Paint();             // 创建画笔
        paint.setColor(Color.RED);           // 画笔颜色 - 红色色
        paint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        paint.setStrokeWidth(5);              // 边框宽度 - 10
    }

    @Override   //测量View大小
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heigh = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override  //确定子View布局(自定义View包含子View时有用)
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


//    如果对View的宽高进行修改了，不要调用 super.onMeasure( widthMeasureSpec, heightMeasureSpec);
//    要调用 setMeasuredDimension( widthsize, heightsize);
    @Override  //确定view的大小
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeigh = h;
    }

    @Override   //实际绘制内容
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(viewWidth/2 ,viewHeigh/2);
        canvas.drawLine(0,-viewHeigh/2,0,viewHeigh/2,paint);
        canvas.drawLine(-viewWidth/2,0,viewWidth/2,0,paint);

        startDraw(canvas);
    }
    protected abstract void startDraw(Canvas canvas);


}
