package com.zkp.breath.designpattern.composite;

/**
 * 叶子节点
 */
public class Leaf extends Component {

    public Leaf(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        // 叶子节点是最终节点，所以空实现。但这样可以消除叶节点和枝节点再抽象层次的区别
    }

    @Override
    public void remove(Component component) {
        // 叶子节点是最终节点，所以空实现。但这样可以消除叶节点和枝节点再抽象层次的区别
    }

    @Override
    public void display() {
        System.out.println("name:" + name);
    }
}
