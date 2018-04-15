/*
 * CameraPublishActivity.java
 * CameraPublishActivity
 * 
 * Github: https://github.com/daniulive/SmarterStreaming
 * 
 * Created by DaniuLive on 2015/09/20.
 * Copyright © 2014~2016 DaniuLive. All rights reserved.
 */

package com.daniulive.smartpublisher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.daniulive.smartpublisher.SmartPublisherJni.WATERMARK;
import com.eventhandle.SmartEventCallback;
import com.voiceengine.NTAudioRecord;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SuppressWarnings("deprecation")
public class CameraPublishActivity extends Activity implements Callback, PreviewCallback {
    private static String TAG = "SmartPublisher";

    NTAudioRecord audioRecord_ = null;    //for audio capture

    private SmartPublisherJni libPublisher = null;

    private int pushType = 0;

    private int watemarkType = 0;

    /* 推流分辨率选择
     * 0: 640*480
     * 1: 320*240
     * 2: 176*144
     * 3: 1280*720
     * */
    private Spinner resolutionSelector;


    private int sw_video_encoder_profile = 1;    //default with baseline profile

    private ImageView imgSwitchCamera;
    private Button btnStartStop;//推流的按鍵
    private SurfaceView mSurfaceView = null;
    private SurfaceHolder mSurfaceHolder = null;

    private Camera mCamera = null;
    private AutoFocusCallback myAutoFocusCallback = null;

    private boolean mPreviewRunning = false;

    private boolean isStart = false;

    private boolean isPushing = false;
    private boolean isRecording = false;

    final private String logoPath = "/sdcard/daniulivelogo.png";
    private boolean isWritelogoFileSuccess = false;

    private String publishURL;
    final private String baseURL = "rtmp://player.daniulive.com:1935/hls/stream";
    private String inputPushURL = "";

    private String printText = "URL:";
    private String txt = "当前状态";

    private static final int FRONT = 1;        //前置摄像头标记
    private static final int BACK = 2;        //后置摄像头标记
    private int currentCameraType = BACK;    //当前打开的摄像头标记
    private static final int PORTRAIT = 1;    //竖屏
    private static final int LANDSCAPE = 2;    //横屏
    private int currentOrigentation = PORTRAIT;
    private int curCameraIndex = -1;

    private int videoWidth = 640;
    private int videoHight = 480;

    private int frameCount = 0;

    private String recDir = "/sdcard/daniulive/rec";    //for recorder path

    private boolean is_need_local_recorder = false;        // do not enable recorder in default

    private boolean is_noise_suppression = true;

    private boolean is_agc = false;

    private boolean is_speex = false;



    private int sw_video_encoder_speed = 6;

    private boolean is_hardware_encoder = false;

    private Context myContext;

    private String imageSavePath;

    static {
        System.loadLibrary("SmartPublisher");
    }


    private byte[] ReadAssetFileDataToByte(InputStream in) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int c = 0;

        while ((c = in.read()) != -1) {
            bytestream.write(c);
        }

        byte bytedata[] = bytestream.toByteArray();
        bytestream.close();
        return bytedata;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate..");

        super.onCreate(savedInstanceState);
        SaveInputUrl("rtmp://218.56.135.214/dj_live/test?0");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);    //屏幕常亮

        setContentView(R.layout.activity_main2);

        myContext = this.getApplicationContext();

        //设置快照路径(具体路径可自行设置)
        File storageDir = getOwnCacheDirectory(myContext, "daniuimage");//创建保存的路径
        imageSavePath = storageDir.getPath();
        Log.i(TAG, "快照存储路径: " + imageSavePath);

        try {

            InputStream logo_input_stream = getClass().getResourceAsStream(
                    "/assets/logo.png");

            byte[] logo_data = ReadAssetFileDataToByte(logo_input_stream);

            if (logo_data != null) {
                try {
                    FileOutputStream out = new FileOutputStream(logoPath);
                    out.write(logo_data);
                    out.close();
                    isWritelogoFileSuccess = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "write logo file to /sdcard/ failed");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "write logo file to /sdcard/ failed");
        }

        //push type, audio/video/audio&video
        final String[] types = new String[]{"音视频", "纯音频", "纯视频"};
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, types);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        final String[] watermarks = new String[]{"图片水印", "全部水印", "文字水印", "不加水印"};

        ArrayAdapter<String> adapterWatermark = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, watermarks);

        adapterWatermark.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        resolutionSelector = (Spinner) findViewById(R.id.resolutionSelctor);
        final String[] resolutionSel = new String[]{"高分辨率", "中分辨率", "低分辨率", "超高分辨率"};
        ArrayAdapter<String> adapterResolution = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, resolutionSel);
        adapterResolution.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resolutionSelector.setAdapter(adapterResolution);

        resolutionSelector.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (isStart || isPushing || isRecording) {
                    Log.e(TAG, "Could not switch resolution during publishing..");
                    return;
                }

                Log.i(TAG, "[推送分辨率]Currently choosing: " + resolutionSel[position]);

                SwitchResolution(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnStartStop = (Button) findViewById(R.id.button_start_stop);
        btnStartStop.setOnClickListener(new ButtonStartListener());


        imgSwitchCamera = (ImageView) findViewById(R.id.button_switchCamera);
        imgSwitchCamera.setOnClickListener(new SwitchCameraListener());

        mSurfaceView = (SurfaceView) this.findViewById(R.id.surface);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //自动聚焦变量回调       
        myAutoFocusCallback = new AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                if (success)//success表示对焦成功
                {
                    Log.i(TAG, "onAutoFocus succeed...");
                } else {
                    Log.i(TAG, "onAutoFocus failed...");
                }
            }
        };

        libPublisher = new SmartPublisherJni();
    }

    class SwitchCameraListener implements OnClickListener {
        public void onClick(View v) {
            Log.i(TAG, "Switch camera..");
            try {
                switchCamera();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    void SwitchResolution(int position) {
        Log.i(TAG, "Current Resolution position: " + position);

        switch (position) {
            case 0:
                videoWidth = 640;
                videoHight = 480;
                break;
            case 1:
                videoWidth = 320;
                videoHight = 240;
                break;
            case 2:
                videoWidth = 176;
                videoHight = 144;
                break;
            case 3:
                videoWidth = 1280;
                videoHight = 720;
                break;
            default:
                videoWidth = 640;
                videoHight = 480;
        }

        mCamera.stopPreview();
        initCamera(mSurfaceHolder);
    }

    void CheckInitAudioRecorder() {
        if (audioRecord_ == null) {
            audioRecord_ = new NTAudioRecord(this, 1);
        }

        if (audioRecord_ != null) {
            Log.i(TAG, "onCreate, call executeAudioRecordMethod..");
            // auido_ret: 0 ok, other failed
            int auido_ret = audioRecord_.executeAudioRecordMethod();
            Log.i(TAG, "onCreate, call executeAudioRecordMethod.. auido_ret=" + auido_ret);
        }
    }

    //Configure recorder related function.
    void ConfigRecorderFuntion(boolean isNeedLocalRecorder) {
        if (libPublisher != null) {
            if (isNeedLocalRecorder) {
                if (recDir != null && !recDir.isEmpty()) {
                    int ret = libPublisher.SmartPublisherCreateFileDirectory(recDir);
                    if (0 == ret) {
                        if (0 != libPublisher.SmartPublisherSetRecorderDirectory(recDir)) {
                            Log.e(TAG, "Set recoder dir failed , path:" + recDir);
                            return;
                        }

                        if (0 != libPublisher.SmartPublisherSetRecorder(1)) {
                            Log.e(TAG, "SmartPublisherSetRecoder failed.");
                            return;
                        }

                        if (0 != libPublisher.SmartPublisherSetRecorderFileMaxSize(200)) {
                            Log.e(TAG, "SmartPublisherSetRecoderFileMaxSize failed.");
                            return;
                        }

                    } else {
                        Log.e(TAG, "Create recoder dir failed, path:" + recDir);
                    }
                }
            } else {
                if (0 != libPublisher.SmartPublisherSetRecorder(0)) {
                    Log.e(TAG, "SmartPublisherSetRecoder failed.");
                    return;
                }
            }
        }
    }


    class EventHande implements SmartEventCallback {
        @Override
        public void onCallback(int code, long param1, long param2, String param3, String param4, Object param5) {
            switch (code) {
                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_STARTED:
                    txt = "开始。。";
                    break;
                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_CONNECTING:
                    txt = "连接中。。";
                    break;
                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_CONNECTION_FAILED:
                    txt = "连接失败。。";
                    break;
                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_CONNECTED:
                    txt = "连接成功。。";
                    break;
                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_DISCONNECTED:
                    txt = "连接断开。。";
                    break;
                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_STOP:
                    txt = "关闭。。";
                    break;
                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_RECORDER_START_NEW_FILE:
                    Log.i(TAG, "开始一个新的录像文件 : " + param3);
                    txt = "开始一个新的录像文件。。";
                    break;
                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_ONE_RECORDER_FILE_FINISHED:
                    Log.i(TAG, "已生成一个录像文件 : " + param3);
                    txt = "已生成一个录像文件。。";
                    break;

                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_SEND_DELAY:
                    Log.i(TAG, "发送时延: " + param1 + " 帧数:" + param2);
                    txt = "收到发送时延..";
                    break;

                case EVENTID.EVENT_DANIULIVE_ERC_PUBLISHER_CAPTURE_IMAGE:
                    Log.i(TAG, "快照: " + param1 + " 路径：" + param3);

                    if (param1 == 0) {
                        txt = "截取快照成功。.";
                    } else {
                        txt = "截取快照失败。.";
                    }
                    break;
            }

            String str = "当前回调状态：" + txt;

            Log.i(TAG, str);

        }
    }

    private void SaveInputUrl(String url) {
        inputPushURL = "";

        if (url == null)
            return;

        // rtmp://
        if (url.length() < 8) {
            Log.e(TAG, "Input publish url error:" + url);
            return;
        }

        if (!url.startsWith("rtmp://")) {
            Log.e(TAG, "Input publish url error:" + url);
            return;
        }

        inputPushURL = url;

        Log.i(TAG, "Input publish url:" + url);
    }


    class ButtonStartListener implements OnClickListener {
        public void onClick(View v) {
            if (isPushing || isRecording) {
                return;
            }

            if (isStart) {
                stop();

                return;
            }

            isStart = true;
            btnStartStop.setText(" 停止推流 ");
            Log.i(TAG, "onClick start..");

            if (libPublisher != null) {
                if (inputPushURL != null && inputPushURL.length() > 1) {
                    publishURL = inputPushURL;
                    Log.i(TAG, "start, input publish url:" + publishURL);
                } else {
                    publishURL = baseURL + String.valueOf((int) (System.currentTimeMillis() % 1000000));
                    Log.i(TAG, "start, generate random url:" + publishURL);

                }

                printText = "URL:" + publishURL;

                Log.i(TAG, printText);


                ConfigRecorderFuntion(is_need_local_recorder);

                Log.i(TAG, "videoWidth: " + videoWidth + " videoHight: " + videoHight + " pushType:" + pushType);

                int audio_opt = 1;
                int video_opt = 1;

                if (pushType == 1) {
                    video_opt = 0;
                } else if (pushType == 2) {
                    audio_opt = 0;
                }

                libPublisher.SmartPublisherInit(myContext, audio_opt, video_opt, videoWidth, videoHight);

                if (is_hardware_encoder) {
                    int hwHWKbps = setHardwareEncoderKbps(videoWidth, videoHight);

                    Log.i(TAG, "hwHWKbps: " + hwHWKbps);

                    int isSupportHWEncoder = libPublisher.SetSmartPublisherVideoHWEncoder(hwHWKbps);

                    if (isSupportHWEncoder == 0) {
                        Log.i(TAG, "Great, it supports hardware encoder!");
                    }
                }

                libPublisher.SetSmartPublisherEventCallback(new EventHande());

                //如果想和时间显示在同一行，请去掉'\n'
                String watermarkText = "大牛直播(daniulive)\n\n";

                String path = logoPath;

                if (watemarkType == 0) {
                    if (isWritelogoFileSuccess)
                        libPublisher.SmartPublisherSetPictureWatermark(path, WATERMARK.WATERMARK_POSITION_TOPRIGHT, 160, 160, 10, 10);
                } else if (watemarkType == 1) {
                    if (isWritelogoFileSuccess)
                        libPublisher.SmartPublisherSetPictureWatermark(path, WATERMARK.WATERMARK_POSITION_TOPRIGHT, 160, 160, 10, 10);

                    libPublisher.SmartPublisherSetTextWatermark(watermarkText, 1, WATERMARK.WATERMARK_FONTSIZE_BIG, WATERMARK.WATERMARK_POSITION_BOTTOMRIGHT, 10, 10);

                    //libPublisher.SmartPublisherSetTextWatermarkFontFileName("/system/fonts/DroidSansFallback.ttf");

                    //libPublisher.SmartPublisherSetTextWatermarkFontFileName("/sdcard/DroidSansFallback.ttf");
                } else if (watemarkType == 2) {
                    libPublisher.SmartPublisherSetTextWatermark(watermarkText, 1, WATERMARK.WATERMARK_FONTSIZE_BIG, WATERMARK.WATERMARK_POSITION_BOTTOMRIGHT, 10, 10);

                    //libPublisher.SmartPublisherSetTextWatermarkFontFileName("/system/fonts/DroidSansFallback.ttf");
                } else {
                    Log.i(TAG, "no watermark settings..");
                }
                //end


                if (!is_speex) {
                    libPublisher.SmartPublisherSetAudioCodecType(1);
                } else {
                    // set Speex encoder
                    libPublisher.SmartPublisherSetAudioCodecType(2);
                    libPublisher.SmartPublisherSetSpeexEncoderQuality(8);
                }

                libPublisher.SmartPublisherSetNoiseSuppression(is_noise_suppression ? 1 : 0);

                libPublisher.SmartPublisherSetAGC(is_agc ? 1 : 0);

                //libPublisher.SmartPublisherSetClippingMode(0);

                libPublisher.SmartPublisherSetSWVideoEncoderProfile(sw_video_encoder_profile);

                libPublisher.SmartPublisherSetSWVideoEncoderSpeed(sw_video_encoder_speed);

                libPublisher.SmartPublisherSaveImageFlag(1);

                //libPublisher.SetRtmpPublishingType(0);


                //libPublisher.SmartPublisherSetGopInterval(40);

                //libPublisher.SmartPublisherSetFPS(15);

                //libPublisher.SmartPublisherSetSWVideoBitRate(600, 1200);
                // IF not set url or url is empty, it will not publish stream
                // if ( libPublisher.SmartPublisherSetURL("") != 0 )
                if (libPublisher.SmartPublisherSetURL(publishURL) != 0) {
                    Log.e(TAG, "Failed to set publish stream URL..");
                }

                int isStarted = libPublisher.SmartPublisherStart();
                if (isStarted != 0) {
                    Log.e(TAG, "Failed to publish stream..");
                } else {

                }
            }

            if (pushType == 0 || pushType == 1) {
                CheckInitAudioRecorder();    //enable pure video publisher..
            }
        }
    };



    private void stop() {
        Log.i(TAG, "onClick stop..");
        StopPublish();
        isStart = false;
        btnStartStop.setText(" 开始推流 ");
    }

    private void stopPush() {
        if (!isRecording) {
            if (audioRecord_ != null) {
                Log.i(TAG, "stopPush, call audioRecord_.StopRecording..");
                audioRecord_.StopRecording();
                audioRecord_ = null;
            }
        }

        if (libPublisher != null) {
            libPublisher.SmartPublisherStopPublisher();
        }
    }

    private void stopRecorder() {
        if (!isPushing) {
            if (audioRecord_ != null) {
                Log.i(TAG, "stopRecorder, call audioRecord_.StopRecording..");
                audioRecord_.StopRecording();
                audioRecord_ = null;
            }
        }

        if (libPublisher != null) {
            libPublisher.SmartPublisherStopRecorder();
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "activity destory!");

        if (isStart) {
            isStart = false;
            StopPublish();
            Log.i(TAG, "onDestroy StopPublish");
        }

        if (isPushing || isRecording) {
            if (audioRecord_ != null) {
                Log.i(TAG, "surfaceDestroyed, call StopRecording..");
                audioRecord_.StopRecording();
                audioRecord_ = null;
            }

            stopPush();
            stopRecorder();

            isPushing = false;
            isRecording = false;
        }

        super.onDestroy();
        finish();
        System.exit(0);
    }

    private void SetCameraFPS(Camera.Parameters parameters) {
        if (parameters == null)
            return;

        int[] findRange = null;

        int defFPS = 20 * 1000;

        List<int[]> fpsList = parameters.getSupportedPreviewFpsRange();
        if (fpsList != null && fpsList.size() > 0) {
            for (int i = 0; i < fpsList.size(); ++i) {
                int[] range = fpsList.get(i);
                if (range != null
                        && Camera.Parameters.PREVIEW_FPS_MIN_INDEX < range.length
                        && Camera.Parameters.PREVIEW_FPS_MAX_INDEX < range.length) {
                    Log.i(TAG, "Camera index:" + i + " support min fps:" + range[Camera.Parameters.PREVIEW_FPS_MIN_INDEX]);

                    Log.i(TAG, "Camera index:" + i + " support max fps:" + range[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]);

                    if (findRange == null) {
                        if (defFPS <= range[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]) {
                            findRange = range;

                            Log.i(TAG, "Camera found appropriate fps, min fps:" + range[Camera.Parameters.PREVIEW_FPS_MIN_INDEX]
                                    + " ,max fps:" + range[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]);
                        }
                    }
                }
            }
        }

        if (findRange != null) {
            parameters.setPreviewFpsRange(findRange[Camera.Parameters.PREVIEW_FPS_MIN_INDEX], findRange[Camera.Parameters.PREVIEW_FPS_MAX_INDEX]);
        }
    }

    /*it will call when surfaceChanged*/
    private void initCamera(SurfaceHolder holder) {
        Log.i(TAG, "initCamera..");

        if (mPreviewRunning)
            mCamera.stopPreview();

        Camera.Parameters parameters;
        try {
            parameters = mCamera.getParameters();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        parameters.setPreviewSize(videoWidth, videoHight);
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP);

        SetCameraFPS(parameters);

        setCameraDisplayOrientation(this, curCameraIndex, mCamera);

        mCamera.setParameters(parameters);

        int bufferSize = (((videoWidth | 0xf) + 1) * videoHight * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat())) / 8;

        mCamera.addCallbackBuffer(new byte[bufferSize]);

        mCamera.setPreviewCallbackWithBuffer(this);
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            if (null != mCamera) {
                mCamera.release();
                mCamera = null;
            }
            ex.printStackTrace();
        }
        mCamera.startPreview();
        mCamera.autoFocus(myAutoFocusCallback);
        mPreviewRunning = true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated..");
        try {

            int CammeraIndex = findBackCamera();
            Log.i(TAG, "BackCamera: " + CammeraIndex);

            if (CammeraIndex == -1) {
                CammeraIndex = findFrontCamera();
                currentCameraType = FRONT;
                imgSwitchCamera.setEnabled(false);
                if (CammeraIndex == -1) {
                    Log.i(TAG, "NO camera!!");
                    return;
                }
            } else {
                currentCameraType = BACK;
            }

            if (mCamera == null) {
                mCamera = openCamera(currentCameraType);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged..");
        initCamera(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.i(TAG, "Surface Destroyed");
    }

    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
            Log.i(TAG, "onConfigurationChanged, start:" + isStart);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (!isStart && !isPushing && !isRecording) {
                    currentOrigentation = LANDSCAPE;
                }
            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (!isStart && !isPushing && !isRecording) {
                    currentOrigentation = PORTRAIT;
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        frameCount++;
        if (frameCount % 3000 == 0) {
            Log.i("OnPre", "gc+");
            System.gc();
            Log.i("OnPre", "gc-");
        }

        if (data == null) {
            Parameters params = camera.getParameters();
            Size size = params.getPreviewSize();
            int bufferSize = (((size.width | 0x1f) + 1) * size.height * ImageFormat.getBitsPerPixel(params.getPreviewFormat())) / 8;
            camera.addCallbackBuffer(new byte[bufferSize]);
        } else {
            if (isStart || isPushing || isRecording) {
                libPublisher.SmartPublisherOnCaptureVideoData(data, data.length, currentCameraType, currentOrigentation);
            }

            camera.addCallbackBuffer(data);
        }
    }

    @SuppressLint("NewApi")
    private Camera openCamera(int type) {
        int frontIndex = -1;
        int backIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();
        Log.i(TAG, "cameraCount: " + cameraCount);

        CameraInfo info = new CameraInfo();
        for (int cameraIndex = 0; cameraIndex < cameraCount; cameraIndex++) {
            Camera.getCameraInfo(cameraIndex, info);

            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                frontIndex = cameraIndex;
            } else if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
                backIndex = cameraIndex;
            }
        }

        currentCameraType = type;
        if (type == FRONT && frontIndex != -1) {
            curCameraIndex = frontIndex;
            return Camera.open(frontIndex);
        } else if (type == BACK && backIndex != -1) {
            curCameraIndex = backIndex;
            return Camera.open(backIndex);
        }
        return null;
    }

    private void switchCamera() throws IOException {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        if (currentCameraType == FRONT) {
            mCamera = openCamera(BACK);
        } else if (currentCameraType == BACK) {
            mCamera = openCamera(FRONT);
        }

        initCamera(mSurfaceHolder);
    }

    private void StopPublish() {
        if (audioRecord_ != null) {
            Log.i(TAG, "surfaceDestroyed, call StopRecording..");
            audioRecord_.StopRecording();
            audioRecord_ = null;
        }

        if (libPublisher != null) {
            libPublisher.SmartPublisherStop();
        }
    }

    //Check if it has front camera
    private int findFrontCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return camIdx;
            }
        }
        return -1;
    }

    //Check if it has back camera
    private int findBackCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return camIdx;
            }
        }
        return -1;
    }

    private void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            // back-facing  
            result = (info.orientation - degrees + 360) % 360;
        }

        Log.i(TAG, "curDegree: " + result);

        camera.setDisplayOrientation(result);
    }

    private int setHardwareEncoderKbps(int width, int height) {
        int hwEncoderKpbs = 0;

        switch (width) {
            case 176:
                hwEncoderKpbs = 300;
                break;
            case 320:
                hwEncoderKpbs = 500;
                break;
            case 640:
                hwEncoderKpbs = 1000;
                break;
            case 1280:
                hwEncoderKpbs = 1700;
                break;
            default:
                hwEncoderKpbs = 1000;
        }

        return hwEncoderKpbs;
    }

    /**
     * 根据目录创建文件夹
     *
     * @param context
     * @param cacheDir
     * @return
     */
    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        //判断sd卡正常挂载并且拥有权限的时候创建文件
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
            Log.i(TAG, "appCacheDir: " + appCacheDir);
        }
        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    /**
     * 检查是否有权限
     *
     * @param context
     * @return
     */
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }

}