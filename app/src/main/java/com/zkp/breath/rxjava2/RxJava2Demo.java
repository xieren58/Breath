package com.zkp.breath.rxjava2;

import android.util.Log;

import com.blankj.utilcode.util.ThreadUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Created b Zwp on 2019/7/18. https://www.jianshu.com/p/464fa025229e 详细讲解可观看这位大佬的文章
 * <p>
 * 其实就是代理模式，然后一层一层传递调用。
 */
public class RxJava2Demo {


    /**
     * 基本使用
     * 注意: 只有当上游和下游建立连接之后, 上游才会开始发送事件. 也就是调用了subscribe() 方法之后才开始发送事件.
     *
     * 这种观察者模型不支持背压：当被观察者快速发送大量数据时，下游不会做其他处理，即使数据大量堆积，调用链也不会
     * 报MissingBackpressureException,消耗内存过大只会OOM。所以，当我们使用Observable/Observer的时候，
     * 我们需要考虑的是，数据量是不是很大(官方给出以1000个事件为分界线作为参考)。
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

        // 可以设置只接收onNext事件，但是不推荐。
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

                for (int i = 0; i < 100; i++) {
                    emitter.onNext(i);
                    Log.i("threadSwitch", "subscribe: " + i);
                }
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                // 下游设置多个线程以最后一句为准,所以下面是运行在RxCachedThreadScheduler线程上面
//                .observeOn(Schedulers.newThread())  // RxNewThreadScheduler
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

    /**
     * just操作符，内部调用了fromArray操作符。该操作符相当于省略了被观察者的事件操作实现。
     * 内部提供重载函数，参数数量从1-10。建议多参数使用fromArray操作符。
     */
    public static void just() {
        // 将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();
        Observable.just("Hello", "Hi", "Aloha")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("just",
                                "onSubscribe: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("just",
                                "onNext: " + Thread.currentThread().getName() + ",s:" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("just",
                                "onError: " + Thread.currentThread().getName() + ",e:" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("just", "onComplete: " + Thread.currentThread().getName());
                    }
                });
    }

    /**
     * fromArray操作符接收数组参数（参数类型是可变参数），作用和just操作符一样。
     */
    public static void fromArray() {
        String[] strings = new String[]{"Hello", "Hi", "Aloha"};
        Observable.fromArray(strings)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("fromArray",
                                "onSubscribe: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("fromArray",
                                "onNext: " + Thread.currentThread().getName() + ",s:" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("fromArray",
                                "onError: " + Thread.currentThread().getName() + ",e:" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("fromArray", "onComplete: " + Thread.currentThread().getName());
                    }
                });
    }

    /**
     * fromIterable操作符接收Iterable接口实现类，一般是Collection集合。
     * 作用和fromArray一样，只是一个接收数组类型，一个接收集合类型。
     */
    public static void fromIterable() {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("Hi");
        list.add("Aloha");
        Observable.fromIterable(list)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("fromIterable",
                                "onSubscribe: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("fromIterable",
                                "onNext: " + Thread.currentThread().getName() + ",s:" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("fromIterable",
                                "onError: " + Thread.currentThread().getName() + ",e:" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("fromIterable", "onComplete: " + Thread.currentThread().getName());
                    }
                });
    }

    /**
     * map转换操作符，将源数据的类型转换成目标数据类型。
     */
    public static void map() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }

            // Function，只有源数据和目标数据
            // Array2Func,Array3Func.....Array9Func,接收的源数据对应n个。
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

    /**
     * flatMap操作符，将数据源的每个事件构建成一个新的Observable对象。（在嵌套请求网络接口很有用）
     */
    public static void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
            // Function的泛型一：数据源类型；  泛型二：新的类型的ObservableSource数据源
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

    /**
     * zip操作符，对多个Observable进行操作，得出一个新的目标Observable
     */
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
                // 最后一个参数是最终的数据类型
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
     * 当上下游在不同的线程中，通过Observable发射，处理，响应数据流时，如果上游发射数据的速度快于下游接收处理数据的
     * 速度，这样对于那些没来得及处理的数据就会造成积压，这些数据既不会丢失，也不会被垃圾回收机制回收，而是存放在一
     * 个异步缓存池中，如果缓存池中的数据一直得不到处理，越积越多，最后就会造成内存溢出，这便是响应式编程中的
     * 背压（backpressure）问题。
     * <p>
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
     * 背压缓存策略：
     * 1.Drop就是直接把存不下的事件丢弃（丢弃新的）
     * 2.Latest就是只保留最新的事件（覆盖旧的）
     * 3.error（超过128个事件就抛异常）
     * 4.buffer (加大缓冲容量，比较容易出现内存溢出)
     * 5.missing (超过就超过，不作出任何反应。一般场景都不会用的)
     *
     * <p>
     * 线程调度器（Schedulers）：
     * 1.AndroidSchedulers.mainThread()	需要依赖rxandroid库, 切换到UI线程。
     * 2.Schedulers.computation()	用于计算任务，如事件循环和回调处理，默认线程数等于处理器数量。
     * 3.Schedulers.io()	用于IO密集型任务，如异步阻塞IO操作，这个调度器的线程池会根据需求，
     *                      它默认是一个CacheThreadScheduler。
     * 4.Schedulers.newThread()	为每一个任务创建一个新线程。
     * 5.Schedulers.trampoline():在当前线程中立刻执行，如当前线程中有任务在执行则将其暂停，等插入进来的任务
     *                          执行完成之后，在将未完成的任务继续完成。（相当于Thread的join()作用）。
     * 6.Scheduler.from(executor)	指定Executor作为调度器。
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
                .subscribeOn(Schedulers.trampoline())
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
     * <p>
     * val open = InputStreamReader(assets.open("test.txt"))
     *
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

    public static void w() {

    }
}



