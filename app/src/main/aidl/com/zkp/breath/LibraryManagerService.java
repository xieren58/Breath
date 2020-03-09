package com.zkp.breath;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LibraryManagerService extends Service {
    private static final String TAG = "LibraryManagerService";

    // CopyOnWriteArrayList 支持并发读写
    private CopyOnWriteArrayList<String> mBookList = new CopyOnWriteArrayList<>();

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
    };

    public LibraryManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add("book0");
        mBookList.add("book1");
    }
}
