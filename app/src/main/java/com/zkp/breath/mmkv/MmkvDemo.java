package com.zkp.breath.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.tencent.mmkv.MMKV;

import java.util.Arrays;
import java.util.HashSet;

import static android.content.Context.MODE_PRIVATE;

/**
 * 替换Sp的牛逼轻量级k-v框架（基于内存映射mmap原理），支持多进程，读写效率高出sp很多，推荐使用！推荐使用！
 * <p>
 * 支持的数据类型
 * 基本类型：boolean、int、long、float、double、byte[]
 * 类和容器：1.String、Set<String>； 2.任何实现了Parcelable的类型
 */
public class MmkvDemo {

    public void demo(Context context) {
        // 无指定目录解决路径，默认文件夹路径和sp在同一级路径下（files/mmkv/）
        String rootDir = MMKV.initialize(context);
        System.out.println("mmkv root: " + rootDir);

        // 默认文件名
        // /data/data/包名/files/文件夹名/mmkv.default
        MMKV kv = MMKV.defaultMMKV();
    }

    private void createOrUpdate(MMKV kv) {
        kv.encode("bool", true);
        System.out.println("bool: " + kv.decodeBool("bool"));

        kv.encode("int", Integer.MIN_VALUE);
        System.out.println("int: " + kv.decodeInt("int"));

        kv.encode("long", Long.MAX_VALUE);
        System.out.println("long: " + kv.decodeLong("long"));

        kv.encode("float", -3.14f);
        System.out.println("float: " + kv.decodeFloat("float"));

        kv.encode("double", Double.MIN_VALUE);
        System.out.println("double: " + kv.decodeDouble("double"));

        kv.encode("string", "Hello from mmkv");
        System.out.println("string: " + kv.decodeString("string"));

        byte[] bytes = {'m', 'm', 'k', 'v'};
        kv.encode("bytes", bytes);
        System.out.println("bytes: " + new String(kv.decodeBytes("bytes")));
    }

    private void removeOrQuery(MMKV kv) {
        kv.removeValueForKey("bool");
        System.out.println("bool: " + kv.decodeBool("bool"));

        kv.removeValuesForKeys(new String[]{"int", "long"});
        System.out.println("allKeys: " + Arrays.toString(kv.allKeys()));

        boolean hasBool = kv.containsKey("bool");
    }

    private void createNewFile() {
        // 创建多一张新表
        MMKV mmkv = MMKV.mmkvWithID("MyID");
        mmkv.encode("bool", true);
    }

    private void multiProcess() {
        // 如果业务需要多进程访问，那么在初始化的时候加上标志位 MMKV.MULTI_PROCESS_MODE：
        MMKV mmkv = MMKV.mmkvWithID("InterProcessKV", MMKV.MULTI_PROCESS_MODE);
        mmkv.encode("bool", true);
    }

    private void cusDir(Context context, String dirName) {
        String dir = context.getFilesDir().getAbsolutePath() + "/" + dirName;
        String rootDir = MMKV.initialize(dir);
        System.out.println("MMKV_mmkv root: " + rootDir);
    }

    private void cusSpecilFilesDir(Context context, String dirName) {
        String dir = context.getFilesDir().getAbsolutePath() + "/" + dirName;
        MMKV kv = MMKV.mmkvWithID("testCustomDir", dir);
    }

    /**
     * MMKV 提供了 importFromSharedPreferences() 函数，可以比较方便地迁移数据过来。
     * <p>
     * MMKV 还额外实现了一遍 SharedPreferences、SharedPreferences.Editor 这两个 interface，在迁移的时候只需两三行代码即可，其他 CRUD
     * 操作代码都不用改。
     *
     * @param context
     */
    private void testImportSharedPreferences(Context context) {
        MMKV preferences = MMKV.mmkvWithID("myData");
        // 迁移旧数据
        {
            SharedPreferences old_man = context.getSharedPreferences("myData", Context.MODE_PRIVATE);
            preferences.importFromSharedPreferences(old_man);
            old_man.edit().clear().commit();
        }
        // 跟以前用法一样
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("bool", true);
        editor.putInt("int", Integer.MIN_VALUE);
        editor.putLong("long", Long.MAX_VALUE);
        editor.putFloat("float", -3.14f);
        editor.putString("string", "hello, imported");
        HashSet<String> set = new HashSet<String>();
        set.add("W");
        set.add("e");
        set.add("C");
        set.add("h");
        set.add("a");
        set.add("t");
        editor.putStringSet("string-set", set);
        // 无需调用 commit()
        //editor.commit();
    }

}
