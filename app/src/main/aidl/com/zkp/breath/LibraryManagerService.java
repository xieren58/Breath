package com.zkp.breath;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class LibraryManagerService extends Service {

    private static final String TAG = "LibraryManagerService";
    // 系统提供的专门用于保存、删除跨进程 listener 的类
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();
    // AtomicBoolean 支持并发读写
    private AtomicBoolean mIsServiceDestroy = new AtomicBoolean(false);
    // CopyOnWriteArrayList 支持并发读写
    private CopyOnWriteArrayList<String> mBookList = new CopyOnWriteArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add("book0");
        mBookList.add("book1");
        simulate();
    }

    private void simulate() {
        // 在子线程中每隔3秒创建一本新书，并通知所有已注册的客户端
        new Thread(() -> {
            // 如果服务还没终止
            while (!mIsServiceDestroy.get()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Book book = new Book();
                // 获取存放的监听对象的数量
                int n = mListenerList.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
                    if (listener != null) {
                        try {
                            // 回调
                            listener.onNewBookArrived(book);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // 移除所有存放的监听对象
                mListenerList.finishBroadcast();
            }
        }).start();
    }

    private Binder mBinder = new ILibraryManager.Stub() {

        @Override
        public List<String> getNewBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void donateBook(String book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void testCustomDataType(Book book) throws RemoteException {

        }

        @Override
        public void register(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
        }

        @Override
        public void unregister(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroy.set(true);
    }
}
