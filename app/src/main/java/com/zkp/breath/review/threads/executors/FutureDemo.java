package com.zkp.breath.review.threads.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 将任务包裹一层，能够获取到执行结果
 */
public class FutureDemo {

    public static void main(String[] args) {
//        f1();
//        f2();
        f3();
    }

    // 模式取消任务
    private static void f3() {
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("线程信息: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException  | CancellationException e) {
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
