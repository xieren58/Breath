package com.zkp.breath.review;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类加载器的Demo
 * https://blog.csdn.net/briblue/article/details/54973413
 */
public class ClassLoaderDemo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //创建自定义classloader对象。
        DiskClassLoader diskLoader = new DiskClassLoader("D:\\lib");
        try {
            //加载class文件
            Class c = diskLoader.loadClass("com.frank.test.Test");

            if(c != null){
                try {
                    Object obj = c.newInstance();
                    Method method = c.getDeclaredMethod("say", (Class<?>) null);
                    //通过反射调用Test类的say方法
                    method.invoke(obj, (Object) null);
                } catch (InstantiationException | IllegalAccessException
                        | NoSuchMethodException
                        | SecurityException |
                        IllegalArgumentException |
                        InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static class DiskClassLoader extends ClassLoader {

        private String mLibPath;

        public DiskClassLoader(String path) {
            // TODO Auto-generated constructor stub
            mLibPath = path;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            // TODO Auto-generated method stub

            String fileName = getFileName(name);

            File file = new File(mLibPath, fileName);

            try {
                FileInputStream is = new FileInputStream(file);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int len = 0;
                try {
                    while ((len = is.read()) != -1) {
                        bos.write(len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] data = bos.toByteArray();
                is.close();
                bos.close();

                return defineClass(name, data, 0, data.length);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return super.findClass(name);
        }

        //获取要加载 的class文件名
        private String getFileName(String name) {
            // TODO Auto-generated method stub
            int index = name.lastIndexOf('.');
            if (index == -1) {
                return name + ".class";
            } else {
                return name.substring(index + 1) + ".class";
            }
        }
    }

    // 文件放在任意位置，这里我们放在D盘lib文件夹下
    // package com.frank.test;
//    public class Test {
//        public void say(){
//            System.out.println("Say Hello");
//        }
//
//    }

}
