package guo.guo.mainitem._13CountDownTimer;

import android.os.CountDownTimer;

/**
 * 作者：author
 * 时间：2017/11/24:8:54
 * 说明：
 */

public class TimeBean {
    private String uuid;
    private CountDownTimer countDownTimer;
    private boolean uuidStatus = true;

    public TimeBean() {
    }

    public TimeBean(String uuid, CountDownTimer countDownTimer) {
        this.uuid = uuid;
        this.countDownTimer = countDownTimer;
    }
    public void cancelCount(){
        countDownTimer.cancel();
    }
    public void startCount(){
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                uuidStatus = false;
            }
        }.start();
    }


    public boolean isUuidStatus() {
        return uuidStatus;
    }

    public void setUuidStatus(boolean uuidStatus) {
        this.uuidStatus = uuidStatus;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }

    public void setCountDownTimer(CountDownTimer countDownTimer) {
        this.countDownTimer = countDownTimer;
    }


}
