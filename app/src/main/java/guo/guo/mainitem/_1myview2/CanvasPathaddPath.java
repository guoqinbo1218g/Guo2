package guo.guo.mainitem._1myview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 作者：author
 * 时间：2017/10/25:16:00
 * 说明：
 */

public class CanvasPathaddPath extends CanvasPathView {
    public CanvasPathaddPath(Context context) {
        super(context,null);
    }

    public CanvasPathaddPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CanvasPathaddPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void startDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        Path path1 = new Path();
        Path path2 = new Path();

        path1.addRect(-200,-200,200,200,Path.Direction.CW);
        path2.addCircle(0,-0,100,Path.Direction.CW);

//        path1.addPath(path2);
        path1.addPath(path2,0,-200);

        canvas.drawPath(path1,paint);

    }
}
