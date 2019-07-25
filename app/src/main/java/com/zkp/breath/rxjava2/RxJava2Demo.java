package com.zkp.breath.rxjava2;

import android.util.Log;

import com.blankj.utilcode.util.ThreadUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created b Zwp on 2019/7/18.
 */
public class RxJava2Demo {

    // 基本使用
    public void demo1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("demo1", "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("demo1", "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("demo1", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("demo1", "onComplete:");
            }
        });
    }

    // observeOn和subscribe
    public void demo2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.i("demo2", "subscribe: " + ThreadUtils.isMainThread());

                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("demo2", "onSubscribe: " + ThreadUtils.isMainThread());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("demo2",
                                "onNext: " + ThreadUtils.isMainThread() + ",integer:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("demo2", "onComplete: " + ThreadUtils.isMainThread());
                    }
                });
    }

    public void demo3() {

    }
}



