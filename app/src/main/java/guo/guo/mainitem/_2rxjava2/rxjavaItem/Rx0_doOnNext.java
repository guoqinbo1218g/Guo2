package guo.guo.mainitem._2rxjava2.rxjavaItem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import guo.guo.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 *      doOnNext()发生在 发送事件之后,可以做些其他的事
 *      打印结果
 *      E/Rx0_doOnNext: Observable:      hello前
        E/Rx0_doOnNext: doOnNext: hello
        E/Rx0_doOnNext: observer: hello
        E/Rx0_doOnNext: Observable:      hello后
        E/Rx0_doOnNext: doOnNext: world
 *
 */
public class Rx0_doOnNext extends AppCompatActivity {
    private static final String TAG = "Rx0_doOnNext";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx0_doonnext);

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "Observable:      hello前");
                emitter.onNext("hello");
                Log.e(TAG, "Observable:      hello后");
                emitter.onNext("world");
                emitter.onNext("hello");
                emitter.onNext("rxjava");
            }
        })
                .doOnNext((x) -> Log.e(TAG, "doOnNext: " + x))
                .doOnSubscribe((x) -> Log.e(TAG, "doOnSubscribe: " + x))
//        .subscribe((x) -> Log.e(TAG, "observer: " + x));
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "onNext: ");
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
