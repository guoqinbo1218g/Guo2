package guo.guo.mainitem._1myview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 作者：author
 * 时间：2017/10/24:13:59
 * 说明：
 */

public class CanvasdrawPath extends CanvasPathView{
    private static final String TAG = "CanvasdrawPath";

    public CanvasdrawPath(Context context) {
        super(context,null);
//        Log.e(TAG, "CanvasdrawPath: ");
    }

    public CanvasdrawPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CanvasdrawPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        Log.e(TAG, "CanvasdrawPath     333: ");

    }

    @Override  //确定view的大小
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: ");
    }

    @Override
    protected void startDraw(Canvas canvas) {

        Paint paint2 = new Paint();
        paint2.setColor(Color.BLUE);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(10);

        Path path = new Path();
        path.lineTo(200,200);

        String text = "A(200,200)";
        paint.setTextSize(50);

        canvas.drawText(text,200,200,paint);
        path.moveTo(200,100);
        path.lineTo(200,0);
        path.close();
        canvas.drawPath(path,paint2);

//        RectF rectF = new RectF(100,100,200,200);
//        canvas.drawRect(rectF, paint);
    }


}
