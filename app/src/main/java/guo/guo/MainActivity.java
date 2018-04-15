package guo.guo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.PixelFormat;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.mainitem._0filter.Main_Filter3;
import guo.guo.mainitem._10webview.WebViewActivity;
import guo.guo.mainitem._11download.DownloadActivity;
import guo.guo.mainitem._13CountDownTimer.CountDownTimerActivity;
import guo.guo.mainitem._14PreferenceScreen.MyPreferenceActivity;
import guo.guo.mainitem._15WebviewCamera.WebviewCamera;
import guo.guo.mainitem._16utilActivity.UtilActivity;
import guo.guo.mainitem._1myview.MyViewMainActivity;
import guo.guo.mainitem._2rxjava2.MainRxjava;
import guo.guo.mainitem._3xuanfuqiu.FloatActivity;
import guo.guo.mainitem._4recycleview.MainRecycleview;
import guo.guo.mainitem._5dialogfragment.MainDialogFragment;
import guo.guo.mainitem._6camera.MainCamera;
import guo.guo.mainitem._7dualsim.DualSIMActivity;
import guo.guo.mainitem._8callphone.CallphoneActivity;
import guo.guo.mainitem._9camera2.Camera2Activity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // Action 添加Shortcut
    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String TAG = "MainActivity";
    @BindView(R.id.lv_main)
    ListView lvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        String[] data = new String[]{"0数据过滤器filter", "1自定义view", "2Rxjava2", "3悬浮球", "4recycleview和toolbar",
                "5DialogFragment", "6camera相关", "7判断是否是双卡测试", "8轰炸电话(其实是学习ConstraintLayout)",
                "9使用camera2的api", "10webview", "11rxdowanload", "12databinding", "13定时任务CountDownTimer",
                "14 PreferenceScreen","15 webview调用camera","16 类似工具类"
        };


        ArrayList<String> arraydata = new ArrayList<>(Arrays.asList(data));
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraydata);
        lvMain.setAdapter(arrayAdapter);
        lvMain.setOnItemClickListener(this);
        //test();  //使用windowmanage 弹窗
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            initDynamicShortcuts();
        }


        Observable.create((ObservableEmitter<String> e) -> {e.onNext("test");}).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        });


        //addShortCut(); //你大爷的无效
//        startService(new Intent(this, MyService.class));
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState:        ");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(MainActivity.this, Main_Filter3.class);
                break;
            case 1:
                intent = new Intent(MainActivity.this, MyViewMainActivity.class);
                break;
            case 2:
                intent = new Intent(MainActivity.this, MainRxjava.class);
                break;
            case 3:
                intent = new Intent(MainActivity.this, FloatActivity.class);
                break;
            case 4:
                intent = new Intent(MainActivity.this, MainRecycleview.class);
                break;
            case 5:
                intent = new Intent(MainActivity.this, MainDialogFragment.class);
                break;
            case 6:
                intent = new Intent(MainActivity.this, MainCamera.class);
                break;
            case 7:
                intent = new Intent(MainActivity.this, DualSIMActivity.class);
                break;
            case 8:
                intent = new Intent(MainActivity.this, CallphoneActivity.class);
                break;
            case 9:
                intent = new Intent(MainActivity.this, Camera2Activity.class);
                break;
            case 10:
                intent = new Intent(MainActivity.this, WebViewActivity.class);
                break;
            case 11:
                intent = new Intent(MainActivity.this, DownloadActivity.class);
                break;
            case 12:
                break;
            case 13:
                intent = new Intent(MainActivity.this, CountDownTimerActivity.class);
                break;
            case 14:
                intent = new Intent(MainActivity.this, MyPreferenceActivity.class);
                break;
            case 15:
                intent = new Intent(MainActivity.this, WebviewCamera.class);
                break;
            case 16:
                intent = new Intent(MainActivity.this, UtilActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void initDynamicShortcuts() {//创建动态shortcuts
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        ArrayList<ShortcutInfo> shortcutInfoList = new ArrayList<>();
        //创建动态快捷方式的详细信息
        Intent intent1 = new Intent(MainActivity.this, CountDownTimerActivity.class);
        intent1.setAction("dynmic1");
        ShortcutInfo ShortcutInfo1 = new ShortcutInfo.Builder(this, "dynamic_one")
                .setShortLabel("dynmic shortLabel")
                .setIcon(Icon.createWithResource(this, R.drawable.googlelogo))
                .setRank(0)
                .setLongLabel("dynmic longLabel")
                .setIntent(intent1)
                .build();

        Intent intent2 = new Intent(MainActivity.this, WebViewActivity.class);
        intent2.setAction("dynmic2");
        ShortcutInfo ShortcutInfo2 = new ShortcutInfo.Builder(this, "dynamic_two")
                .setShortLabel("dynmic shortLabel")
                .setIcon(Icon.createWithResource(this, R.drawable.yoona1))
                .setRank(1)
                .setLongLabel("dynmic longLabel")
                .setIntent(intent2)
                .build();
        shortcutInfoList.add(ShortcutInfo1);
        shortcutInfoList.add(ShortcutInfo2);

        shortcutManager.setDynamicShortcuts(shortcutInfoList);
    }
    void test() { // windowManage 弹出对话框
        final WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams para = new WindowManager.LayoutParams();
        para.height = WindowManager.LayoutParams.WRAP_CONTENT;//WRAP_CONTENT  原本是-2
        para.width = WindowManager.LayoutParams.WRAP_CONTENT;//WRAP_CONTENT
        para.format = PixelFormat.TRANSLUCENT;

        para.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        para.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        para.gravity = Gravity.CENTER;

        para.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        final View contentView = View.inflate(this, R.layout.windowmanage, null);
        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_content);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        final Button btnConfirm = (Button) contentView.findViewById(R.id.btn_confirm);
        final Button btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);

        String confName = "guo";
        if (!TextUtils.isEmpty(confName) && confName.contains("的会议")) {
            confName = "收到 " + confName;
        } else if (!TextUtils.isEmpty(confName)) {
            confName = "收到 " + confName + " 的会议";
        }
        tvTitle.setText(confName);
        tvContent.setText("请点击下面的按键来处理会议");
        btnConfirm.requestFocus();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了取消按键", Toast.LENGTH_SHORT).show();
                wm.removeView(contentView);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了确定按键", Toast.LENGTH_SHORT).show();
                wm.removeView(contentView);
            }
        });
        wm.addView(contentView, para);
    }
    private void addShortCut() {//添加桌面快捷方式
        Toast.makeText(this, "添加 快捷方式", Toast.LENGTH_SHORT).show();
        Intent jumpIntent = new Intent(Intent.ACTION_MAIN);
        jumpIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        jumpIntent.setClass(MainActivity.this, MainActivity.class);
        Intent shortCutIntent = new Intent(ACTION_ADD_SHORTCUT);
        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,"JAVA");
        shortCutIntent.putExtra("duplicate", false);
        shortCutIntent.putExtra(Intent.EXTRA_INTENT,jumpIntent);
        Parcelable iconRes = Intent.ShortcutIconResource.fromContext(MainActivity.this, R.drawable.girl);//设置快捷图标的资源
        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,iconRes);
        sendBroadcast(shortCutIntent);
    }
}
