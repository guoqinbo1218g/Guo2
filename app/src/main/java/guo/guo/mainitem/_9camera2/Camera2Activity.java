package guo.guo.mainitem._9camera2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import guo.guo.R;

public class Camera2Activity extends AppCompatActivity {
    public static final int CAMERAPERMISSSIONREQUESTCODE = 77;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERAPERMISSSIONREQUESTCODE);

        }else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERAPERMISSSIONREQUESTCODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startCamera();
                }
                break;
        }
    }

    private void startCamera() {
        CameraManager cameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId:cameraManager.getCameraIdList()) { //获取可用摄像头列表
                //获取相机的相关参数
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
