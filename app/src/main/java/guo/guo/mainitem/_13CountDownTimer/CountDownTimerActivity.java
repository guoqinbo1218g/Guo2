package guo.guo.mainitem._13CountDownTimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import guo.guo.R;

public class CountDownTimerActivity extends AppCompatActivity {
    private static final String TAG = "CountDownTimerActivity";
    @BindView(R.id.btn_click)
    Button btnClick;
    @BindView(R.id.chronometer)
    Chronometer chronometer;
    @BindView(R.id.testtext)
    TextView testtext;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ButterKnife.bind(this);

        start();
        chronometer.setFormat("Chronometer 计时: (%s)");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

    }

    @OnClick(R.id.btn_click)
    public void onViewClicked() {
        countDownTimer.cancel();
        start();
    }

    private void start() {
        Log.e(TAG, "start: ");
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, "onTick: " + millisUntilFinished / 1000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnClick.setText(millisUntilFinished / 1000 + "");
                    }
                });

            }

            @Override
            public void onFinish() {
                Log.e(TAG, "onFinish: 结束了");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnClick.setText("定时结束");
                    }
                });
            }
        }.start();
    }


}
