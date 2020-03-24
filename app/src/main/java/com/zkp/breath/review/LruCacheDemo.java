package com.zkp.breath.review;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class LruCacheDemo {

    private final Context context;
    private Handler handler = new Handler();
    //使用固定线程池优化
    private static ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    //设置缓存大小，建议当前应用可用最大内存的八分之一 即(int) (Runtime.getRuntime().maxMemory() / 1024 / 8)
    BitmapLruCache bitmapLruCache = new BitmapLruCache((int) (Runtime.getRuntime().maxMemory() / 1024 / 8));


    public LruCacheDemo(Context context) {
        this.context = context;
    }

    static class BitmapLruCache extends LruCache<String, Bitmap> {

        public BitmapLruCache(int maxSize) {
            super(maxSize);
        }

        /**
         * 计算当前节点的内存大小 这个方法需要重写 不然返回1
         */
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    }

    //加载图片的类
    public class Requestor {

        private final Context context;
        private String mUrl;

        public Requestor(String mUrl, Context context) {
            this.mUrl = mUrl;
            this.context = context;
        }

        private int mErrorResId;

        public Requestor error(int errorResId) {
            this.mErrorResId = errorResId;
            return this;
        }

        //加载图片：三级缓存
        public void into(ImageView target) {
            //从Lru获取
            Bitmap bitmap = bitmapLruCache.get(mUrl);

            //内存有图片
            if (bitmap != null) {
                target.setImageBitmap(bitmap);
                return;
            }

            //内存没有图片，从disk获取
            bitmap = getBitmapFromDisk(mUrl, context);

            //磁盘有图片：
            if (bitmap != null) {
                //存放到内存,Lru缓存
                bitmapLruCache.put(mUrl, bitmap);
                //显示图片
                target.setImageBitmap(bitmap);
                return;
            }

            //磁盘没有图片，从网络获取
            //每次new Thread性能消耗会很大
            //new Thread(new LoadBitmapTask(mUrl, target, mErrorResId)).start();
            //从线程池中 开启线程
            threadPool.execute(new LoadBitmapTask(mUrl, target, mErrorResId, context));
        }
    }

    /**
     * 加载图片的任务
     */
    public class LoadBitmapTask implements Runnable {

        private final Context context;
        private String mUrl;
        private ImageView mImageView;
        private int mErrorResId;
        private Bitmap bitmap;

        public LoadBitmapTask(String mUrl, ImageView mImageView, int mErrorResId, Context context) {
            this.mUrl = mUrl;
            this.mImageView = mImageView;
            this.mErrorResId = mErrorResId;
            this.context = context;
        }

        @Override
        public void run() {
            //从网络加载图片
            try {
                URLConnection urlConnection = new URL(mUrl).openConnection();
                urlConnection.setReadTimeout(5000);
                urlConnection.setConnectTimeout(5000);
                urlConnection.connect();
                //获取到的图片
                InputStream inputStream = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

                //从网络加载成功，存放到磁盘
                saveBitmapToDisk(mUrl, bitmap, context);

                //存放到Lru缓存
                bitmapLruCache.put(mUrl, bitmap);

                //显示图片 跳转主线程显示
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //出错了，加载错误的图片
                        mImageView.setImageResource(mErrorResId);
                    }
                });
            }
        }


    }


    //从磁盘获取图片
    private static Bitmap getBitmapFromDisk(String mUrl, Context context) {
        File file = new File(getMyCacheDir(context), MD5Utils(mUrl));
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }


    private static void saveBitmapToDisk(String mUrl, Bitmap bitmap, Context context) {
        try {
            //文件路径+MD5加密文件名
            File file = new File(getMyCacheDir(context), MD5Utils(mUrl));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //包Bitmap保存到文件
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static File getMyCacheDir(Context context) {
        //获取apk: data/data/包民/cache
        File dir = new File(context.getCacheDir(), "davince-cache");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static String MD5Utils(String str) {//项目中的
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte b[] = md5.digest();

            StringBuffer sb = new StringBuffer("");
            for (int n = 0; n < b.length; n++) {
                int i = b[n];
                if (i < 0) i += 256;
                if (i < 16) sb.append("0");
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();  //32位加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}
