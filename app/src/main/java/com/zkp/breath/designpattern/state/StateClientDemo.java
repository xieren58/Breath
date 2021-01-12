package com.zkp.breath.designpattern.state;

/**
 * 状态模式：主要解决的是当控制一个对象状态转换的条件表达式过于复杂的情况，把状态的判断逻辑转移到表示不同状态的
 * 一系列当中。
 * 当一个对象的行为取决于它的状态，并且它必须在运行时刻根据状态改变它的行为时，就可以考虑使用状态模式。
 * <p>
 * 优点：
 * 1. 将与特定状态相关的行为局部化，并且将不同状态的行为分割开来。
 * 2. 消除庞大的条件分支语句。
 * 3. 把各种状态转移逻辑分布到State的子类中，来减少相互间的依赖。
 */
class StateClientDemo {

    public static void main(String[] args) {
        // 只需要先设定mp3的初始状态，就可以调用各种功能方法了，不需要再考虑功能和状态之间的关系.
        Context context = new Context();
        context.setMp3State(new PowerOffState());
        context.preSong();
        context.powerOn();
        context.nextSong();
        context.powerOff();
    }

}
