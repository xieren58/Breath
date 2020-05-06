package com.zkp.breath.arithmetic;


/**
 * https://mp.weixin.qq.com/s/3h9iqU4rdH3EIy5m6AzXsg
 * <p>
 * 动态规划：最优子结构，边界，状态转移方程
 */
public class DynamicProgramming {

    public static void main(String[] args) {
        int climbingWays = getClimbingWays(10);
        System.out.println("结果：" + climbingWays);
    }

    private static int getClimbingWays(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1) {
            return 1;   // 边界
        }

        if (n == 2) {
            return 2;   // 边界
        }

        // 状态转移方程
        return getClimbingWays(n - 1) + getClimbingWays(n - 2);
    }
}
