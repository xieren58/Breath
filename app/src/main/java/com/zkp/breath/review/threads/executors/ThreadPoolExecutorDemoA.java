package com.zkp.breath.review.threads.executors;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * https://segmentfault.com/a/1190000016629668
 *
 * Executor
 * 执行器接口，也是最顶层的抽象核心接口， 分离了任务和任务的执行。
 * <p>
 * ExecutorService
 * 在Executor的基础上提供了执行器生命周期管理，任务异步执行等功能。
 * <p>
 * ScheduledExecutorService
 * 在ExecutorService基础上提供了任务的延迟执行/周期执行的功能。
 * <p>
 * Executors
 * 生产具体的执行器的静态工厂
 * <p>
 * ThreadFactory
 * 线程工厂，用于创建单个线程，减少手工创建线程的繁琐工作，同时能够复用工厂的特性。
 * <p>
 * AbstractExecutorService
 * ExecutorService的抽象实现，为各类执行器类的实现提供基础。
 * <p>
 * ThreadPoolExecutor
 * 线程池Executor，也是最常用的Executor，可以以线程池的方式管理线程。
 * <p>
 * ScheduledThreadPoolExecutor
 * 在ThreadPoolExecutor基础上，增加了对周期任务调度的支持。
 * <p>
 * ForkJoinPool
 * Fork/Join线程池，在JDK1.7时引入，是实现Fork/Join框架的核心类。
 */
public class ThreadPoolExecutorDemoA {

    public static void main(String[] args) {

        /**
         * 使用给定的参数创建ThreadPoolExecutor.
         *
         * @param corePoolSize    核心线程池中的最大线程数
         * @param maximumPoolSize 总线程池中的最大线程数
         * @param keepAliveTime   空闲线程的存活时间
         * @param unit            keepAliveTime的单位
         * @param workQueue       任务队列, 保存已经提交但尚未被执行的线程
         * @param threadFactory   线程工厂(用于指定如果创建一个线程)
         * @param handler         拒绝策略 (当任务太多导致工作队列满时的处理策略)
         */
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
    }
}
