package com.zkp.breath.review.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            // 无返回值提交任务
//            executorService.execute(new RunnableImp(i));

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
