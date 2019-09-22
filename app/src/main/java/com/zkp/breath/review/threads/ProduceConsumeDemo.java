package com.zkp.breath.review.threads;

public class ProduceConsumeDemo {

    private int product;
    private static final int MAX_PRODUCT = 20;
    private static final int MIN_PRODUCT = 0;

    /**
     * 生产者生产出来的产品交给店员
     */
    private synchronized void produce() {

        while (this.product >= MAX_PRODUCT) {
            try {
                System.out.println("产品已满,请稍候再生产");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.product++;
        System.out.println("生产者生产第" + this.product + "个产品.");
        notifyAll();
    }

    /**
     * 消费者从店员取产品
     */
    private synchronized void consume() {

        // 用while是防止多消费线程出问题
        // 比如有1，2消费线程，1获得执行权消费完产品进入等待后被生产者获得执行权生产了一个，然后2获得执行权消费完后激活了1，
        // 1进入就绪状态，但是2还是在执行权状态，又执行了一次consume()方法判断成立进入等待，而这时候1恰巧获得执行权，如果没有使用while
        // 再判断一把就直接走下面的自减方法得到的就是一个负数，这样子就不对了，所以一定要加while。
        while (this.product == MIN_PRODUCT) {
            try {
                System.out.println("缺货,稍候再取");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("消费者取走了第" + this.product + "个产品.");
        this.product--;
        notifyAll();
    }


    public static void main(String[] args) {
        ProduceConsumeDemo produceConsumeDemo = new ProduceConsumeDemo();

        Thread p1 = new Thread(new ProduceRunnableImp(produceConsumeDemo));
        Thread p2 = new Thread(new ProduceRunnableImp(produceConsumeDemo));
        Thread p3 = new Thread(new ProduceRunnableImp(produceConsumeDemo));

        Thread c1 = new Thread(new ConsumeRunnableImp(produceConsumeDemo));
        Thread c2 = new Thread(new ConsumeRunnableImp(produceConsumeDemo));
        Thread c3 = new Thread(new ConsumeRunnableImp(produceConsumeDemo));

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }

    private static class ProduceRunnableImp implements Runnable {

        private final ProduceConsumeDemo produceConsumeDemo;

        public ProduceRunnableImp(ProduceConsumeDemo produceConsumeDemo) {
            this.produceConsumeDemo = produceConsumeDemo;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    produceConsumeDemo.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ConsumeRunnableImp implements Runnable {

        private final ProduceConsumeDemo produceConsumeDemo;

        public ConsumeRunnableImp(ProduceConsumeDemo produceConsumeDemo) {
            this.produceConsumeDemo = produceConsumeDemo;
        }

        @Override
        public void run() {

            while (true) {
                try {
                    Thread.sleep(1500);
                    produceConsumeDemo.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
