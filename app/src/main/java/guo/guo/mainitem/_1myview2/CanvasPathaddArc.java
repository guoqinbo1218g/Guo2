package guo.guo.mainitem._1myview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 作者：author
 * 时间：2017/10/25:16:38
 * 说明：
 */

public class CanvasPathaddArc extends CanvasPathView {
    public CanvasPathaddArc(Context context) {
        super(context,null);
    }

    public CanvasPathaddArc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CanvasPathaddArc(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void startDraw(Canvas canvas) {
        Path path = new Path();
        path.lineTo(100,100);

        RectF rectF = new RectF(0,0,200,200);
        path.addArc(rectF,0,270);

        canvas.drawPath(path,paint);
    }
}
