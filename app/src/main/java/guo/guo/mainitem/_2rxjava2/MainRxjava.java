package guo.guo.mainitem._2rxjava2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.R;
import guo.guo.mainitem._2rxjava2.rxjavaItem.Rx0_doOnNext;
import guo.guo.mainitem._2rxjava2.rxjavaItem.Rx_Retrofit2.Rx_Retrofit2;
import guo.guo.mainitem._2rxjava2.rxjavaItem.Rx_ok3;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;

/**
 * doOnNext()方法的使用
 */
public class MainRxjava extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @BindView(R.id.lv_rxjavamain)
    ListView lvRxjavamain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rxjava);
        ButterKnife.bind(this);


        List<String> data = new ArrayList<>(Arrays.asList("0测试doOnNext","1.okhttp3回顾","2.Retrofit使用"));
        lvRxjavamain.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
        lvRxjavamain.setOnItemClickListener(this);
    }
    void test(){
//  package io.reactivex;

//        public interface ObservableSource<T> {  void subscribe(@NonNull Observer<? super T> observer);

//        public abstract class Observable<T> implements ObservableSource<T>{
//        public interface Observer<T> {


//        public abstract class Flowable<T> implements Publisher<T> {

//        public interface ObservableOnSubscribe<T> {      void subscribe( ObservableEmitter<T> e)

//  package org.reactivestreams;
//       public interface Subscriber<T> {
//        public interface Publisher<T> {  void subscribe(Subscriber<? super T> s);

//        Subscriber
//        Publisher



//        class CompositeDisposable implements Disposable


        ListCompositeDisposable listCompositeDisposable = new ListCompositeDisposable();
        CompositeDisposable compositeDisposable = new CompositeDisposable();


        Flowable.range(0,5).onBackpressureBuffer().subscribe((s)->{});

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {

            }
        }, BackpressureStrategy.BUFFER).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(100);
            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        //支持背压的方式
        Observable observable = Observable.create(e -> {});
        observable.toFlowable(BackpressureStrategy.BUFFER);


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
//        Publisher
        Single single = Single.create(e -> {});
        single.toFlowable();


        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {

            }
        }).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {

            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position){
            case 0:
                intent = new Intent(MainRxjava.this, Rx0_doOnNext.class);
                break;
            case 1:
                intent = new Intent(MainRxjava.this, Rx_ok3.class);
                break;
            case 2:
                intent = new Intent(MainRxjava.this, Rx_Retrofit2.class);
                break;
        }
        startActivity(intent);
    }
}
