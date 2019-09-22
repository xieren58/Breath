package com.zkp.breath.review.threads;

public class JoinThreadDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new A(true));
        thread.start();
    }

    private static class A implements Runnable {
        private boolean joinFlag;

        public A(boolean joinFlag) {
            this.joinFlag = joinFlag;
        }

        @Override
        public void run() {

            Thread thread = new Thread(new B());
            thread.start();

            if (joinFlag) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 10000; i++) {
                System.out.println("线程A：" + i);
            }
        }
    }

    private static class B implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                System.out.println("线程B：" + i);
            }
        }
    }


}
