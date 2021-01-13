package com.zkp.breath.designpattern.memento;

/**
 * 备忘录模式：保存一个对象的某个状态，以便在适当的时候恢复对象。备忘录模式属于行为型模式。
 * 备忘录的存放和获取都只能由Memento操作。
 */
class MementoClientDemo {

    public static void main(String[] args) {
        // 发起人
        Originator originator = new Originator();
        originator.setState1("满血");
        originator.setState2("满魔");

        CareTaker careTaker = new CareTaker();
        careTaker.setMemento(originator.saveState());

        System.out.println("保存：" + originator.toString());

        //...............
        //...............

        originator.restoreState(careTaker.getMemento());
        System.out.println("恢复：" + originator.toString());
    }

}
