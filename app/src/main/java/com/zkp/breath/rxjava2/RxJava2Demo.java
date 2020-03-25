package com.zkp.breath.rxjava2;

import android.util.Log;

import com.blankj.utilcode.util.ThreadUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
     * 同一个线程：1.创建策略发射器对象，内部持有下游；2.通过下游对象调用下游的onSubscribe（）方法将发射器对象
     * 传入到下游对象中存放，至此发射器和下游互相持有对方；3.通过上游对象调用上游的subscribe（）将发射器对象传入
     * 到上游对象中存放。
     * <p>
     * 一般我们会在上游对象中调用策略发射器对象的OnNext（）：1.判断是否已经中断，中断则不走接下来的流程；2.判断
     * 参数是否为Null，为Null则中断接下来的流程 3.判断当前存在请求则执行下游的OnNext方法，不存在请求则直接抛异常。
     */
    public static void backbressStrategyError() {

        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
            Log.i("backbressErrorInvok", "emit 1");
            emitter.onNext(1);
            Log.i("backbressErrorInvok", "emit 2");
            emitter.onNext(2);
            Log.i("backbressErrorInvok", "emit 3");
            emitter.onNext(3);
            Log.i("backbressErrorInvok", "emit complete");
            emitter.onComplete();
        }, BackpressureStrategy.ERROR)
                .subscribe(new FlowableSubscriber<Integer>() {

                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.i("backbressErrorInvok", "onSubscribe: ");
                        subscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("backbressErrorInvok", "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("backbressErrorInvok", "onError: " + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("backbressErrorInvok", "onComplete: ");
                    }
                });
    }

    /**
     * 当上下游工作在不同的线程里时，每一个线程里都有一个requested，而我们调用request（1000）时，实际上改变的是
     * 下游主线程中的requested，而上游中的requested的值是由RxJava内部调用request(n)去设置的，这个调用会在合适的时候自动触发。
     * <p>
     * 当上下游处于不同线程，那么上游发送的事件会进入一个缓存大小为128队列中，下游获取的事件其实就是从缓存队列中获取的。
     * <p>
     * BackpressureStrategy.BUFFER只是让缓存大小变为Integer.MAX_VALUE，如果这时候下游没有去处理，那么内存
     * 会快速上升，有可能造成OOM
     */
    public static void backbressErrorInvok1() {

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 默认提前设置128
                Log.i("backbressErrorInvok1", "requestCount " + emitter.requested());

                // 这里i < 128修改为129就会报错。
                for (int i = 0; i < 128; i++) {
                    Log.i("backbressErrorInvok1", "emit " + i);
                    emitter.onNext(i);
                    // 每发送一个就会减少一个
                    Log.i("backbressErrorInvok1", "requestCount_now " + emitter.requested());
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.i("backbressErrorInvok1", "onSubscribe: ");
                        subscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("backbressErrorInvok1", "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("backbressErrorInvok1", "onError: " + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("backbressErrorInvok1", "onComplete: ");
                    }
                });
    }


    private static Subscription subscription;

    public static void request() {
        subscription.request(128);
        subscription = null;
    }

    /**
     * Drop就是直接把存不下的事件丢弃,Latest就是只保留最新的事件。
     */
    public static void backbressErrorInvok2(boolean flag) {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10000; i++) {
                    Log.i("backbressErrorInvok2", "emit " + i);
                    emitter.onNext(i);
                }
                Log.i("backbressErrorInvok2", "发送完毕");
            }
        }, flag ? BackpressureStrategy.DROP : BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("backbressErrorInvok2", "onNext：" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // 背压
    public static void backbress() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) {
                for (int i = 0; i < 128; i++) {
                    emitter.onNext(i);
                    Log.i("backbress", "emit:" + i);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    Subscription mSubscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.i("backbress", "onSubscribe");
                        // 响应式拉取（响应式编程，其实理解成一种主动接收和被动接收的区别）
                        // 在没有响应式拉取前，上游发多少下游就要接收多少，而现在是下游想要多少才能上游发多少。
                        mSubscription = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("backbress", "onNext: " + integer);
                        mSubscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("backbress", "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("backbress", "onComplete");
                    }
                });
    }

    /**
     * 模拟读取大分文件的场景
     *
     * val open = InputStreamReader(assets.open("test.txt"))
     * @param open
     */
    public static void practice1(InputStreamReader open) {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                Log.i("practice1", "subscribe: " + emitter.requested());

                try {
//                    FileReader reader = new FileReader(open);
                    BufferedReader br = new BufferedReader(open);

                    String str;

                    while ((str = br.readLine()) != null && !emitter.isCancelled()) {
                        while (emitter.requested() == 0) {
                            if (emitter.isCancelled()) {
                                break;
                            }
                        }
                        emitter.onNext(str);
                        Log.i("practice1", "subscribe: " + emitter.requested());
                    }

                    br.close();
                    open.close();

                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    Subscription mSubscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(String string) {
                        Log.i("practice1", "onNext: " + string);
                        System.out.println(string);
                        try {
                            Thread.sleep(1000);
                            mSubscription.request(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}



