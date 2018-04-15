package guo.guo.mainitem;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import guo.guo.R;

public class _14animActivity extends AppCompatActivity {
    @BindView(R.id.ivAnim)
    ImageView imageView;

    @BindView(R.id.tv_anim)
    TextView tvAnim;
    @BindView(R.id.btnAnim1)
    Button btnAnim1;
    @BindView(R.id.btnAnim2)
    Button btnAnim2;
    @BindView(R.id.btnAnim3)
    Button btnAnim3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_14anim);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btnAnim1, R.id.btnAnim2, R.id.btnAnim3})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnAnim1:
                textAnim1();
                break;
            case R.id.btnAnim2:
                break;
            case R.id.btnAnim3:
                break;
        }
    }

    /**
       AnimatorSet.Builder中包括以下四个方法：
            after(Animator anim)   将现有动画插入到传入的动画之后执行
            after(long delay)   将现有动画延迟指定毫秒后执行
            before(Animator anim)   将现有动画插入到传入的动画之前执行
            with(Animator anim)   将现有动画和传入的动画同时执行
     */
    private void textAnim1() {

        ObjectAnimator alphaAnimator =
                ObjectAnimator.ofFloat(tvAnim,"alpha",0f,1.0f);
        ObjectAnimator rotateAnimator =
                ObjectAnimator.ofFloat(tvAnim,"rotation",0,360);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnimator).with(rotateAnimator);
        animatorSet.setDuration(1000);
        animatorSet.start();

    }
    private void textAnim2() {
        ObjectAnimator alphaAnimator =
                ObjectAnimator.ofFloat(tvAnim,"alpha",0f,1.0f)
                .setDuration(1000);
        ObjectAnimator rotateAnimator =
                ObjectAnimator.ofFloat(tvAnim,"rotation",0,360);

        alphaAnimator.start();
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

    }
}
