package com.zkp.breath.review;

/**
 * Created b Zwp on 2019/8/22.
 * String和StringBuffer
 * String可看成是常量（改变内容会重新new，内存地址就改变了），StringBuffer是变量（改变内容内存地址也不会改变）
 */
public class StringAndSB {

    public static void main(String[] args) {

        String s1 = "abc";
        s1 = s1 + "d";
        StringBuffer s2 = new StringBuffer(s1);
        // false,String的equals()会进行类型判断，非String类型直接返回false
        System.out.println("丢1" + s1.equals(s2));
        // false,StringBuffer没有重写Object的equals()方法，比较的是内存地址，一定不相等
        System.out.println("丢2" + s2.equals(s1));
        // true,StringBuffer的toString()会new出一个String，而String的equals是比较内容
        System.out.println("丢3" + s2.toString().equals(s1));
        // false,StringBuffer的toString()会new出一个String，new出来的内存地址一定不相等
        System.out.println("丢4" + (s2.toString() == s1));

        String str1 = "123";
        // 看源码，0直接返回本身
        System.out.println("123" == str1.substring(0)); //3,true
        // 其余会返回一个新new出来的对象
        System.out.println("23" == str1.substring(1));  //4,false

        trimMethod();
    }

    private static void trimMethod() {
        // trim内部调用了substring
        String str = "ab";
        String str1 = (" a" + "b ").trim();  // 左右有空格发生截取，重新new
        String str2 = ("a" + "b").trim();   // 没有发生截取，返回本身
        System.out.println(str == str1);    // false
        System.out.println(str == str2);    // true


        String s3 = " a bcde ";
        System.out.println("输出:" + s3);

    }


}
