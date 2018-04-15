package guo.guo.mainitem._2rxjava2.rxjavaItem.Rx_Retrofit2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.R;
import guo.guo.mainitem._2rxjava2.MainRxjava;
import guo.guo.mainitem._2rxjava2.rxjavaItem.other.RetrofitFactory;
import guo.guo.other.MsgBean;
import guo.guo.other.URLBean;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Rx_Retrofit2 extends AppCompatActivity {
    private static final String TAG = "Rx_Retrofit2";
    String URL = "http://oa.pinganlinshu.com/";
    @BindView(R.id.tv_rxret)
    TextView tvRxret;
    private RetService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxretrofit2);
        ButterKnife.bind(this);
        service = RetrofitFactory.getRetrofitInstance(URL)
                .create(RetService.class);
//        getMessage();
//        getMessage1("TV_2017040601");
//        getMessage2("TV_2017040601");
//        testFlowable();
        Observable.intervalRange(1,60,0,1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .map((number)->{
                    if (number <60) return "倒计时"+(60-number);
                    else return "倒计时结束";
                })
                .subscribe( s -> tvRxret.setText(s) );

    }
    void getMessage3(String account) {
        service.getPath(account).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(urlBeans -> {
                    if (urlBeans != null)
                        return service.getMessage(account);
                    else
                        return null;
                })
                .observeOn(Schedulers.newThread())
                .doOnError(throwable -> {
                    Log.e(TAG, "doOnError的accept: " );
                })
                .subscribe(msgBean -> {
                    tvRxret.setText("" + msgBean.get我的首页().get(0).getAddress());
                },throwable -> {
                    Log.e(TAG, "subscribe中Throwable的accept: 线程是" + Thread.currentThread().getName());
                    Toast.makeText(Rx_Retrofit2.this, "第一次获取信息失败", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Rx_Retrofit2.this, MainRxjava.class));
                    finish();
                });
    }


    /**
     * @param account
     *   使用了flatmap(concatmap) 变换的方式
     */
    private void getMessage2(String account) {
        service.getPath(account)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .concatMap(new Function<List<URLBean>, ObservableSource<MsgBean>>() {//返回 Observable<MsgBean>
                    @Override
                    public ObservableSource<MsgBean> apply(List<URLBean> urlBeans) throws Exception {
                        if (urlBeans != null)
                            return service.getMessage(account);
                        else
                            return null;
                    }
                })
                .doOnError(new Consumer<Throwable>() {//并没发现又什么用
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "doOnError的accept: "+new Throwable("doonerror 的异常"));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求登录的结果
                .subscribe(new Consumer<MsgBean>() {
                    @Override
                    public void accept(MsgBean msgBean) throws Exception {
                        tvRxret.setText("" + msgBean.get我的首页().get(0).getAddress());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "subscribe中Throwable的accept: 线程是" + Thread.currentThread().getName());
                        Log.e(TAG, "subscribe中Throwable的accept: 异常" + throwable.getMessage());
                        Toast.makeText(Rx_Retrofit2.this, "第一次获取信息失败", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Rx_Retrofit2.this, MainRxjava.class));
                        finish();
                    }
                });
    }

    /**
     * @param account
     *    使用map 变换   map变换功能有限,function()的第二个参数不适合放有泛型的
     */
    private void getMessage1(String account) {
        service.getPath(account)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<List<URLBean>, Observable<MsgBean>>() {//返回 Observable<Observable<MsgBean>>
                    @Override
                    public Observable<MsgBean> apply(List<URLBean> urlBeans) throws Exception {
                        return service.getMessage(account);
                    }
                })
                .subscribe(new Consumer<Observable<MsgBean>>() {
                    @Override
                    public void accept(Observable<MsgBean> msgBeanObservable) throws Exception {

                    }
                });

    }

    private void getMessage() {

//        MultipartBody
//        RequestBody   abstract  RequestBody.creat();

        service.getPath("TV_2017040606")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<URLBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(List<URLBean> urlBeans) {
                        tvRxret.setText("" + urlBeans.get(0).getRemark());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });
    }
}
