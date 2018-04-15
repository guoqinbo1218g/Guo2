package guo.guo.mainitem._11download;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.R;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 *         安装应用参考 https://www.jianshu.com/p/577816c3ce93
 *                      http://blog.csdn.net/czhpxl007/article/details/53781464
 */
public class DownloadActivity extends AppCompatActivity {
    private static final String TAG = "DownloadActivity";
    private static final int NOTIFICATION_ID = 472;

    @BindView(R.id.et_phone)
    TextInputEditText etPhone;
    @BindView(R.id.textinputlayout_phone)
    TextInputLayout textinputlayoutPhone;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.textinputlayout_password)
    TextInputLayout textinputlayoutPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private static final int PERMIAASION_WRITER = 1120;
    private String apkName = "tim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);

        init();
        dialog= new AlertDialog.Builder(this)
                .setTitle("检测到新版本下载")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission
                                (DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(DownloadActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMIAASION_WRITER);
                        }else {

                            File file = new File(Environment.getExternalStorageDirectory()+"/Download", apkName+".apk");
                            if (!file.exists())
                                startDownload();
                            else
                                startInstallAPK(apkName);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
    private AlertDialog dialog;
    @Override
    protected void onDestroy() {
        if (dialog != null)
            dialog.dismiss();
        super.onDestroy();
    }

    private Disposable disposable = null;

    void startDownload(){
        NotificationManager manager;
        NotificationCompat.Builder builder;
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        RemoteViews remoteView = new RemoteViews(this.getPackageName(),R.layout.notification_contentview);

        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_navigation_icon)
                .setProgress(100,0,false)
//                .setContent(remoteView)
                .setContentTitle("正在下载,请稍后");

        manager.notify(NOTIFICATION_ID,builder.build());

//        String url = "https://github.com/ssseasonnn/RxDownload/archive/rxdownload2_v1.1.1.zip";
        String url = "http://sqdd.myapp.com/myapp/qqteam/tim/down/tim.apk";
        RxDownload.getInstance().maxThread(1)
                .download(url,"tim.apk",null)////最后一个参数指定下载位置 ,默认为/storage/emulated/0/Download/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadStatus>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        disposable = d;
                    }
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull DownloadStatus downloadStatus) {
                        double percent = ((double)downloadStatus.getDownloadSize()/(double) downloadStatus.getTotalSize()) * 100;
//                        Log.e(TAG, "accept: 进度: onnext33333333: "+ );
                        builder.setProgress(100, (int)percent,false).setContentText("下载:"+downloadStatus.getPercent());
                        manager.notify(NOTIFICATION_ID,builder.build());

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Toast.makeText(DownloadActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                   public void onComplete() {
                        manager.cancel(NOTIFICATION_ID);
                        startInstallAPK("tim");
                        Toast.makeText(DownloadActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void startInstallAPK(String apkName) {
        File file = new File(Environment.getExternalStorageDirectory()+"/Download", apkName+".apk");
        if (Build.VERSION.SDK_INT < 24){
            Uri apkUri =Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(intent);
        }else {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(this, "guo.fileprovider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(intent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMIAASION_WRITER) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startDownload();
            else
                Toast.makeText(this, "没有权限无法下载", Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void init() {

        String password = textinputlayoutPassword.getEditText().getText().toString();

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validPhone();
            }
        });
    }

    private void validPhone() {
        String phone = textinputlayoutPhone.getEditText().getText().toString();
        if (TextUtils.isEmpty(phone.trim()) || (phone.trim().length() != 11)){
            textinputlayoutPhone.setError("请输入正确的手机号");
            Toast.makeText(this, "手机号不正确", Toast.LENGTH_SHORT).show();
        }else{
            textinputlayoutPhone.setErrorEnabled(false);
            Toast.makeText(this, "可以登录了", Toast.LENGTH_SHORT).show();
        }
    }


}
