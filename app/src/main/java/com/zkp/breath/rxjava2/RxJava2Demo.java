package com.zkp.breath.rxjava2;

import android.util.Log;

import com.blankj.utilcode.util.ThreadUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created b Zwp on 2019/7/18. https://www.jianshu.com/p/464fa025229e 详细讲解可观看这位大佬的文章
 */
public class RxJava2Demo {

    /**
     * 基本使用
     * 注意: 只有当上游和下游建立连接之后, 上游才会开始发送事件. 也就是调用了subscribe() 方法之后才开始发送事件.
     */
    public static void basicUse() {
        // 被观察者
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 2
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
            // 订阅了观察者
        }).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                // 1
                Log.i("basicUse", "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                // 3
                Log.i("basicUse", "onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                // 4
                Log.i("basicUse", "onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                // 4
                Log.i("basicUse", "onComplete:");
            }
        });
    }

    public static void consumer() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.i("consumer", "Observable thread is : " + Thread.currentThread().getName());
                Log.i("consumer", "emit 1");
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i("consumer", "Observer thread is :" + Thread.currentThread().getName());
                Log.i("consumer", "onNext: " + integer);
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    // observeOn和subscribe
    public static void threadSwitch() {
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
                // 下游设置多个线程以最后一句为准,所以下面是运行在RxCachedThreadScheduler线程上面
                .observeOn(Schedulers.newThread())  // RxNewThreadScheduler
                .observeOn(Schedulers.io()) // RxCachedThreadScheduler
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("threadSwitch", "onSubscribe: " + ThreadUtils.isMainThread());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("threadSwitch",
                                "onNext: " + Thread.currentThread().getName() + ",integer:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("threadSwitch", "onComplete: " + Thread.currentThread().getName());
                    }
                });
    }

    public static void map() {
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

    public static void flatMap() {
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

    public static void zip() {
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

    /**
     * 在主线程中下游没有调用Subscription#request()，那么上游认为上游没有处理能力抛出异常，而不会一直等待。
     */
    public static void backbressErrorInvok() {
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();

        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
            Log.d(method, "emit 1");
            emitter.onNext(1);
            Log.d(method, "emit 2");
            emitter.onNext(2);
            Log.d(method, "emit 3");
            emitter.onNext(3);
            Log.d(method, "emit complete");
            emitter.onComplete();
        }, BackpressureStrategy.ERROR)
                .subscribe(new FlowableSubscriber<Integer>() {

                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.i(method, "onSubscribe: ");
                        subscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(method, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(method, "onError: " + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(method, "onComplete: ");
                    }
                });
    }

    /**
     * 异步线程下没有调用Subscription#request()，那么上游是会正确发送事件的，知识下游不会收到而已。
     * 当上下游处于不同线程，那么上游发送的事件会进入一个缓存队列中，下游获取的事件其实就是从缓存队列中获取的。
     */
    public static void backbressErrorInvok1() {
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(method, "emit 1");
                emitter.onNext(1);
                Log.d(method, "emit 2");
                emitter.onNext(2);
                Log.d(method, "emit 3");
                emitter.onNext(3);
                Log.d(method, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Integer>() {

                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.i(method, "onSubscribe: ");
                        subscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(method, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(method, "onError: " + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(method, "onComplete: ");
                    }
                });
    }

    // 背压
    public static void backbress() {
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
                        // 响应式拉取（响应式编程，其实理解成一种主动接收和被动接收的区别）
                        // 在没有响应式拉取前，上游发多少下游就要接收多少，而现在是下游想要多少才能上游发多少。
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



