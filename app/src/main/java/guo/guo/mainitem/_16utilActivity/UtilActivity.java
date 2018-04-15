package guo.guo.mainitem._16utilActivity;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.MainActivity;
import guo.guo.MyApplication;
import guo.guo.R;
import guo.guo.mainitem.OnRecyclerViewItemClick;

public class UtilActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);
        ButterKnife.bind(this);

        List<String> mData = new ArrayList<>(Arrays.asList(new String[]{"0_重启应用","1_测试"}));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);

        UtilAdapter utilAdapter = new UtilAdapter(this, mData);
        mRecyclerview.setAdapter(utilAdapter);
        utilAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(UtilActivity.this, "test "+position, Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        restartApp();
                        break;
                }

            }

        });


    }

    /**
     *
     */
    private void restartApp() {
        Intent intentActivity = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity
                (this,(int) System.currentTimeMillis(),intentActivity,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,System.currentTimeMillis() + 3000,pendingIntent);

        MyApplication.getInstance().clearAllActivity();
//        ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
//        am.killBackgroundProcesses(getPackageName());

    }

    /**
     * @param servicename
     * @param context
     * @return
     */
    public boolean isServiceRunning(String servicename,Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo>  infos = am.getRunningServices(100);
        for(ActivityManager.RunningServiceInfo info: infos){
            if(info.service.getClassName().contains(servicename)){
                return true;
            }
        }
        return false;
    }
}
