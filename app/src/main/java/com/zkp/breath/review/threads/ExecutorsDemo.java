package com.zkp.breath.review.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsDemo {

    private static void executorCompletionService() {
        // 如果向线程池提交了多个任务，获取顺序是任意的，取决于任务的完成顺序

        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService);
        int length = 0;

        for (int i = 0; i < 10000; i++) {
            executorCompletionService.submit(new CallableImp(i));
            length++;
        }

        int finalLength = length;
        new Thread(() -> {
            for (int i = 0; i < finalLength; i++) {
                try {
                    Future<Integer> take = executorCompletionService.take();
                    Integer integer = take.get();
                    System.out.println("获取到的返回值 : " + integer);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private static void submit() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            // 有返回值提交
            Future<Integer> submit = executorService.submit(new CallableImp(i));
            list.add(submit);
        }

        new Thread(() -> {

            for (int i = 0; i < list.size(); i++) {
                Future<Integer> submit = list.get(i);
                try {
                    // 所在线程会发生阻塞，放在子线程
                    Integer integer = submit.get();
                    System.out.println("获取到的返回值 : " + integer);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private static void execute() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10000; i++) {
            // 无返回值提交任务
            executorService.execute(new RunnableImp(i));
        }
    }

    public static void main(String[] args) {
//        execute();
//        submit();
        executorCompletionService();
    }

    private static class RunnableImp implements Runnable {

        private final int name;

        public RunnableImp(int name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("线程名: " + name);
        }
    }

    private static class CallableImp implements Callable<Integer> {

        private final int name;

        public CallableImp(int name) {
            this.name = name;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return name;
        }
    }


}
