package guo.guo.mainitem._1myview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：author
 * 时间：2017/10/26:10:49
 * 说明： 贝塞尔函数的二阶曲线
 */

public class BezierView extends View {
    private static final String TAG = "BezierView";
    private PointF startPointF,endPointF,controlPointF;

    private int centerX, centerY;
    private Paint paint;

    public BezierView(Context context) {
        this(context,null);
        Log.e(TAG, "构造:               ");
    }
    public BezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(); //初始化
    }

    private void init() {
        Log.e(TAG, "init:               ");
        startPointF = new PointF(0,0);
        endPointF = new PointF(0,0);
        controlPointF = new PointF(0,0);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setTextSize(50);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e(TAG, "onSizeChanged: ");
        super.onSizeChanged(w, h, oldw, oldh);
//        init();

        centerX = w/2;
        centerY = h/2;
        startPointF.x = centerX-200;
        startPointF.y = centerY;
        endPointF.x = centerX+200;
        endPointF.y = centerY;

        controlPointF.x = centerX;
        controlPointF.y = centerY;

    }
//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controlPointF.x = event.getX();
        controlPointF.y = event.getY();
        invalidate();
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//      canvas.drawPoint(startPointF.x,startPointF.y,paint);
        paint.setColor(Color.RED);

        canvas.drawLine(startPointF.x,startPointF.y,controlPointF.x,controlPointF.y,paint);
        canvas.drawLine(endPointF.x,endPointF.y,controlPointF.x,controlPointF.y,paint);

        paint.setColor(Color.BLUE);
        Path path = new Path();
        path.moveTo(startPointF.x,startPointF.y);
        path.quadTo(controlPointF.x,controlPointF.y,endPointF.x,endPointF.y);

        canvas.drawPath(path,paint);

    }
}
