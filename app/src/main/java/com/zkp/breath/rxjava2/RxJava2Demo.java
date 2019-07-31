package com.zkp.breath.rxjava2;

import android.util.Log;

import com.blankj.utilcode.util.ThreadUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created b Zwp on 2019/7/18. https://www.jianshu.com/p/464fa025229e 详细讲解可观看这位大佬的文章
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
                Log.i("basicUse", "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("basicUse", "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("basicUse", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("basicUse", "onComplete:");
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
                Log.i("map", "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("map", "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("map", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("map", "onComplete:");
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
        }).flatMap(new Function<Integer, ObservableSource<String>>() {

            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {

                return Observable.create(new ObservableOnSubscribe<String>() {

                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("first:" + integer + "_1");
                        emitter.onNext("second:" + integer + "_2");
                        emitter.onNext("thrid:" + integer + "_3");
                    }
                });
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i("flatMap", "onSubscribe");
            }

            @Override
            public void onNext(String integer) {
                Log.i("flatMap", "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("flatMap", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("flatMap", "onComplete:");
            }
        });
    }

    public void zip() {
        Observable<Integer> integerObservable = Observable
                .create(new ObservableOnSubscribe<Integer>() {

                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onComplete();
                    }
                });

        Observable<String> stringObservable = Observable
                .create(new ObservableOnSubscribe<String>() {

                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("A");
                        emitter.onNext("B");
                        emitter.onNext("C");
                        emitter.onNext("D");
                        emitter.onComplete();
                    }
                });

        Observable.zip(integerObservable, stringObservable,
                new BiFunction<Integer, String, String>() {

                    @Override
                    public String apply(Integer integer, String s) throws Exception {
                        return integer + s;
                    }
                })

                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("zip", "onSubscribe");
                    }

                    @Override
                    public void onNext(String integer) {
                        Log.i("zip", "onNext:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("zip", "onError:" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("zip", "onComplete:");
                    }
                });
    }

    // 背压
    public void backbress() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d("backbress", "emit 1");
                emitter.onNext(1);
                Log.d("backbress", "emit 2");
                emitter.onNext(2);
                Log.d("backbress", "emit 3");
                emitter.onNext(3);
                Log.d("backbress", "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    Subscription mSubscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d("backbress", "onSubscribe");
                        mSubscription = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("backbress", "onNext: " + integer);
                        mSubscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w("backbress", "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("backbress", "onComplete");
                    }
                });
    }
}



