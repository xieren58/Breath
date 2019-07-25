package com.zkp.breath.rxjava2;

import android.util.Log;

import com.blankj.utilcode.util.ThreadUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created b Zwp on 2019/7/18.
 */
public class RxJava2Demo {

    // 基本使用
    public void basicUse() {
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
                Log.d("basicUse", "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("basicUse", "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("basicUse", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("basicUse", "onComplete:");
            }
        });
    }

    // observeOn和subscribe
    public void threadSwitch() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.i("threadSwitch", "subscribe: " + ThreadUtils.isMainThread());

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
                        Log.i("threadSwitch", "onSubscribe: " + ThreadUtils.isMainThread());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("threadSwitch",
                                "onNext: " + ThreadUtils.isMainThread() + ",integer:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("threadSwitch", "onComplete: " + ThreadUtils.isMainThread());
                    }
                });
    }

    public void map() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).map(new Function<Integer, Integer>() {

            @Override
            public Integer apply(Integer integer) throws Exception {
                return integer + 1;
            }
        }).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("map", "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("map", "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("map", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("map", "onComplete:");
            }
        });
    }

    public void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<Integer>>() {

            @Override
            public ObservableSource<Integer> apply(Integer integer) throws Exception {

                return Observable.create(new ObservableOnSubscribe<Integer>() {

                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(integer + 1);
                        emitter.onNext(integer + 2);
                        emitter.onNext(integer + 3);
                    }
                });
            }
        }).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("map", "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("map", "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("map", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("map", "onComplete:");
            }
        });
    }
}



