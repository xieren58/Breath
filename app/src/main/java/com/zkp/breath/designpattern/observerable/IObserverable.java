package com.zkp.breath.designpattern.observerable;

/**
 * 被观察者
 * <p>
 * 三种实现：
 * 1. 可以是接口，现面向接口编程。
 * 2. 可以是抽象类，因为一般而言下面三个方法的实现逻辑都是固定的，并没有什么特别的地方。
 * 3. 被观察者可以不需要抽象层，其实可以只有具体实现层。但由于开闭原则和依赖倒转原则还是建议多一层抽象层。
 */
public interface IObserverable {

    public void registerObserver(IObserver o);

    public void removeObserver(IObserver o);

    public void notifyObservers();
}
