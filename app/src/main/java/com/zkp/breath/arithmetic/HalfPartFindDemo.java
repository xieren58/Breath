package com.zkp.breath.arithmetic;

/**
 * 二分法查找
 * 算法：当数据量很大适宜采用该方法。采用二分法查找时，数据需是有序不重复的。
 * 算法核心：
 * 如果 value==arr[mid],中间值正好等于要查找的值，则返回下标，return mid;
 * 如果 value<arr[mid],要找的值小于中间的值，则再往数组的小端找，high=mid-1;
 * 如果 value>arr[mid],要找的值大于中间的值，则再往数组的大端找，low=mid+1;
 */
public class HalfPartFindDemo {
    public static void main(String[] args) {
        int[] arr = new int[]{12, 23, 34, 45, 56, 67, 77, 89, 90};
        System.out.println(search(arr, 89));
    }

    public static int search(int[] arr, int findTarget) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            if (findTarget < arr[middle]) {
                end = middle - 1;
            } else if (findTarget > arr[middle]) {
                start = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }
}
