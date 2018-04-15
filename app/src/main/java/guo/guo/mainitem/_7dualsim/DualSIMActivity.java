package guo.guo.mainitem._7dualsim;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 *  参考 https://stackoverflow.com/questions/14517338/android-check-whether-the-phone-is-dual-sim
 */
public class DualSIMActivity extends AppCompatActivity {
    public static final int PERMISSION_READ_PHONE_STATE = 1;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = new TextView(this);
        tv.setTextSize(25);
        tv.setTextColor(Color.RED);
        int id = View.generateViewId();
        tv.setId(id);
        setContentView(tv);
        //setContentView(R.layout.activity_dual_sim);
        //tv = (TextView) findViewById(R.id.tv);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},PERMISSION_READ_PHONE_STATE);
        }else {
            getDualSIMInfo();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_READ_PHONE_STATE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getDualSIMInfo();
            }
        }
    }

    private void getDualSIMInfo() {
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(this);

        String imeiSIM1 = telephonyInfo.getImsiSIM1();
        String imeiSIM2 = telephonyInfo.getImsiSIM2();

        boolean isSIM1Ready = telephonyInfo.isSIM1Ready();
        boolean isSIM2Ready = telephonyInfo.isSIM2Ready();

        boolean isDualSIM = telephonyInfo.isDualSIM();


        tv.setText(" IME1 : " + imeiSIM1 + "\n" +
                " IME2 : " + imeiSIM2 + "\n" +
                " IS DUAL SIM : " + isDualSIM + "\n" +
                " IS SIM1 READY : " + isSIM1Ready + "\n" +
                " IS SIM2 READY : " + isSIM2Ready + "\n"
                +" 卡一卡号 : " + telephonyInfo.getTelNumber() + "\n"   //
                +" 卡二卡号 : " + telephonyInfo.getTelNumber2() + "\n"  //用之前方式复制获取手机号有问题,有时间再看看

        );
    }

}
