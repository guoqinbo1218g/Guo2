package guo.guo.mainitem._8callphone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import guo.guo.R;

public class CallphoneActivity extends AppCompatActivity {
    public static final int CALLPHONERESULT = 0;
    @BindView(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.et_interval)
    EditText etInterval;
    @BindView(R.id.btn_begin)
    Button btnBegin;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callphone);
        ButterKnife.bind(this);
        phoneNumber = etPhoneNumber.getText().toString();
        phoneNumber = "17674754295";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALLPHONERESULT);
        } else {
            callPhone(phoneNumber);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CALLPHONERESULT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone(phoneNumber);
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void callPhone(final String phoneNumber) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (ActivityCompat.checkSelfPermission(CallphoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 5000, 60000);

    }

    @OnClick(R.id.btn_begin)
    public void onViewClicked() {
    }
}
