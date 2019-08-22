package com.zkp.breath.review;

/**
 * Created b Zwp on 2019/8/22. 自增运算符讲解
 */
public class Increment {

    public static void main(String[] args) {

        int y = 5;
        y = ++y + y++;
        System.out.println("y结果为" + y);  // 12

        // 分解
        int x = 5;
        int xx = ++x;   // 相当于下面的prefixAutoAdd()
        int xxx = x++;   // 相当于下面的suffixAutoAdd()
        int i = xx + xxx;
        System.out.println("i结果为" + i);
    }

    // 后缀自增分解:X++
    private int suffixAutoAdd(int x) {
        int temp = x;
        x = x + 1;
        return temp;
    }

    // 前缀自增分解:++X
    private int prefixAutoAdd(int x) {
        x = x + 1;
        return x;
    }
}
