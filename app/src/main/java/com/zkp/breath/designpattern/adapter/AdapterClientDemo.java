package com.zkp.breath.designpattern.adapter;

import com.zkp.breath.designpattern.adapter.classadapter.ClassAdapter;
import com.zkp.breath.designpattern.adapter.classadapter.ITarget;
import com.zkp.breath.designpattern.adapter.interfaceadapter.AInterfaceAdapterImp;
import com.zkp.breath.designpattern.adapter.interfaceadapter.InterfaceAdapter;
import com.zkp.breath.designpattern.adapter.objectadapter.Adaptee2;
import com.zkp.breath.designpattern.adapter.objectadapter.ObjectAdapter;

/**
 * Created b Zwp on 2019/7/17.
 * 适配器模式：将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些
 * 类可以一起工作。
 */
public class AdapterClientDemo {

    public static void main(String[] args) {
        classAdapterFunction();
        objectAdapterFunction();
    }

    /**
     * 类适配器
     */
    private static void classAdapterFunction() {
        // 原本的实现类不满足需求，为了保持原有的调用逻辑且满足开闭原则，接入适配类去重写或者补充原先的逻辑。
        // 需要清楚的点：
        // 1. 为了保持原有的调用逻辑，适配器类也需要实现原来的接口。（这里指代ITarget的request()方法）
        // 2. 适配器类继承一个父类，该父类存在对原有的逻辑进行改写或者补充。
        // 3. 适配器实现接口的方法内部调用继承类的方法，达到适配转换的目的

//        ITarget adapter = new TargetImp();
        ITarget adapter = new ClassAdapter();
        adapter.request("我们");
    }

    /**
     * 对象适配器 推荐，因为比类适配器更灵活。 类适配器的写法只能写死用一种转换，但是对象适配器模式，只要传入不同的转换类就能实现不同的效果
     */
    private static void objectAdapterFunction() {

//        ITarget adapter = new ObjectAdapter(new Adaptee());
        ITarget adapter = new ObjectAdapter(new Adaptee2());
        adapter.request("我们");
    }

    /**
     * 接口适配器模式
     */
    private static void interfaceAdapterFunction() {
        InterfaceAdapter interfaceAdapter = new AInterfaceAdapterImp();
//        InterfaceAdapter interfaceAdapter = new BInterfaceAdapterImp();
        interfaceAdapter.a();
        interfaceAdapter.b();
        interfaceAdapter.c();
        interfaceAdapter.d();
    }
}
