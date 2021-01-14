package com.zkp.breath.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 枝节点
 */
public class Composite extends Component {

    private List<Component> children = new ArrayList<>();

    public Composite(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void remove(Component component) {
        children.remove(component);
    }

    @Override
    public void display() {
        for (Component c : children) {
            System.out.println("name:" + name);
        }
    }

}
