package com.zkp.breath.designpattern.composite;

/**
 * 组合模式：将对象组合成树形结构表示“部分-整体”的层次结构。使得用于对单个对象和组合对象的使用具有一致性。
 * 透明方式和安全方式
 */
class CompositeClientDemo {

    public static void main(String[] args) {
        // 树干
        Composite root = new Composite("root");

        // 叶节点
        root.add(new Leaf("Leaf A"));
        root.add(new Leaf("Leaf B"));

        // 枝节点
        Composite comp = new Composite("Composite X");
        root.add(new Leaf("Leaf XA"));
        root.add(new Leaf("Leaf XB"));

        root.add(comp);
    }

}
