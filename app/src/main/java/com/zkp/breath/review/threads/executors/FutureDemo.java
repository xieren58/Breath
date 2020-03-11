package com.zkp.breath.review.threads.executors;

import com.zkp.breath.bean.ReflectionBean;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * https://segmentfault.com/a/1190000016767676
 * <p>
 * 将任务包裹一层，能够获取到执行结果
 * <p>
 * FutureTask继承了Runnable和Future，所以本身是一个任务，能作为参数传入到Thread。
 * 而Future又能接收Callable(有返回值的任务)或者Runnable(内部适配器模式包裹成一个Callable)，实际内部的run()方法
 * 内运行的是Callable/Runnable的run()方法。
 *
 * 只是FutureTask多了支持获取任务结果（Callable的返回值）和取消任务（只有New状态下才能取消成功，否则直接返回false）。
 *
 *
 */
public class FutureDemo {

    public static void main(String[] args) {
//        f1();
//        f2();
        f3();
    }

    // 模式取消任务
    private static void f3() {
        // 实例化对象的时候就初始为New状态
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("线程信息: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException | CancellationException e) {
                    e.printStackTrace();
                    System.out.println("响应中断: ");
                    throw new Exception();
                }
                System.out.println("睡眠结束");
                return 22;
            }
        });
        new Thread(futureTask).start();

        boolean cancel = futureTask.cancel(true);
        System.out.println("取消状态: " + cancel + "，isCancel：" + futureTask.isCancelled());

        try {
            // 看源码知道，当前为New或者COMPLETING状态则会阻塞线程，NORMAL状态可以正常获取结果，
            // CANCELLED/INTERRUPTING/INTERRUPTED都会抛出CancellationException异常。
            System.out.println("获取到的数值为: " + futureTask.get());
        } catch (ExecutionException | InterruptedException | CancellationException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    // 模拟FutureTask的get()阻塞线程的场景
    private static void f2() {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println("线程信息: " + Thread.currentThread().getName());
            Thread.sleep(7000);
            System.out.println("睡眠结束");
            return 22;
        });

        new Thread(futureTask).start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            // 只要状态不等于New都返回true。
            System.out.println("isDone：" + futureTask.isDone());
            System.out.println("获取到的数值为: " + futureTask.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    // 模拟无阻塞获取到结果的场景
    private static void f1() {
        FutureTask<Integer> future = new FutureTask<>(() -> {
            System.out.println("线程信息: " + Thread.currentThread().getName());
            return 22;
        });
        new Thread(future).start();

        try {
            System.out.println("进入睡眠");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("获取到的数值为: " + future.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("捕获异常");
        }
    }
}
