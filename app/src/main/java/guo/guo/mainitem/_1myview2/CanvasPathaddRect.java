package guo.guo.mainitem._1myview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 作者：author
 * 时间：2017/10/25:13:46
 * 说明：
 */

public class CanvasPathaddRect extends CanvasPathView {

    public CanvasPathaddRect(Context context) {
        super(context,null);
    }

    public CanvasPathaddRect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public CanvasPathaddRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override  //确定view的大小
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeigh = h;

    }

    @Override
    protected void startDraw(Canvas canvas) {
        Path path = new Path();
        path.addRect(-200,-200,200,200, Path.Direction.CW);

        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setTextSize(30);
        canvas.drawTextOnPath("大明湖 明湖大  \n" +
                "大明湖里有荷花  \n" +
                "荷花上面有蛤蟆  \n" +
                "一戳一蹦达",path,-200,-200,paint);
        canvas.drawPath(path,paint);
    }
}
