package guo.guo.mainitem._2rxjava2.rxjavaItem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import guo.guo.R;
import guo.guo.other.URLBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *   测试地址 http://oa.pinganlinshu.com/Platform/NewWeb/ashx/NewWeb.ashx?method=getServerInfoByUser&userId=TV_2017040606
 */
public class Rx_ok3 extends AppCompatActivity {
    public static final String URL ="http://oa.pinganlinshu.com/Platform/NewWeb/ashx/NewWeb.ashx?method=getServerInfoByUser&userId=TV_2017040606";

    private static final String TAG = "Rx_ok3";
    private List<URLBean> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxok3);
        mDatas = new ArrayList<>();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .get()
                .url(URL)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();
                mDatas = gson.fromJson(response.body().string(),new TypeToken<ArrayList<URLBean>>(){}.getType());
//                Log.e(TAG, "onResponse: "+response.body().string());
            }
        });
    }
}
